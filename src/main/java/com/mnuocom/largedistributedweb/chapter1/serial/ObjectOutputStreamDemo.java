/**
 * ObjectOutputStreamDemo.java created at 2016年8月2日 上午10:46:02
 */
package com.mnuocom.largedistributedweb.chapter1.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author saxon
 * 传统的java系列化:ObjectOutputSream
 */
public class ObjectOutputStreamDemo {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//序列化,将对象存入ByteArrayOutputStream,即写入内存中,也可以存入文件中
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		
		oos.writeObject(new Persion("张三"));
		byte[] zhangsan = bos.toByteArray();
		
		//反序列化,将对象从内存中,读取出来
		ByteArrayInputStream bis = new ByteArrayInputStream(zhangsan);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Persion object = (Persion) ois.readObject();
		System.out.println(object.getName());
	}
}
class Persion implements Serializable{
	private static final long serialVersionUID = 3956104297699278267L;
	private String name;
	
	public Persion(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}