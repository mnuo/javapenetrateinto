/**
 * Server.java created at 2016年7月27日 下午1:42:21
 */
package com.mnuocom.distributedjava.distributedjava1.springrmi.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author saxon
 */
public class Server {

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("com/mnuocom/distributedjava/distributedjava1/springrmi/server/server.xml");
		System.out.println("Server has been started!");
	}

}
