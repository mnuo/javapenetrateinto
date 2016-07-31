#### java并发包
#### 1 ConcurrentHashMap
ConcurrentHashMap是线程安全的HashMap
代码操作:
		/**
		 * @author saxon
		 * ConcurrentHashMap key和value均不可为空 ; HashMap key和value均可为空
		 */
		public class CurrentHashMapDemo {
			/**
			 * @param args
			 */
			public static void main(String[] args) {
				ConcurrentHashMap<String, Object> cmap = new ConcurrentHashMap<String, Object>();
				cmap.put("A", "hello world ");
				cmap.put("b", "aa");
				cmap.put("B", "bb");
				///key value 均不可为空
				//cmap.put(null, "2aa");
				//cmap.put("b", null);
				System.out.println(cmap);
				System.out.println("是否包含keyA: " + cmap.containsKey("A"));
				System.out.println("remove key==B");
				cmap.remove("B");
				//遍历
				Set<Entry<String, Object>> set = cmap.entrySet();
				Iterator<Entry<String, Object>> ite = set.iterator();
				while(ite.hasNext()){
					Entry<String, Object> element = ite.next();
					System.out.print(element.getKey() + ": ");
					System.out.println(element.getValue());
				}
				//HashMap
				HashMap<String, Object> mashmap = new HashMap<String, Object>();
				mashmap.put("A", "hello world ");
				mashmap.put("b", null);
				mashmap.put("b", "gg");
				//均可为空
				mashmap.put(null, null);
				System.out.println(mashmap);
			}
		}
与HashMap性能比较:
> 1 无论元素数量为多少,在线程数为10时,ConcurrentHashMap带来的性能提升并不明显;但当线程数越多时,在增加和删除元素带来了一倍左右的性能提升,在查找元素上更是带来了10倍左右的性能提升,并且ConcurrentHashMap性能并没有出现下降的现象

#### 2 CopyOnWriteArrayList
CopyWriteArrayList是一个线程安全,并且在读操作时无锁的ArrayList.
方法:
+ add(E) 
add方法并没有加上synchronized关键字,他通过ReentrantLock来保证县城安全,和ArrayList不同的是每次都会创建一个新的Object数组,次数字的大小为当前数字大小加1,将之前数组中的内容复制到新的数组中,并将新增加的对象放入数组末尾,最后切换将新创建的数组对象复制给全局的数组对象

+ remove(E)
和add一样也是通过ReentrantLock来保证线程安全;remove时创建一个比当前数组小1的数组,遍历新数组,如找到equals或均为null的元素,则将之后的元素全部复制给新的数组对象,并作引用切换,返回true;如果未找到,则将当前的元素,赋值给新的数组对象,最后特殊处理数组中的最后一个元素

+ get(int)
此方法没有加锁

+ iterator()

和ArrayList性能比较
> CopyOnWriteArrayList在增加元素和删除元素 时性能下降比较明显,并且性能会比ArrayList低,但在查找元素这点上随着线程数的增加,性能较ArraList会好很多

#### 3 CopyOnWriteArraySet
CopyOnWriteArraySet 基于CopyOnWriteArrayList;唯一个不同是在add时调用CopyOnwriteArrayList的addIfAbsent方法;CopyOnWriteArraySet在add时每次都要进行数组的遍历,因此性能会略低于CopyOnwriteArrayList

#### 4 ArrayBlockingQueue
ArrayBlockingQueue是一个基于数组的先进先出,线程安全的集合类,其特色为可实现指定时间的阻塞读写,并且容量是可限制的

+ ArrayBlockingQueue(int)
没有构造器,出入的参数即为创建对象数组的大小,同时初始化锁和两个锁上的Condition:notEmpty,notFull
+ offer(E,long,TimeUnit)
此方法用于插入元素至数组的尾部,如数组已满,则进入等待,知道出现以下三种情况:
	- 被唤醒
	- 到达指定时间
	- 当前线程被中断

+ poll(E,long,TimeUnit)
获取第一个元素,如队列中没有元素,则进入等待与offer相同

+ iterater();

#### 5 AtomicInteger
AtomicInteger是一个支持院子操作的Integer类,在没有AtomicInteger前,要实现一个按顺序获取的ID,就必须每次获取时进行加锁操作,以避免出现并发时获取到同样ID的现象
/**
 * @author saxon
 */
public class AtomicintegerDemo {
	private static int id = 0;
	private static AtomicInteger atomicId = new AtomicInteger();
	private static CountDownLatch latch = null;
	
	public synchronized static int qetNextId(){
		return ++id;
	}
	
	public static int getNextIdWithAtomic(){
		return atomicId.incrementAndGet();
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		latch = new CountDownLatch(50);
		long beginTime = System.nanoTime();
		for(int i = 0 ; i < 50; i ++){
			new Thread(new Task(false)).start();
		}
		latch.await();
		System.out.println("Synchronized syle consume time:" + (System.nanoTime() - beginTime));
		latch = new CountDownLatch(50);
		beginTime = System.nanoTime();
		for(int i = 0; i < 50; i ++){
			new Thread(new Task(true)).start();
		}
		latch.await();
		System.out.println("CAS style consume time: " + (System.nanoTime() - beginTime));
	}
	static class Task implements Runnable{
		private boolean isAtomic;
		
