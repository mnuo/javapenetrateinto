/**
 * ClientSocket.java created at Jul 25, 2016 10:14:56 PM
 */
package com.mnuocom.distributedjava.distributedJava1.tcpbio;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author saxon
 */
public class ClientSocket {

	/**
	 * 
	 */
	public ClientSocket() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void createConnection() throws UnknownHostException, IOException{
		//创建连接,如果域名解析不了会抛出UnknownHostException,当连接不上时,会抛出IOException,
		//如果希望控制建立的超时,可先调用 new Socket(),然后调用socket.connect(SocketAddress类型的目标地址,以毫秒为单位的超时时间)
		Socket socket = new Socket("localhost", 1001);
	}
}
