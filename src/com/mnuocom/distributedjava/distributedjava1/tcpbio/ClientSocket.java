/**
 * ClientSocket.java created at Jul 25, 2016 10:14:56 PM
 */
package com.mnuocom.distributedjava.distributedjava1.tcpbio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
		try {
			createConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void createConnection() throws UnknownHostException, IOException{
		//创建连接,如果域名解析不了会抛出UnknownHostException,当连接不上时,会抛出IOException,
		//如果希望控制建立的超时,可先调用 new Socket(),然后调用socket.connect(SocketAddress类型的目标地址,以毫秒为单位的超时时间)
		Socket socket = new Socket("127.0.0.1", 1001);
//		socket.setSoTimeout(2000);
		//创建读取服务器端返回流的BufferedReader
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		//向服务器写入的流 printWriter
		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		//向服务器发送字符串信息,要注意的是,此处即使写失败也不会抛出异常,并且一直阻塞到写入操作系统或网络IO 出现异常为止
		writer.println("hello");
		//阻塞读取服务器端返回的信息,以下待会会阻塞到服务器端返回信息,或网络IO出现异常为止,如果希望超过一段时间后不阻塞,那么要在创建socket对象后调用 socket.setSoTimeout
		String returnStr = reader.readLine();
		System.out.println("client: " + returnStr);
	}
	
	public static void createConnection1() throws UnknownHostException, IOException{
		//向本机的4700端口发出客户请求
		Socket socket=new Socket("127.0.0.1",4700);

		//由系统标准输入设备构造BufferedReader对象
		BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));

		//由Socket对象得到输出流，并构造PrintWriter对象
		PrintWriter os=new PrintWriter(socket.getOutputStream());

		//由Socket对象得到输入流，并构造相应的BufferedReader对象
		BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));

		String readline;
		readline=sin.readLine(); //从系统标准输入读入一字符串

		//若从标准输入读入的字符串为 "bye"则停止循环
		while(!readline.equals("bye")){
			//将从系统标准输入读入的字符串输出到Server
			os.println(readline);

			//刷新输出流，使Server马上收到该字符串
			os.flush();

			//在系统标准输出上打印读入的字符串
			System.out.println("Client:"+readline);

			//从Server读入一字符串，并打印到标准输出上
			System.out.println("Server:"+is.readLine());

			readline=sin.readLine(); //从系统标准输入读入一字符串
		} //继续循环
		os.close(); //关闭Socket输出流
		is.close(); //关闭Socket输入流
		socket.close(); //关闭Socket
	}
}
