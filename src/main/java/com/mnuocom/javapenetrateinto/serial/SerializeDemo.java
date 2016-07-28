/**
 * create at 2016年6月30日 下午4:38:55
 */
package com.mnuocom.javapenetrateinto.serial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * @author mnuo
 * <p>
 * 	演示ObjectInputStream ObjectOutputSteam操作
 * 	关键字： transient
 * </p>
 */
public class SerializeDemo {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		/*Person person = new Person("zhangsan",19);
		File file = new File("/home/mnuo/object/person");
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/home/mnuo/object/person"));
		
		oos.writeObject(person);
		
		oos.flush();
		oos.close();*/
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("/home/mnuo/object/person")));
		Object object = ois.readObject();
		System.out.println(object.toString());
		ois.close();
		
	}

}
class Person implements Serializable{
	private static final long serialVersionUID = 1251598589287415311L;
	
	private String name;
	private transient int age;
	
	public Person() {
	}
	public Person(String name, int age){
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
		return "person:[ name:" + this.name + ", age:" + this.age + "]"; 
	}
}
