/**
 * CurrentHashMapDemo.java created at Jul 30, 2016 9:41:49 PM
 */
package com.mnuocom.distributedjava.distrubutedjava3currentpacket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
//		cmap.put(null, "2aa");
//		cmap.put("b", null);
		
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
