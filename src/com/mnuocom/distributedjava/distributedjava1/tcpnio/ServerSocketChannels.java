/**
 * ServerSocketChannels.java created at 2016年7月26日 上午10:33:12
 */
package com.mnuocom.distributedjava.distributedjava1.tcpnio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author saxon
 * 在Java中可基于java.nio.channels中的Channel和Selector的相关类来实现,TCP/IP + NIO的
 * 系统间通信.Channel有SocketChannel和ServerSocketChannel两种,SocketChannel用于建立连接
 * 监听事件及操作读写,ServerSocketChannel用于监听端口及监听连接事件;程序通过Selector来获取是否有要处理的事件
 */
public class ServerSocketChannels {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		serverListen();
	}
	
	public static void serverListen() throws IOException{
		//打开选择器
		Selector selector = Selector.open();
		//打开服务器套接字通道
		ServerSocketChannel ssc = ServerSocketChannel.open();
		//检索与此通道关联的服务器套接字
		ServerSocket serverSocket = ssc.socket();
		//将 ServerSocket绑定到特定地址(IP 地址和端口号)
		serverSocket.bind(new InetSocketAddress(7889));
		System.out.println("server listen on port: 7889");
		
		//调整通道的阻塞模式
		ssc.configureBlocking(false);
		//向给定的选择器注册此通道,返回一个选择键,selection.op_accept -- 用于套接字操作的的操作集位
		//注册感兴趣的连接建立事件
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		
		while(true){
			//timeout:为正,则在等待某个通道准备就绪时最多阻塞timeout毫秒;如果为0则无限期阻塞;必须为负数
			int nKeys = selector.select(1000);
			if(nKeys > 0){
				for(SelectionKey key : selector.selectedKeys()){
					//测试此键的通道是否已准备好接受新的套接字连接--如果此键的通道不支持道街子接受操作,则此方法始终返回false
					if(key.isAcceptable()){
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel sc = server.accept();
						if(sc == null){
							continue;
						}
						sc.configureBlocking(false);
						sc.register(selector, SelectionKey.OP_READ);
					}else if(key.isReadable()){
						//分配一个新的字节缓冲区
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						SocketChannel sc = (SocketChannel) key.channel();
						int readBytes = 0;
						String message = null;
						try{
							int ret;
							try{
								while((ret = sc.read(buffer))> 0){
									readBytes += ret;
								}
							}catch(Exception e){
								readBytes = 0;
							}finally {
								//反转此缓冲区,首先对当前位置设置限制,然后将该位置设置为0
								buffer.flip();
							}
							if(readBytes > 0){
								message = Charset.forName("UTF-8").decode(buffer).toString();
								buffer = null;
							}
						}finally{
							if(buffer != null){
								buffer.clear();
							}
						}
						if(readBytes > 0){
							System.out.println("messge from client: " + message);
							if("quit".equalsIgnoreCase(message.trim())){
								sc.close();
								selector.close();
								System.out.println("Serverr has been shutdown!");
								System.exit(0);
							}
							String outMessage = "Server response: "+ message;
							sc.write(Charset.forName("UTF-8").encode(outMessage));
						}
					}
				}
				selector.selectedKeys().clear();
			}
		}
	}
}
