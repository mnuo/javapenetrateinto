/**
 * BusinessImpl.java created at Jul 26, 2016 10:47:20 PM
 */
package com.mnuocom.distributedjava.distributedjava1.webservice;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
/**
 * @author saxon
 */
@WebService(name="Business",serviceName="BusinessService",targetNamespace="http://"
		+ "distributedjava.mnuocom.com/distributedjava1/webservice/client")
@SOAPBinding(style=SOAPBinding.Style.RPC)
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
