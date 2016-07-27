/**
 * BusinessImpl.java created at 2016年7月27日 下午2:52:52
 */
package com.mnuocom.distributedjava.distributedjava1.apachecxf;

import javax.jws.WebService;

/**
 * @author saxon
 */
@WebService(serviceName="BusinessService", endpointInterface="com.mnuocom.distributedjava.distributedjava1.apachecxf.Business")
public class BusinessImpl implements Business {
	@Override
	public String echo(String message) {
		if("quit".equalsIgnoreCase(message.toString())){
			System.out.println("Server will be shutdown!");
			System.exit(0);
		}
		System.out.println("Message from client: " + message);
		return "Server response: " + message;
	}

}
