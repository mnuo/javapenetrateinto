/**
 * Client.java created at 2016年8月2日 上午11:33:25
 */
package com.mnuocom.largedistributedweb.chapter1.tcprpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author saxon
 */
public class Client {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//接口名称
		String interfacename = SayHelloService.class.getName();
		//需要远程执行的方法
		Method method = SayHelloService.class.getMethod("sayHello", java.lang.String.class);
		int i = 0;
		//需要传递到远端的参数
		Socket socket = new Socket("127.0.0.1", 1212);
		//将方法名和参数传递到远端
		while (true) {
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			Object[] arguments = {"hello" + i ++};
			output.writeUTF(interfacename);
			output.writeUTF(method.getName());
			output.writeObject(method.getParameterTypes());
			output.writeObject(arguments);
			
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			Object result = inputStream.readObject();
			
			System.out.println(result);
			
			if(result.toString().equals("bye")){
				System.out.println("bye");
				socket.close();
				break;
			}
			Thread.sleep(1000);
		}
	}

}