		public Task (boolean isAtomic) {
			this.isAtomic = isAtomic;
		}
		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				if(isAtomic){
					getNextIdWithAtomic();
				}else{
					qetNextId();
				}
			}
			latch.countDown();
		}
	}
}
输出:
Synchronized syle consume time:32397426
CAS style consume time: 20887698

由此,CAS 的性能比使用sychronized要好

#### 6 ThreadPoolExecutor
ThreadPollExecutor 是并发包中提供的一个线程池的服务,基于ThreadPoolExecutor可以很容易的将一个实现了Runnable接口的任务放入线程池中执行

ThreadPoolExecutor提供了4种策略:
+ CallerRunsPolicy
>  采用此种策略的情况下,当线程池中的线程数等于最大线程数后,则交由调用者线程来执行此Runnable任务
+ AbortPolicy
> 采用此种策略,当线程池中的线程数等于最大线程数时,直接抛出RejectedExecutionException
+ DiscardPolicy
> 采用此种策略,当线程池中的线程数等于最大线程数时,不作任何动作
+ DiscardOldestPolicy
> 采用此种策略,当线程池中的线程数等于最大线程数时,抛弃要执行的最后一个Runnable任务,并执行此新传入的Runnable任务

#### 7 Executor
Excutors 提供了一些方便创建ThreadPoolExcutor的方法
+ newFixedThreadPool(int)
创建固定大小的线程池,线程keepAliveTime为0,默认情况下,ThreadPoolExecutor中启动的corepoolSize数量的线程启动后就一直运行,并不会由于keepAliveTime时间到达后仍没有任务需要执行就退出,缓冲任务的队列 为LinkedBlockingQueue,大小为整形的最大数

+ new SingleThreadExecutor()
相当于创建大小为1单位的固定线程池

+ new CachedThreadPool()
创建corPoolSize为0,最大线程数为整型的最大数

#### 8 FutureTask
FutureTask 可用于要异步获取执行结果,或取消执行任务的场景,通过传入Runnable或Callable的任务给FutureTask,直接调用其run方法或放入线程池执行,之后可在外部通过FutureTask的get一步获取执行结果

#### 9 Semaphore
Semaphore是并发包中提供的用于控制某资源同时被访问的个数的类,例如连接池中通常要控制创建的连接的个数:
传统方法:
int MaxActive;
int numActive;
int maxWait;
LinkedList pool;
public void get() throws Exception{
	long startTime = System.currentTimeMillis();
	Object object = null;
	for(;;){
		sychronized(this){
		object = pool.removeFirst();
		if((object == null)&& (numActive >= maxActive)){
		long waitTime = maxWait-(System.currentTimeMillis()-startTime);
		wait(waitTime);
		if((System.currentTimeMillis() - startTime) > maxWait)
			//throw new Exception("");
		}else{
			continue
		}
	}
	numActive ++;
	}
	return object;
	}
}
semaphore:修改for循环
final long elapsed = (System.currentTimeMillis() - starttime);
final long waitTime = maxWait - epapsed;
boolean timeouted = semmaphore.tryAcquirre(waitTime, TimeUnit.MILLISECONDS);
if(!timeouted){
//throw "timeout waiting for idle object"
try{
sysnchronized(pool){
pair = (ObjectTimestampPair) (poolremoveFirst());

#### 10 CountDownLatch
CountDownLatch 是并发包中提供的一个可用于控制多个线程同时开始某动作的类,其采用的方式为减计数的方式,当计数减到0时,位于latch.await后的代码才会被执行

#### 11 CyclicBarrier
CyclicBarrier和CountDownLatch不同,CyclicBarrier是当await的数量到达了设定的数量后,才继续往下执行

#### 12 ReentrantLock
ReentrantLock是并发包中提供的一个更为方便的控制并发资源的类,且和sychronized语法达到的效果一致的

#### 13 Condition
Condition是并发包中提供的一个接口,典型的实现有,ReentrantLock,ReentrantLock提供了一个newCondition的方法,以便用户在同一个锁的情况下可以根据不同的情况执行等待或唤醒动作,典型的用法可参考ArrayBlockingQueue的实现

#### 14 ReentrantReadWriteLock
ReentrantReadWriteLock和ReentrantLock没有任何关系,提供了读锁(ReadLock)和写锁(WriteLock),相比ReentrantLock只有一把锁而言,在读写多少的的场景中可大幅提升度的性能
+ 在同一线程中,持有读锁后,不能直接调用写锁的lock方法,如果这样会造成死锁
+ 在同一线程中,持有写锁后,可调用读锁的lock方法,在此之后如果调用写锁的unlock方法,那么当前锁将降级为读锁






