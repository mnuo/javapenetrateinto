/**
 * Client.java created at Jul 26, 2016 11:32:28 PM
 */
package com.mnuocom.distributedjava.distributedjava1.webservice.client;

/**
 * @author saxon
 */
public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BusinessService businessService = new BusinessService();
		Business business = businessService.getBusinessPort();
		System.out.println(business.echo("hello world"));
		System.out.println(business.echo("quit"));
	}

}
