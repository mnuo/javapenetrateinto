/**
 * Client.java created at Jul 26, 2016 10:19:27 PM
 */
package com.mnuocom.distributedjava.distributedjava1.rmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author saxon
 * 基于客户端的代码
 */
public class Client {

	/**
	 * RMI 的客户端首先通过LocateRegistry.getRegistry来获取Registry对象,然后通过Registry.lookup字符串获取要调用的服务器端接口
	 * 的实例对象,最后以接口的方式透明的调用远程对象的方法
	 * @param args
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws AccessException 
	 */
	public static void main(String[] args) throws AccessException, RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry("127.0.0.1");
		String name = "BusinessDemo";
		//创建BusinessDemo类的代理类,当调用时,则调用127.0.0.1:1099上名称为BisinessDemo的对象
		//如服务器端没有对应名称的绑定,则抛出NotBoundException;如通信出现错误,则抛出RemoteException
		Business business = (Business) registry.lookup(name);
		System.out.println(business.echo("hello word"));
		System.out.println(business.echo("quit"));
	}

}
