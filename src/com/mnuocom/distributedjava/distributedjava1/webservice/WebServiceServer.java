/**
 * WebServiceServer.java created at Jul 26, 2016 11:03:34 PM
 */
package com.mnuocom.distributedjava.distributedjava1.webservice;

import javax.xml.ws.Endpoint;

/**
 * @author saxon
 */
public class WebServiceServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:9527/BusinessService", new BusinessImpl());
		System.out.println("Server has been started");
	}

}
