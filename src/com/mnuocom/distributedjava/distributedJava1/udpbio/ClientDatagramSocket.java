/**
 * ClientDatagramSocket.java created at 2016年7月26日 下午2:53:39
 */
package com.mnuocom.distributedjava.distributedJava1.udpbio;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;

/**
 * @author saxon
 */
public class ClientDatagramSocket {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		client();
	}
	
	public static void client() throws Exception{
		//由于UDP/IP是无连接的,如果希望双向通信,就必须启动一个监听端口,承担服务器的职责,如不能绑定到指定的端口,则抛出SocketException
		DatagramSocket socket = new DatagramSocket();
		SocketAddress remote = new InetSocketAddress("127.0.0.1", 7889);
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		while(true){
			DatagramPacket packet = new DatagramPacket(line.getBytes(), line.getBytes().length, remote);
			socket.send(packet);
			if("quit".equalsIgnoreCase(line.trim())){
				socket.close();
				scanner.close();
				System.exit(0);
			}
			line = scanner.nextLine();
		}
		//阻塞发送packet,到指定的服务器和端口,当出现网络IO异常时抛出IOException,当连不上目标地址和端口时,抛出PortUnreachableException
	}
}
