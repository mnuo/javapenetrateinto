/**
 * MapDemo.java created at 2016年7月2日 上午10:37:20
 */
package com.mnuocom.javapenetrateinto.map;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * @author saxon
 * <p>
 * 	Map接口,保存键值对,保存的键是不可重复:<br>
 *  HashMap:保存内容是无序的;是非线程安全的,异步执行,性能相对HashTable高;键值对中都可以保存null<br>
 *  HashTable:保存内容是无序的;是线程安全的,同步执行,性能相对HashMap差;键值对中都不可以可以保存null<br>
 *  TreeMap:保存内容是有序的,根据key排序,key不能保存null<br>
 * </p>
 * 
 * 
 */
public class MapDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, Person> map = new HashMap<String, Person>();
		map.put("zhangsan", new Person("zhangsan", 22));
		map.put("lisi", new Person("lisi", 12));
		map.put("wangwu", new Person("wangwu", 23));

		Iterator<Entry<String, Person>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Person> entry = iterator.next();
			System.out.println(entry.getKey() + "-->" + entry.getValue().getAge());
		}
		
		Map<String, String> hashtable = new Hashtable<String,String>();
		//hashtable.put(null, "112");
		//hashtable.put("valunull", null);
		hashtable.put("valunul1l", "nul");
		System.out.println(hashtable);
		
		Map<String, String> treeMap = new TreeMap<String,String>();
		//treeMap.put(null, "112");
		treeMap.put("valunull", null);
		treeMap.put("valunul1l", "nul");
		System.out.println(treeMap);
	}

}
class Person{
	private String name;
	private int age;
	
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}