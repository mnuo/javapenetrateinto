/**
 * ComparatorDemo.java created at Jun 20, 2016 11:14:01 PM
 */
package com.mnuocom.javapenetrateinto.arraycomparate;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author saxon
 * Comparator 接口是重新编写一个比较类实现Comparator接口,重写compare方法
 * Comparator和Comparable的区别:
 * 	1 Comparable 和 Comparator 都是实现对象排序的接口
 *  2 java.lang.Comparable 是类在定义的时候实现comparable接口,方法里面重写comparato
 *  3 java.util.Comparator 是当对象需要比较排序的时候单独定义一个比较规则类,这个类里面有compare()和equals方法
 * 
 */
public class ComparatorDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Person1[] arr = new Person1[]{
				new Person1("张三", 22),
				new Person1("李四", 12),
				new Person1("王五", 32),
				new Person1("赵六", 21),
				new Person1("前妻", 20)
			};
			Arrays.sort(arr, new PersonComparator());
			System.out.println(Arrays.toString(arr));

	}

}
class PersonComparator implements Comparator<Person1>{

	@Override
	public int compare(Person1 o1, Person1 o2) {
		return o1.getAge() - o2.getAge();
	}
	
}
class Person1{
	private String name;
	private int age;
	
	public Person1(String name, int age) {
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
}