/**
 * CxfClient.java created at 2016年7月27日 下午3:38:16
 */
package com.mnuocom.distributedjava.distributedjava1.apachecxf;

/**
 * @author saxon
 */
public class CxfClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JaxwsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(Business.class);
		factory.setAddress("http://localhost:9527/business");
		Business business = (Business)factory.create();
		business.echo("hello world");

	}

}
