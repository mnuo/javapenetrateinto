/**
 * XMLDemo.java created at 2016年8月3日 上午10:17:15
 */
package com.mnuocom.largedistributedweb.chapter1.jsonxml;

import java.io.IOException;
import java.io.Serializable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author saxon
 */
public class XMLDemo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Person person = new Person("zhangsan","jiangsu");
		
		//将person对象转化成xml
		XStream stream = new XStream(new DomDriver());
		stream.alias("person", Person.class);
		String personXML = stream.toXML(person);
		System.out.println(personXML);
		/*FileWriter writer = new FileWriter(new File("d://person.xml"));
		writer.write(personXML);
		writer.flush();
		writer.close();*/
		Person person2 = (Person) stream.fromXML(personXML);
		System.out.println(person2.getName() + ","+ person2.getAddress());
	}

}
class Person implements Serializable{
	private static final long serialVersionUID = 3956104297699278267L;
	private String name;
	private String address;
	
	public Person(String name, String address) {
		this.name = name;
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}