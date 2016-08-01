5 性能调优
##### 1 CPU消耗分析
+ 上下文切换:
> 每个CPU在同一时间只能执行一个线程,Linux采用的是抢占式调度,即为每个线程分配一定的执行时间,当到达执行时间,线程中有IO阻塞或高优先级线程要执行时,Linux将切换执行的线程,在切换时要存储目前线程的执行状态,并恢复要执行的线程的状态,这个过程就称为上下文切换.对于Java应用,典型的是在进行文件IO操作,网络IO操作,所等待或线程Sleep时,当前线程会进入阻塞或休眠状态,从而触发上下文切换,上下文切换过多会造成内核占据较多的CPU使用,使得应用的响应速度下降
+ 运行队列
> 每个cpu都维护了一个可运行的线程队列,例如一个4核的CPU,JAVA应用中启动了8个线程,且这8个线程都处于可运行状态,那么在分配平均的情况下,每个CPU中运行队列里就会有两个线程.通常而言,系统的load主要由CPU的运行队列来决定,假设以上状况维持了1分钟,那么着1分钟内系统的load就会是2,但由于load是个复杂的值,因此也绝不是绝对的,运行队列值越大,就意味着线程会要消耗越长的时间才能执行完
+ 利用率
> CPU利用率为CPU在用户进程,内核,中断处理,IO等待以及空闲5个部分使用百分比,这5个值是用来分析CPU消耗情况的关键指标,建议用户进程的CPU消耗/内核的CPU消耗比率在65%-70%/30%-35%
	- top 其中4.0% 表示用户进程处理所占的百分比;8.9% sy表示为内核线程处理所占的百分比;ni表示内nice命令改变优先级的任务所占的百分;id表示CPU的空闲时间所占的百分比;wa表示为在执行的过程中等待IO所占的百分比;hi表示为硬件中断所占的百分比;si表示为软件中断所占的百分比
	- pidstat 是sysstat中的工具,如需使用pidstat 先安装sysstat
	通过pidstat 找到 tid 转成16进制,使用jstack pid | grep 'nid=0x...' 找到线程

#### 2 文件IO消耗分析
Linux 在操作文件时，将数据放入文件缓存区，知道内存不够或系统要释放内存给用户进行使用，因此在查看linux内存状况时，经常会发现可用（free）的物理内存不多，但cached用户很多，这是Linux提升文件IO速度的一种做法。

+ pidstat 
> pidstat -d -t -p [pid] 1 100

+ iostat 
> iostatt -x xvda 3 5
	- r/s 表示每秒读的请求书；w/s表示每秒写的请求数；wait表示平均每次IO操作的等待时间ms；avgqu-sz表示等待请求的队列的平均长度；svctm表示平均每次设备执行IO操作的时间；util表示一秒之中有百分之几用于IO操作

#### 3 网络IO消耗分析

+ sar 
> sar -n FULL 1 2 

#### 4 内存消耗分析
目前的Java应用只有在创建线程和使用Direct ByteBuffer 时才会操作JVM堆外的内存JVM因此在内存消耗方面，最为值得关注的是JVM内存的消耗状况，对于JVM内存消耗状况分析的方法在深入JVM中已介绍（jmap，jstat，mat，visualvm等方法），在此就不在进行阐述，JVM内存消耗过多会导致GC执行频繁，CPU消耗增加，应用线程的执行速度严重下降，甚至造成OutOfMemeoryError，最终导致Java进程退出。   
对于JVM堆以歪的内存方面的消耗，最为值得关注的是swap的消耗以及物理内存的消耗，这两方面的消耗都可基于os提供的命令来查看

+ vmstat 
> 其中信息和内存相关的主要是memory 的swpd ， free， buff， cache 以及swap下的si和so
		
		[root@iZ94ibhx8rnZ ~]# vmstat
		procs -----------memory---------- ---swap-- -----io---- --system-- -----cpu-----
		 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
		 0  0      0 108912  29428  64784    0    0     0     2    6   26  1  1 97  0  0

