/**
 * create at 2016年7月1日 上午9:31:00
 */
package com.mnuocom.javapenetrateinto.collectionset;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author mnuo
 */
public class SetDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<Person> treeSet = new TreeSet<Person>();
		treeSet.add(new Person("张三", 10));
		treeSet.add(new Person("李四", 10));
		treeSet.add(new Person("张三", 10));
		treeSet.add(new Person("王五", 20));
		treeSet.add(new Person("李四", 10));
		
		System.out.println(treeSet);
		
		Set<HashPerson> hashSet = new HashSet<HashPerson>();
		hashSet.add(new HashPerson(12, "wangwu"));
		hashSet.add(new HashPerson(11, "saxon"));
		hashSet.add(new HashPerson(10, "joke"));
		hashSet.add(new HashPerson(14, "Shabi"));
		hashSet.add(new HashPerson(13, "LiLi"));
		
		System.out.println(hashSet);
		
	}

}
class HashPerson {
	private String name;
	private int age;
	public HashPerson(int age, String name) {
		this.age = age;
		this.name = name;
	}
	public HashPerson(){
		
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashPerson other = (HashPerson) obj;
		if (age != other.age)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "HashPerson [name=" + name + ", age=" + age + "]";
	}
}
class Person implements Comparable<Person>{
	private String name;
	private int age;
	public Person(String name, int age){
		this.name = name;
		this.age = age;
	}
	public Person(){
		
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
	public int compareTo(Person o) {
		if(this.age > o.age){
			return 1;
		}else if (this.age < o.age){
			return -1;
		}else{
			return this.name.compareTo(o.name);
		}
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
}

