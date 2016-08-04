---
title: 大型分布式网站--1面向服务的体系架构(SOA)
date: 2016-08-04 16:12:08 
tags: 分布式java基础应用和实践
category: 分布式java基础应用和实践
---
### 1 面向服务的体系架构(SOA)

#### 1 基于TCP协议的RPC

##### 1.1 PRC
> Remote Process Call,即远程过程调用,他应用广泛实现方式也很多,拥有RMI,WebService等诸多成熟的方案,在业界得到了广泛的使用

##### 1.2 对象序列化
    - 将对象转换成二进制流的过程称为对象的序列化
	- 将二进制转成对象的过程称为对象的反序列化

    方案:
	- Google的Protocal Buffers: 性能优异,支持跨平台,无法直接使用Java等面向对象编程语言
	- java内置序列化方式,Hessian:Hessian相对Protocal Buffers,效率稍低,但对各种语言有着良好的支持,且性能稳定,比java内置反式效率要高很多
	- JSON和XML

    例子:  
	java内置对象:
    
        	public static void main(String[] args) throws IOException, ClassNotFoundException {
        		//序列化,将对象存入ByteArrayOutputStream,即写入内存中,也可以存入文件中
        		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        		ObjectOutputStream oos = new ObjectOutputStream(bos);
        		
        		oos.writeObject(new Persion("张三"));
        		byte[] zhangsan = bos.toByteArray();
        		
        		//反序列化,将对象从内存中,读取出来
        		ByteArrayInputStream bis = new ByteArrayInputStream(zhangsan);
        		ObjectInputStream ois = new ObjectInputStream(bis);
        		Persion object = (Persion) ois.readObject();
        		System.out.println(object.getName());
        	}
	Hessian:
            
        	public static void main(String[] args) throws Exception {
        		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        		//Hessian的序列化输出
        		HessianOutput ho = new HessianOutput(bos);
        		ho.writeObject(new Persion("李四"));
        		byte[] data = bos.toByteArray();
        		
        		ByteArrayInputStream bis = new ByteArrayInputStream(data);
        		//Hessian反序列化读取对象
        		HessianInput hi = new HessianInput(bis);
        		Persion lizi = (Persion) hi.readObject();
        		
        		System.out.println(lizi.getName());
        	}

##### 1.3 基于TCP的RPC例子

    /javapenetrateinto/src/main/java/com/mnuocom/largedistributedweb/chapter1/tcprpc

#### 2 基于HTTP协议的RPC

##### 2.1 HTTP协议栈
HTTP是Hypertext Transfer Protocol(超文本传输协议)的缩写

![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largeDistributed-1.2-http-req-resp.jpg)

例子: 客户端会向服务端发送一条命令,服务端收到命令后,会判断命令是否为HELLO 如果是"HELLO",则返回给客户端的相应为"hello",否则返回个客户端的相应为"bye".

##### 2.2 HTTP请求与响应
![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largedistributed-1.2-http-comunite.jpg)

##### 2.3 通过HttpClient发送请求

##### 2.4 HTTP相对TCP的优势

##### 2.5 JSON和XML
JSON(JavaScript Object Notation) 是一种轻量级的数据交换语言,以文字为基础,且易于让人阅读.尽管JSON是JavaScript的一个子集,但其独立于语言的文本格式,使得JSON成为理想的数据交换语言

XML:

	Person person = new Person("zhangsan","jiangsu");
	
	//将person对象转化成xml
	XStream stream = new XStream(new DomDriver());
	stream.alias("person", Person.class);
	String personXML = stream.toXML(person);
	System.out.println(personXML);
	/*FileWriter writer = new FileWriter(new File("d://person.xml"));
	writer.write(personXML);
	writer.flush();
	writer.close();*/
	Person person2 = (Person) stream.fromXML(personXML);
	System.out.println(person2.getName() + ","+ person2.getAddress());

##### 2.6 RESTful 和 RPC
URL链接风格:

+ RPC   
> 直接在HTTP请求的参数中标明需要远程调用的服务接口,服务需要的参数即可: http: //localhost:80/service/getListRelease.action?pageNumber=1&pageSize=10&pressRelease.pressType=0

+ REST (Representational State Transfer) 表现层状态转换
> ![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-largedistributed-1.2-restful.jpg)

##### 2.7 基于HTTP协议的RPC的实现
基于Java的Servlet接口,实现了基于HTTP协议的RPC风格的远程调用,服务和消费者和远端的服务提供方法

#### 3 服务的路由和负载均衡
##### 3.1 服务化的演变

公共的业务被拆分出来,形成可共用的服务,最大程度的保障了代码和逻辑的复用,避免重复建设,这种设计也称为SOA(Service-Oriented Architecture)

##### 3.2 负载均衡算法
+ 1 轮询(Round Robin)法
+ 2 随机(Random)法
+ 3 原地址哈希(Hash)法
+ 4 加权轮询法(Weight Round Robin)
+ 5 加权随机法(Weight Random)

