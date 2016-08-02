/**
 * Server.java created at 2016年8月2日 上午11:33:36
 */
package com.mnuocom.largedistributedweb.chapter1.tcprpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author saxon
 */
public class Server {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		HashMap<String, Object> services = new HashMap<>();
		services.put(SayHelloService.class.getName(), new SayHelloServiceImpl());
		ServerSocket server = new ServerSocket(1212);
		Socket socket = server.accept();
			
		while(true){
			//读取服务信息
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			String interfacename = inputStream.readUTF();//接口名称
			String methodName = inputStream.readUTF();//方法名称
			Class<?>[] parameterTypes = (Class<?>[]) inputStream.readObject();
			Object[] arguments = (Object[]) inputStream.readObject();
			
			//执行调用
			Class<?> serviceinterfaceclass = Class.forName(interfacename);
			Object service = services.get(interfacename);
			Method method = serviceinterfaceclass.getMethod(methodName, parameterTypes);
			Object result = method.invoke(service, arguments);
			
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(result);
			if(result.toString().equals("bye")){
				server.close();
				break;
			}
		}

	}

}
