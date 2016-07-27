/**
 * CxfServer.java created at 2016年7月27日 下午2:55:10
 */
package com.mnuocom.distributedjava.distributedjava1.apachecxf;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * @author saxon
 */
public class CxfServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Business service = new BusinessImpl();
		JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
		factory.setServiceClass(Business.class);
		factory.setAddress("http://localhost:9527/business");
		factory.setServiceBean(service);
		factory.create();
		System.out.println("server started!");
	}

}
