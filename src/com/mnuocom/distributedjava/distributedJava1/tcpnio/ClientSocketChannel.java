/**
 * ClientSocketChannel.java created at 2016年7月26日 上午10:44:53
 */
package com.mnuocom.distributedjava.distributedJava1.tcpnio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author saxon
 */
public class ClientSocketChannel {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		client();

	}
	public static void client() throws IOException{
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);//设置为非阻塞模式
		
		SocketAddress remote = new InetSocketAddress("127.0.0.1", 7889);
		channel.connect(remote);//对于非阻塞模式,立即返回false,表示连接正在建立中
		Selector selector = Selector.open();
		channel.register(selector, SelectionKey.OP_CONNECT);//向channel注册selector以及感兴趣的连接事件
		//console输入
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			if(channel.isConnected()){
				String command = systemIn.readLine();
				channel.write(Charset.forName("UTF-8").encode(command));
				
				if(command == null || "quit".equalsIgnoreCase(command.trim())){
					systemIn.close();
					channel.close();
					selector.close();
					System.out.println("Client quit!");
					System.exit(0);
				}
			}
			//阻塞至有感兴趣的IO事件发生,或到达超时时间,如果希望一直等至感兴趣的io事件发生,可调用无参数的select方法,如果希望不阻塞直接返回目前是否有感兴趣的事件发生
			//可调用selectNow方法
			int nKeys = selector.select(1000);
			//如果nKeys大于0,说明有感兴趣的IO事件发生
			if(nKeys > 0){
				for(SelectionKey key : selector.selectedKeys()){
					if(key.isConnectable()){//对于发生连接的事件
						SocketChannel sc = (SocketChannel) key.channel();
						sc.configureBlocking(false);
						//注册感兴趣的IO读事件,通常不直接注册写事件,在发送缓冲区未满的情况下,一直是可写的,因此如注册了写事件,而又不用写数据,很容易造成CPU消耗100%现象
						sc.register(selector, SelectionKey.OP_READ);
						sc.finishConnect();//完成连接的建立
					}else if(key.isReadable()){//有流可读取
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						SocketChannel sc = (SocketChannel) key.channel();
						int readBytes = 0;
						try {
							int ret = 0;
							try {
								//读取目前可读的流,sc.read返回的为成功复制到bytebuffer中的字节数,此步为阻塞操作,值可能为0;当已经是流的结尾时,返回-1
								while((ret = sc.read(buffer))>0){
									readBytes += ret;
								}
							}finally{
								buffer.flip();
							}
							if(readBytes > 0){
								System.out.println(Charset.forName("UTF-8").decode(buffer).toString());
								buffer = null;
							}
						}finally {
							if(buffer != null){
								buffer.clear();
							}
						}
					}
				}
			}
			
		}
	}
}
