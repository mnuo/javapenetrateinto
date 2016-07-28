/**
 * ServerDatagramSocket.java created at 2016年7月26日 下午2:38:05
 */
package com.mnuocom.distributedjava.distributedjava1.udpbio;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author saxon
 * UDP/IP + BIO 的网络数据传输同样采用Socket机制,只是UDP/IP下的Socket没有建立连接的要求,由于UDP/IP是无连接的,因此
 * 无法进行双向通信.这也就要求如果实现双向通信的话,必须两端都成为UDP server
 * 在java中可基于DatagramSocket和DatagramPacket来实现UDP/IP + BIO方式的通信,DatagramSocket负责监听端口及读写数据
 * DatagramPacket作为数据流对象进行传输
 */
public class ServerDatagramSocket {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		server();
	}
	
	public static void server() throws Exception{
		//由于UDP/IP是无连接的,如果希望双向通信,就必须启动一个监听端口,承担服务器的职责,如不能绑定到指定的端口,则抛出SocketException
		DatagramSocket serverSocket = new DatagramSocket(7889);
		byte[] buffer = new byte[65507];
		while(true){
			DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
			//阻塞并同步读取流信息,如接受到的流信息比packet长度长,则删除更长的信息,可通过调用DatagramSocket.setSoTimeout(以毫秒为单位的超时时间)来设置读取流的超时时间
			serverSocket.receive(receivePacket);
			String string = new String(receivePacket.getData());
			System.out.println("client: "+string);
			if("quit".equalsIgnoreCase(string.trim())){
				serverSocket.close();
				System.out.println("server shutdown!");
				break;
			}else{
				System.out.println("client not close");
			}
			buffer = new byte[65507];
		}
		
	}
}
