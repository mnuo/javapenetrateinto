/**
 * CloneDemo.java created at Jun 17, 2016 10:35:49 PM
 */
package com.mnuocom.javapenetrateinto.systemandclone;

/**
 * @author saxon
 * 
 * 实现克隆,类必须实现标记接口(没有定义任何抽象方法)cloneable
 * 并且重写clone方法:因为clone方法是Object方法且是properted类型
 */
public class CloneDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Person1 person1 = new Person1("zhangsan", 44);
			Person1 person11 = (Person1) person1.clone();
			System.out.println(person1);
			System.out.println(person11);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

}
class Person1 implements Cloneable{

	private String name;
	
	private int age;
	
	public Person1(String name, int age){
		this.name = name;
		this.age = age;
	}
	@Override
	public String toString() {
		return name + "===" + age;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
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
