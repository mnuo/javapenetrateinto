/**
 * Client.java created at Jul 26, 2016 9:14:27 PM
 */
package com.mnuocom.distributedjava.distributedJava1.mina.tcp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @author saxon
 */
public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建客户端连接器.
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        
        // 设置连接超时检查时间
        connector.setConnectTimeoutCheckInterval(30);
        connector.setHandler(new IoHandlerAdapter(){
        	public void messageReceived(IoSession session, Object message) throws Exception {
                String content = message.toString();
                System.out.println("client receive a message is : " + content);
            }

            public void messageSent(IoSession session, Object message) throws Exception {
                System.out.println("messageSent -> ：" + message);
            }
        });
        
        // 建立连接
        ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1", 9123));
        // 等待连接创建完成
        cf.awaitUninterruptibly();
        
        cf.getSession().write("Hi Server!");
        cf.getSession().write("quit");
        
        // 等待连接断开
        cf.getSession().getCloseFuture().awaitUninterruptibly();
        // 释放连接
        connector.dispose();
	}

}
