/**
 * DefaultHelloWorld.java created at 2016年7月28日 上午9:51:20
 */
package com.mnuocom.distributedjava.distributedjava2.tuscany;

/**
 * @author saxon
 */
public class DefaultHelloWorld implements HelloWord {

	@Override
	public String sayHello(String name) {
		System.out.println("Server receive: " + name);
		return "Server response: hello " + name;
	}

}