+ sar 
> 通过sar 的-r参数可查看内存的消耗状况

		[root@iZ94ibhx8rnZ ~]# sar -r 2 5
		Linux 2.6.32-573.22.1.el6.x86_64 (iZ94ibhx8rnZ)         08/01/2016      _x86_64_        (1 CPU)

		01:26:32 PM kbmemfree kbmemused  %memused kbbuffers  kbcached  kbcommit   %commit
		01:26:34 PM    110508    908000     89.15     29480     64940   1216368    119.43
		01:26:36 PM    110508    908000     89.15     29480     64940   1216368    119.43
		01:26:38 PM    110508    908000     89.15     29480     64940   1216368    119.43
		01:26:40 PM    110508    908000     89.15     29480     64940   1216368    119.43
		01:26:42 PM    110508    908000     89.15     29480     64940   1216368    119.43
		Average:       110508    908000     89.15     29480     64940   1216368    119.43

		其中和swap 相关的信息主要是kbswpfree，kbswpused， %swpused， kbswpfree表示swap空闲的大小，kbswpused表示已使用的swap大小，%swpused表示使用的swap空间比率，系统中可用的物理内存为：kbmemfree + kbbuffers + kbcached

+ top 
> 通过top可查看进程所消耗的内存量

+ pidstat
> 通过pidstat也可查看进程所消耗的内存量，命令格式 pidstat -r -p [pid][interval][times] 例如:pidstat -r -p 1199 1 100
	[root@iZ94ibhx8rnZ ~]# pidstat -r -p 1199 1 100
	Linux 2.6.32-573.22.1.el6.x86_64 (iZ94ibhx8rnZ)         08/01/2016      _x86_64_        (1 CPU)

	01:33:22 PM       PID  minflt/s  majflt/s     VSZ    RSS   %MEM  Command
	01:33:23 PM      1199      0.00      0.00 2400520 310272  30.46  java
	01:33:24 PM      1199      0.00      0.00 2400520 310272  30.46  java
	01:33:25 PM      1199      0.00      0.00 2400520 310272  30.46  java
	01:33:26 PM      1199      0.00      0.00 2400520 310272  30.46  java
	01:33:27 PM      1199      0.00      0.00 2400520 310272  30.46  java


#### 5 程序执行慢原因分析
+ 锁竞争激烈
+ 未充分使用硬件资源
+ 数据量增长


### 2 调优
#### 1  JVM调优
+ 代大小的调优
在不采用G1(G1不区分minor GC 和Full GC)的情况下,通常minor GC会快于Full GC
	- -Xms和-Xmx 通常设置为相同的值,避免运行时要不断的扩展JVM内存空间,这个值决定了JVM heap所能用的最大空间
	- -Xmn 决定了新生代(new Generation) 空间的大小,新生代中Eden ,S0和S1三个区域的比例可以通过-XX:SurivorRatio来控制
	- -XX:MaxTenuringThreshold控制对象在经理多少次MinorGC后才转入旧生代,通常又将此值称为新生代存活周期,此参数只有在串行GC时有效,其他GC方式则由Sun JDK自行决定
#### 2 GC调优

#### 3 CPU消耗严重的解决方法
+ CPU us高的解决方法
> CPU us高的原因主要是执行线程无任何官气动作,且一直执行,导致CPU没有机会去调度执行其他的线程,造成线程饿死的现象,对于这种情况,常见的一种优化方法是对这种线程的动作增加Thread.sleep,以释放CPU的执行权,降低CPU的消耗
		for(int k = 0; i < 10000; k ++){
			list.add(str + String.valueOf(k))
			if(k % 50 == 0){
				try{
					Thread.sleep(1);
				}catche(Exception e){

				}
			}
		}

+ CPU sy 高的解决方法
> CPU sy高的原因主要是线程的运行状态要经常切换,对于这种情况,最简单的优化方法是减少线程数.

#### 4 文件IO消耗严重的解决方法
从程序而言,造成文件IO消耗严重的原因主要是多个线程在写大量的数据到统一文件,导致文件很快变得很大,从而写入速度越来越慢,并造成各线程激烈争抢文件锁,常用调用方法:
+ 异步写文件
	> 将写文件的同步动作改为异步动作,避免应用由于写文件慢而性能下降太多,例如写日志,可以使用log4j提供的AsyncAppender
+ 批量读写
> 频繁读写的另外一个调优方式就是限流,从而将文件IO消耗控制到一个能接受的范围,例如:
	log.error(errorInfo, throwable);
+ 限制文件大小

#### 5 网络IO消耗严重的解决方法
从程序角度而言造成IO消耗严重的原因主要是同时需要发送或接收的包太多,对于这类情况,常用的调优方法为进行限流,限流通常是限制发送packet的频率,从而在网络IO消耗可接受的情况下来发送packet

#### 6 对于内存消耗严重的情况
+ 1 释放不必要的引用
	ThreadLocal<byte[]> localString = new ThreadLocal<byte[]>();
	localStirng.set(new byte[1024*1024*30]);
+ 2 使用对象缓存池:

+ 3 使用对象失算缓存池:
 
+ 4 合理使用SoftReference和WeakReference