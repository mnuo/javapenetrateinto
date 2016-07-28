/**
 * Client.java created at 2016年7月27日 下午2:11:36
 */
package com.mnuocom.distributedjava.distributedjava1.springrmi.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mnuocom.distributedjava.distributedjava1.springrmi.server.Business;

/**
 * @author saxon
 */
public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/mnuocom/distributedjava/distributedjava1/springrmi/client/client.xml");
		Business business = (Business) ac.getBean("businessService");
		
		System.out.println(business.echo("hello springRMI."));
		System.out.println(business.echo("quit"));
	}

}
