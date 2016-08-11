---
title: 大型分布式网站--2 分布式系统基础设施--memcached
date: 2016-08-05 16:12:08 
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


