/**
 * Server.java created at Jul 26, 2016 10:16:13 PM
 */
package com.mnuocom.distributedjava.distributedjava1.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author saxon
 * 基于RMI的服务端
 * RMI服务器端通过启动RMI注册对象在一个端口上监听对外提供的接口,其实现实例以字符串的方式绑定到RMI注册对象上。RMI客户端通过proxy的方式
 * 代理了对服务器端接口的访问,RMI客户端将要访问的服务器端对象字符串,方法和和参数封装个一个对象,序列化成流后通过TCP/IP + BIO 传输到RMI服务器端
 * RMI服务器端接收到客户端的请求对象后,解析其中的对象字符串,方法及参数,通过对象字符串从RMI注册对象上找到提供业务功能的实例,之后结合要访问的方法来反射获取到方法实例对象,
 * 传入参数完成对服务器端对象实例的调用,返回的结果则序列化为流以TCP/IP + BIO 方式返回给客户端,客户端再接受到此流后反序列化为对象,并返回给调用者
 */
public class Server {
	/**
	 * RMI 要求服务器端的接口继承Remote接口,接口上的美中方法必须抛出RemoteException,服务器端业务类通过实现此接口提供业务功能
	 * 然后通过调用UnicastRemoteObject.exportObject来将此对象绑定到某端口上,最后将此对象注册到本地的LocatRegistry上,此时形成一个字符串对应于
	 * 对象实例的映射关系
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int port = 9527;
		String name = "BusinessDemo";
		Business business = new BusinessImpl();
		UnicastRemoteObject.exportObject(business, port);
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.rebind(name, business);
	}
}
