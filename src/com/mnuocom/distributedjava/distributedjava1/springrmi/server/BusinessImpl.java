/**
 * BusinessImpl.java created at 2016年7月27日 下午1:27:46
 */
package com.mnuocom.distributedjava.distributedjava1.springrmi.server;

/**
 * @author saxon
 * 服务端实现类
 */
public class BusinessImpl implements Business{
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
