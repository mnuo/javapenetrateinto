/**
 * MemCachedManager.java created at 2016年8月11日 上午10:38:49
 */
package com.mnuocom.largedistributedweb.chapter2.memcached;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.schooner.MemCached.MemcachedItem;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * @author saxon
 */
public class MemCachedManager {
	public static MemCachedClient memCachedClient = new MemCachedClient();
	
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
	/**
	 * 新增缓存
	 * @param key
	 * @param obj
	 */
	public static void addCached(String key, Object obj){
		memCachedClient.add(key, obj);
	}
	/**
	 * 向缓存中添加值
	 * @param cachedKey
	 * @param obj
	 */
	public static void setIntoCached(String cachedKey, Object obj){
		memCachedClient.set(cachedKey, obj);
	}
	/**
	 * 获取指定缓存中的值
	 * @param key
	 * @return
	 */
	public static Object getCached(String key){
		return memCachedClient.get(key);
	}
	/**
	 * 或者多个指定缓存中的值
	 * @param keys
	 * @return
	 */
	public static Map<String, Object> getMultiCache(String[] keys){
		return memCachedClient.getMulti(keys);
	}
	/**
	 * 在对应的key的值前面增加前缀
	 * @param key
	 * @param object
	 */
	public static void prepend(String key, Object object){
		memCachedClient.prepend(key, object);
	}
	/**
	 * 在对应的key的值后面增加后缀
	 * @param key
	 * @param object
	 */
	public static void append(String key, Object object){
		memCachedClient.append(key, object);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
	}

}
