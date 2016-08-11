---
title: 大型分布式网站--2 分布式系统基础设施--memcached
date: 2016-08-011 16:12:08 
tags: 分布式java基础应用和实践
category: 分布式java基础应用和实践
---
### memcached简介及java的使用方法
(1-5部分来自:[http://blog.csdn.net/seelye/article/details/8511073])

[http://blog.csdn.net/seelye/article/details/8511073]: http://blog.csdn.net/seelye/article/details/8511073
#### 1 What is Memcached?
> Memcached是danga.com(运营LiveJournal的技术团队) 开发的一套分布式内存对象缓存系统,用于在动态系统中减少***数据库***负载,提升性能.

#### 2 适用场合

+ 1 分布式应用. 由于memcached本身基于分布式的系统,所以尤其适合大型的分布式系统.
+ 2 数据库前段缓存.数据库常常是网站系统的瓶颈.数据库的大并发量访问,常常造成网站内存溢出.当然我们也可以使用hibernate的缓存机制.但memecached是基于分布式的,并可独立于网站应用本身,所以更适合大型网站进行应用的拆分.
+ 3 服务器间数据共享.举例来讲,我们即将网站的登录系统,查询系统拆分成两个应用,放在不同的服务器上,并进行集群,那这个时候用户登录后,登录信息如何从登录系统服务器同步到查询系统服务器呢?这时候,我们便可以使用memcached,登录系统将登录信息缓存起来,查询系统便可以获得登录信息,就像获取本地信息一样.

#### 3 不适用场合
> 那些不需要分布的,不需要共享的,或者干脆规模小到只有一台服务器的应用,memcached不会带来任何好处,相反还会拖慢系统效率,因为网络连接同样需要资源.

#### 4 Install for window

+ 1 下载window版 

    http://www.urielkatz.com/archive/detail/memcached-64-bit-windows/

+ 2 安装:

	C:\Users\Administrator\Downloads\memcached-win64>memcached.exe -d install # 安装
	C:\Users\Administrator\Downloads\memcached-win64>memcached.exe -d start	# 启动
	
	![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-memcached-install-over.jpg)

#### 5 客户端

+ 1 java_memcached-release    
	- 比较通用的Memecached客户端框架
	- 依赖的jar   
		- A. commons-pool-1.5.6.jar
		- B. java_memcached-release_2.6.3.jar
		- C. slf4j-api-1.6.1.jar
		- D. slf4j-simple-1.6.1.jar

+ 2 alisoft-xplatform-asf-cache   
	- 阿里软件的架构师岑文初进行封装的。里面的注释都是中文的，比较好
	- 依赖的jar   
		- A. alisoft-xplatform-asf-cache-2.5.1.jar
		- B. commons-logging-1.0.4.jar
		- C. hessian-3.0.1.jar
		- D. log4j-1.2.9.jar
		- E. stax-api-1.0.1.jar
		- F. wstx-asl-2.0.2.jar

+ 3 官方memcached-java-client

#### 6 memcached-java-client
1 配置:
	
	static {
		String[] servers = {
				"127.0.0.1:11211"
		};
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);//设置服务器
		pool.setFailback(true);//容错
		pool.setInitConn(10);//设置初始连接数
		pool.setMinConn(5);//设置最小连接数
		pool.setMaxConn(25);//设置最大连接数
		pool.setMaintSleep(30);//设置连接池维护线程的睡眠时间
		pool.setNagle(false);//设置是否使用Nagele算法
		pool.setSocketTO(3000);//设置socket的读取等待超时时间
		pool.setAliveCheck(true);//设置连接心跳检测开关
		pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);//设置Hash算法
		pool.initialize();
	}
2 API:
		//设置缓存
		memCachedClient.add("key", (int)1);
		memCachedClient.set("key", (int)2);
		memCachedClient.replace("key", (int)3);
		
		memCachedClient.add("key1", (int)1);
		memCachedClient.set("key1", (int)2);
		
		memCachedClient.add("key2", (int)1);
		//获取缓存的值
		System.out.println("key: " + memCachedClient.get("key"));
		System.out.println("key1: " + memCachedClient.get("key1"));
		System.out.println("key2: " + memCachedClient.get("key2"));
		Map<String, Object> map = memCachedClient.getMulti(new String[]{"key1","key2"});
		
		Set<Entry<String, Object>> mapset = map.entrySet();
		Iterator<Entry<String, Object>> iterable = mapset.iterator();
		
		while(iterable.hasNext()){
			Entry<String, Object> entiry = iterable.next();
			System.out.println(entiry.getKey() + " : " + entiry.getValue());
		}
		
		//prepend and append 
		memCachedClient.add("key4", "hello world!");
		System.out.println("key4: " + memCachedClient.get("key4"));
		memCachedClient.prepend("key4", "hello ");
		memCachedClient.append("key4", "!");
		System.out.println("prepend and append: " + memCachedClient.getCounter("key4"));
		
		//修改某个key某个版本的值
		MemcachedItem item = memCachedClient.gets("key1");
		memCachedClient.cas("key1", (Integer) item.getValue() + 5, item.getCasUnique());
		
		System.out.println("cas 之后: " + memCachedClient.get("key1"));
		
		//incr 增加
		memCachedClient.incr("key2");
		System.out.println("incr: " + memCachedClient.get("key2"));
		//decr 减少
		memCachedClient.decr("key2");
		System.out.println("incr: " + memCachedClient.get("key2"));

#### 7 memcached-java-client AND Sring 
+ 1 sring 配置文件

    	<!-- 读取配置文件 memcached.properties -->  
        <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">  
    		<property name="order" value="1"/>  
    	        <property name="ignoreUnresolvablePlaceholders" value="true"/>  
    	        <property name="locations">  
    	        <list>  
    	            <value>classpath:memcached/memcached.properties</value>  
    	        </list>  
    	    </property>  
        </bean>  
         <!-- Memcached配置 -->  
        <bean id="memcachedPool" class="com.whalin.MemCached.SockIOPool"  
            factory-method="getInstance" init-method="initialize" destroy-method="shutDown">  
            <property name="servers">  
                <list>  
                    <value>${memcached.server}</value>  
                </list>  
            </property>  
            <property name="initConn">  
                <value>${memcached.initConn}</value>  
            </property>  
            <property name="minConn">  
                <value>${memcached.minConn}</value>  
            </property>  
            <property name="maxConn">  
                <value>${memcached.maxConn}</value>  
            </property>  
            <property name="maintSleep">  
                <value>${memcached.maintSleep}</value>  
            </property>  
            <property name="nagle">  
                <value>${memcached.nagle}</value>  
            </property>  
            <property name="socketTO">  
                <value>${memcached.socketTO}</value>  
            </property>  
        </bean> 

+ 2 memcached配置文件
    	#######################Memcached配置#######################  
    	#服务器地址  
    	memcached.server=127.0.0.1:11211
    	#初始化时对每个服务器建立的连接数目  
    	memcached.initConn=20
    	#每个服务器建立最小的连接数  
    	memcached.minConn=10 
    	#每个服务器建立最大的连接数  
    	memcached.maxConn=50
    	#自查线程周期进行工作，其每次休眠时间  
    	memcached.maintSleep=3000
    	#Socket的参数，如果是true在写数据时不缓冲，立即发送出去  
    	memcached.nagle=false
    	#Socket阻塞读取数据的超时时间  
    	memcached.socketTO=3000

+ 3 封装类
    	private static final Logger logger = Logger.getLogger(MemcachedUtils.class);  
        private static MemCachedClient cachedClient;  
        
        static {  
            if (cachedClient == null)  
                cachedClient = new MemCachedClient();  
        }  
      
        public MemcachedUtils() {  
        }  
      
        /** 
         * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换。 
         * @param key 键 
         * @param value 值 
         * @return 
         */  
        public static boolean set(String key, Object value) {  
            return setExp(key, value, null);  
        }  
      
        /** 
         * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换。 
         * @param key 
         * @param value 
         * @param expire  过期时间 New Date(1000*10)：十秒后过期 
         * @return 
         */  
        public static boolean set(String key, Object value, Date expire) {  
            return setExp(key, value, expire);  
        }  
      
        /** 
         * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换。 
         *  
         * @param key 
         * @param value 
         * @param expire 过期时间 New Date(1000*10)：十秒后过期 
         * @return 
         */  
        private static boolean setExp(String key, Object value, Date expire) {  
            boolean flag = false;  
            try {  
                flag = cachedClient.set(key, value, expire);  
            } catch (Exception e) {  
                // 记录Memcached日志  
                MemcachedLog.writeLog("Memcached set方法报错，key值：" + key + "\r\n" + exceptionWrite(e));  
            }  
            return flag;  
        }  
      
        /** 
         * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对。 
         * @param key 
         * @param value 
         * @return 
         */  
        public static boolean add(String key, Object value) {  
            return addExp(key, value, null);  
        }  
      
        /** 
         * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对。 
         * @param key 
         * @param value 
         * @param expire 过期时间 New Date(1000*10)：十秒后过期 
         * @return 
         */  
        public static boolean add(String key, Object value, Date expire) {  
            return addExp(key, value, expire);  
        }  
      
        /** 
         * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对。 
         * @param key 
         * @param value 
         * @param expire 过期时间 New Date(1000*10)：十秒后过期 
         * @return 
         */  
        private static boolean addExp(String key, Object value, Date expire) {  
            boolean flag = false;  
            try {  
                flag = cachedClient.add(key, value, expire);  
            } catch (Exception e) {  
                // 记录Memcached日志  
                MemcachedLog.writeLog("Memcached add方法报错，key值：" + key + "\r\n" + exceptionWrite(e));  
            }  
            return flag;  
        }  
      
        /** 
         * 仅当键已经存在时，replace 命令才会替换缓存中的键。 
         * @param key 
         * @param value 
         * @return 
         */  
        public static boolean replace(String key, Object value) {  
            return replaceExp(key, value, null);  
        }  
      
        /** 
         * 仅当键已经存在时，replace 命令才会替换缓存中的键。 
         * @param key 
         * @param value 
         * @param expire 过期时间 New Date(1000*10)：十秒后过期 
         * @return 
         */  
        public static boolean replace(String key, Object value, Date expire) {  
            return replaceExp(key, value, expire);  
        }  
      
        /** 
         * 仅当键已经存在时，replace 命令才会替换缓存中的键。 
         * @param key 
         * @param value 
         * @param expire 过期时间 New Date(1000*10)：十秒后过期 
         * @return 
         */  
        private static boolean replaceExp(String key, Object value, Date expire) {  
            boolean flag = false;  
            try {  
                flag = cachedClient.replace(key, value, expire);  
            } catch (Exception e) {  
                MemcachedLog.writeLog("Memcached replace方法报错，key值：" + key + "\r\n" + exceptionWrite(e));  
            }  
            return flag;  
        }  
      
        /** 
         * get 命令用于检索与之前添加的键值对相关的值。 
         * @param key 
         * @return 
         */  
        public static Object get(String key) {  
            Object obj = null;  
            try {  
                obj = cachedClient.get(key);  
            } catch (Exception e) {  
                MemcachedLog.writeLog("Memcached get方法报错，key值：" + key + "\r\n" + exceptionWrite(e));  
            }  
            return obj;  
        }  
      
        /** 
         * 删除 memcached 中的任何现有值。 
         * @param key 
         * @return 
         */  
        public static boolean delete(String key) {  
            return deleteExp(key, null);  
        }  
      
        /** 
         * 删除 memcached 中的任何现有值。 
         * @param key 
         * @param expire 过期时间 New Date(1000*10)：十秒后过期 
         * @return 
         */  
        public static boolean delete(String key, Date expire) {  
            return deleteExp(key, expire);  
        }  
      
        /** 
         * 删除 memcached 中的任何现有值。 
         * @param key 
         * @param expire 过期时间 New Date(1000*10)：十秒后过期 
         * @return 
         */  
        @SuppressWarnings("deprecation")
    	private static boolean deleteExp(String key, Date expire) {  
            boolean flag = false;  
            try {  
                flag = cachedClient.delete(key, expire);  
            } catch (Exception e) {  
                MemcachedLog.writeLog("Memcached delete方法报错，key值：" + key + "\r\n" + exceptionWrite(e));  
            }  
            return flag;  
        }  
      
        /** 
         * 清理缓存中的所有键/值对 
         * @return 
         */  
        public static boolean flashAll() {  
            boolean flag = false;  
            try {  
                flag = cachedClient.flushAll();  
            } catch (Exception e) {  
                MemcachedLog.writeLog("Memcached flashAll方法报错\r\n" + exceptionWrite(e));  
            }  
            return flag;  
        }  
      
        /** 
         * 返回异常栈信息，String类型 
         * @param e 
         * @return 
         */  
        private static String exceptionWrite(Exception e) {  
            StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            pw.flush();  
            return sw.toString();  
        }  
  

+ 4 测试方法:
    	@Test
        public void testMemcachedSpring() throws Exception { 
        	new ClassPathXmlApplicationContext("memcached/spring-memcached.xml");
            MemcachedUtils.set("aa", "bb", new Date(1000*10));  //10秒过期
            Object obj = MemcachedUtils.get("aa");  
            System.out.println("***************************");  
            System.out.println(obj.toString());  
            //sleep 15秒
            Thread.sleep(15000);
            System.out.println(MemcachedUtils.get("aa"));  //print null
        }  

