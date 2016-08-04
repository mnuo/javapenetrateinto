/**
 * LoadBalancingAlgorithmDemo.java created at 2016年8月4日 上午10:20:27
 */
package com.mnuocom.largedistributedweb.chapter1.loadbalancing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author saxon
 */
public class LoadBalancingAlgorithmDemo {
	public static Map<String, Integer> map = new HashMap<>();
	public static Integer pos = 0;
	static {
		map.put("192.167.0.1", 1);
		map.put("192.167.0.2", 1);
		map.put("192.167.0.3", 4);
		map.put("192.167.0.4", 1);
		map.put("192.167.0.5", 2);
		map.put("192.167.0.6", 3);
		map.put("192.167.0.7", 3);
		map.put("192.167.0.8", 1);
	}
	
	public static void main(String[] args) {
		System.out.println(map);
		//System.out.println(roundRobin());
		//System.out.println(testRandom());
		//System.out.println(testHash("192.168.0.3"));
//		System.out.println(testWeightRoundRobin());
		System.out.println(testWeightRandom());
	}
	/**
	 * 轮询法
	 * @return
	 */
	public static String roundRobin(){
		//重新创建一个map,避免出现由于服务器上线和下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(map);
		
		//取得IP地址list
		Set<String> keyset = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<>();
		keyList.addAll(keyset);
		
		String server = null;
		
		synchronized (pos){
			if(pos >= keyset.size()){
				pos = 0;
			}
			server = keyList.get(pos);
			pos ++;
		}
		return server;
	}
	/**
	 * 随机法
	 * @return
	 */
	public static String testRandom(){
		//重新创建一个map,避免出现由于服务器上线和下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(map);
		
		//取得IP地址list
		Set<String> keyset = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<>();
		keyList.addAll(keyset);
		
		Random random = new Random();
		int randomPos = random.nextInt(keyList.size());
		String server = keyList.get(randomPos);
		return server;
	}
	/**
	 * 原地址Hashmap
	 * @param remoteip
	 * @return
	 */
	public static String testHash(String remoteip){
		//重新创建一个map,避免出现由于服务器上线和下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(map);
		
		//取得IP地址list
		Set<String> keyset = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<>();
		keyList.addAll(keyset);
		
		int hashCode = remoteip.hashCode();
		int serverListSize = keyList.size();
		int serverPost = hashCode % serverListSize;
		
		return keyList.get(serverPost);
	}
	/**
	 * 加权轮询法
	 * @return
	 */
	public static String testWeightRoundRobin(){
		//重新创建一个map,避免出现由于服务器上线和下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(map);
		
		//取得IP地址list
		Set<String> keyset = serverMap.keySet();
		Iterator<String> iterator = keyset.iterator();
		ArrayList<String> serverList = new ArrayList<>();
		while(iterator.hasNext()){
			String server = iterator.next();
			Integer weight = serverMap.get(server);
			for(int i = 0; i < weight; i ++){
				serverList.add(server);
			}
		}
		String server = null;
		
		synchronized (pos) {
			if(pos >= serverList.size()){
				pos = 0;
			}
			server = serverList.get(pos);
			pos ++;
		}
		return server;
	}
	/**
	 * 加权随机法
	 * @return
	 */
	public static String testWeightRandom(){
		//重新创建一个map,避免出现由于服务器上线和下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<>();
		serverMap.putAll(map);
		
		//取得IP地址list
		Set<String> keyset = serverMap.keySet();
		Iterator<String> iterator = keyset.iterator();
		ArrayList<String> serverList = new ArrayList<>();
		while(iterator.hasNext()){
			String server = iterator.next();
			Integer weight = serverMap.get(server);
			for(int i = 0; i < weight; i ++){
				serverList.add(server);
			}
		}
		Random random = new Random();
		int randomPos = random.nextInt(serverList.size());
		String server = serverList.get(randomPos);
		return server;
	}
}

