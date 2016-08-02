/**
 * SayHelloServiceImpl.java created at 2016年8月2日 上午11:34:38
 */
package com.mnuocom.largedistributedweb.chapter1.tcprpc;

/**
 * @author saxon
 */
public class SayHelloServiceImpl implements SayHelloService {

	@Override
	public String sayHello(String message) {
		if(!message.equals("bye")){
			return "hello," + message;
		}else{
			return "bye";
		}
	}

}
