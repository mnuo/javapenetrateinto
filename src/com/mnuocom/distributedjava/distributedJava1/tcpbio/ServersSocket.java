/**
 * ServerSocket.java created at Jul 25, 2016 10:07:21 PM
 */
package com.mnuocom.distributedjava.distributedJava1.tcpbio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author saxon
 */
public class ServersSocket {
	public static void main(String[] args) {
		try {
			serverSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		serverSocket1();
	}
	public static void serverSocket() throws IOException{
		//创建对本地指定端口的监听,如端口冲突则抛出SocketException,其他网络IO方面的异常则抛出IOException
		ServerSocket server = new ServerSocket(1001);
		// 接受客户端及监理连接的请求,并返回Socket对象,以便和客户端进行交互,交互的方式和客户端相同,也是通过Socket.getInputStream和Socket.getOutputStream来进行读写操作,
		//此方法会 一直阻塞到有客户端发送建立连接的请求,如果希望此方法最多阻塞一定的时间,则要在创建ServerSocket后调用其setSoTimeout
		Socket socket = server.accept();
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String receive = reader.readLine();
		System.out.println("accereceiver: " + receive);
		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		writer.println("fuck you");
	}
	
	public static void serverSocket1(){
		try{
			ServerSocket server=null;
			try{
				//创建一个ServerSocket在端口4700监听客户请求
				server=new ServerSocket(4700);
			}catch(Exception e) {
				//出错，打印出错信息
				System.out.println("can not listen to:"+ e.toString());
			}
			Socket socket=null;
			try{
				//使用accept()阻塞等待客户请求，有客户
				//请求到来则产生一个Socket对象，并继续执行
				socket=server.accept();
			}catch(Exception e) {
				//出错，打印出错信息
				System.out.println("Error."+e.toString());
			}
			String line = "";
			//由Socket对象得到输入流，并构造相应的BufferedReader对象
			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//由Socket对象得到输出流，并构造PrintWriter对象
			PrintWriter os=new PrintWriter(socket.getOutputStream());
			//由系统标准输入设备构造BufferedReader对象
			BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
			//在标准输出上打印从客户端读入的字符串
			System.out.println("Client:"+is.readLine());
			//从标准输入读入一字符串
			line=sin.readLine();
			//如果该字符串为 "bye"，则停止循环
			while(!line.equals("bye")){
				//向客户端输出该字符串
				os.println(line);
				//刷新输出流，使Client马上收到该字符串
				os.flush();

				//在系统标准输出上打印读入的字符串
				System.out.println("Server:"+line);
				//从Client读入一字符串，并打印到标准输出上
				System.out.println("Client:"+is.readLine());

				//从系统标准输入读入一字符串
				line=sin.readLine();
			}
			//继续循环
			os.close(); //关闭Socket输出流
			is.close(); //关闭Socket输入流
			socket.close(); //关闭Socket
			server.close(); //关闭ServerSocket
		}catch(Exception e){
			//出错，打印出错信息
			System.out.println("Error:"+e.toString());

		}
	}
}

