/**
 * ComparableDemo.java created at Jun 20, 2016 11:06:52 PM
 */
package com.mnuocom.javapenetrateinto.arraycomparate;

import java.util.Arrays;

/**
 * @author saxon
 * 要实现对象的比较排序,对象要实现comparable接口,并且实现conparaTo(Object o)方法
 */
public class ComparableDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Person[] arr = new Person[]{
							new Person("张三", 22),
							new Person("李四", 12),
							new Person("王五", 32),
							new Person("赵六", 21),
							new Person("前妻", 20)
						};
		Arrays.sort(arr);
		System.out.println(Arrays.toString(arr));
	}

}
class Person implements Comparable<Object>{
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


	@Override
	public String toString() {
		return "\n{name: " + this.name + " ,age: " + this.age + "}";
	}

	@Override
	public int compareTo(Object o) {
		Person person = (Person) o;
		return this.age - person.age;
	}
	
}