##### 3.3 动态配置规则
固定的策略在有些时候还是无力满足千变万化的需求,对于开发者来说一方面需要支持特定用户需求,另一方面又得尽可能的复用代码,避免重复开发,导致维护成本增加,这便将这部分特殊的需求剥离出来,采用动态配置规则的方法来实现
> 由于Groovy 脚本语言能够直接编译成Java的class字节码,运行在Java虚拟机上,切能够很好的更Java进行交互,因此这里通过Groovy语言,利用其动态特性,来实现负载均衡规则的动态配置

##### 3.4 ZooKeeper
ZoopKeeper 是Hadoop下的一个子项目,他是一个针对大型分布式系统的可靠的协调系统,提供的功能包括配置维护,名字服务,分布式同步,组服务等.Zookeeper是可以集群复制的,集群间通过Zab(Zookeeper Atomic Broadcast)协议来保持数据的一致性.该协议看起来像是paxos协议的包中变形,该协议包括两个阶段:leader election阶段和Atomic broadcas阶段

+ 1 install

	1.1 下载

	    wget http://mirrors.hust.edu.cn/apache/zookeeper/zookeeper-3.4.8/zookeeper-3.4.8.tar.gz

	1.2 处理
    
		tar -zxvf zookeeper-3.4.8.tar.gz
	  	mv zookeeper-3.4.8 zookeeper
		rm zookeeper-3.4.8.tar.gz

	1.3 配置 
        - 环境变量:
    
		    vim /etc/profile
			export ZOOKEEPER_INSTALL=/usr/zookeeper
			export PATH=$PATH:$ZOOKEEPER_INSTALL/bin

	    - zoo.cfg

            cd zookeeper/conf/
		    cp zoo_sample.cfg zoo.cfg
		    vim zoo.cfg
    			# The number of milliseconds of each tick
    			tickTime=2000
    			#zookeeper服务器心跳时间,单位ms
    			# The number of ticks that the initial
    			# synchronization phase can take
    			initLimit=10
    			#投票选举新leader的初始化时间
    			# The number of ticks that can pass between
    			# sending a request and getting an acknowledgement
    			syncLimit=5
    			#leader与follower心跳检测最大容忍时间,响应超过syncLimit*tickTime,leader认为follower死掉,从服务器列表中删除follower
    			# the directory where the snapshot is stored.
    			# do not use /tmp for storage, /tmp here is just
    			# example sakes.
    			dataDir=/tmp/zookeeper/data
    			dataLogDir=/tmp/zookeeper/log
    			# the port at which the clients will connect
    			clientPort=2181
                
	1.4 创建目录:
		 mkdir -p /tmp/zookeeper/log
		 mkdir -p /tmp/zookeeper/data

	1.5 启动 :
		cd bin/
		./zkServer.sh start(restart/stop/status)
	1.6 测试是否成功:
		jps

		./zkCli.sh -server 127.0.0.1:2181

例子 来自:[http://greemranqq.iteye.com/blog/2171449]:

[http://greemranqq.iteye.com/blog/2171449]: http://greemranqq.iteye.com/blog/2171449

		/**
		 * @author saxon
		 * 例子:
		 * 	假设我是一家KTV的老板,我同事拥有5家KTV,我肯定得时刻监视我KTV的情况,这时候我会给每家KTV设置一个视频监控,然后每一家
		 * 都连接到我的视频监控里面,那么我就可以在家里看到所有的KTV情况了,如果某一家出现问题,我就能及时发现,并且做出反应
		 */
		public class ZookeeperDemo {
			//根节点
			public static final String ROOT = "/root-ktv";
			
			public static void main(String[] args) throws Exception {
				//创建一个与服务器的连接
				ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, new Watcher(){
					//监控所有被触发的事件
					@Override
					public void process(WatchedEvent event) {
						System.out.println("状态: " + event.getState() + " : " + 
								event.getType() + "　：　" + event.getWrapper() + 
								" : " + event.getPath());
					}
				});
				//创建一个总的目录ktv,并不控制权限,这里需要用持久节点,不然下面的节点创建容易出错
				zk.create(ROOT, "root-ktv".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				
				//然后在杭州开一个KTV,PERSISTEMT_SEQUENTIAL类型会自动加上0000000000自增的后缀
				zk.create(ROOT + "/杭州KTV", "杭州ktv".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
				//也可以在北京开一个 EPHEMERAL session过期了就会自动删除
				zk.create(ROOT + "/北京KTV", "北京ktv".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
				//同理,我可以在北京开多个,EPHEMERAL_SEQUENTIAL session过期自动删除,也会加数字的后缀
				zk.create(ROOT + "/北京KTV", "北京ktv".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
				
				//我们也可以来看看,一共监视了多少家的ktv
				List<String> ktvs = zk.getChildren(ROOT, true);
				
				System.out.println(Arrays.toString(ktvs.toArray()));
				/*for(String node : ktvs){
					//删除节点
					zk.delete(ROOT + "/" + node, -1);
				}
				
				zk.delete(ROOT, -1);
				zk.close();*/
			}
		}










