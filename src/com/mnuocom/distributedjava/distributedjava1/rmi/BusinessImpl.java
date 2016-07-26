/**
 * BusinessImpl.java created at Jul 26, 2016 10:14:11 PM
 */
package com.mnuocom.distributedjava.distributedjava1.rmi;

import java.rmi.RemoteException;

/**
 * @author saxon
 * 实现Business接口的类
 */
public class BusinessImpl implements Business {
	@Override
	public String echo(String message) throws RemoteException {
		if("quit".equalsIgnoreCase(message.toString())){
			System.out.println("Server will be shutdown!");
			System.exit(0);
		}
		System.out.println("Message from client: " + message);
		return "Server response: " + message;
	}
}
