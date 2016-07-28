/**
 * UDPEchoServerSelector.java created at 2016年7月26日 下午4:09:12
 */
package com.mnuocom.distributedjava.distributedjava1.udpnio;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * @author saxon
 */
public class UDPEchoServerSelector {
	private static final int TIMEOUT = 3000;
	public static void main(String[] args) throws Exception {
		Selector selector = Selector.open();
		
		DatagramChannel channel = DatagramChannel.open();
		channel.configureBlocking(false);
		channel.socket().bind(new InetSocketAddress(7889));
		channel.register(selector, SelectionKey.OP_READ, new UDPEchoProtocol.ClientRecord());
		
		UDPEchoProtocol echoSelectorProtocol = new UDPEchoProtocol();
		
		while(true){
			if(selector.select(TIMEOUT) == 0){
				System.out.println(".");
				continue;
			}
			
			Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
			while(keyIter.hasNext()){
				SelectionKey key = keyIter.next();
				if(key.isReadable()){
					echoSelectorProtocol.handleRead(key);
				}
				if(key.isValid() && key.isWritable()){
					echoSelectorProtocol.handleWrite(key);
				}
				keyIter.remove();
			}
			
		}

	}

}
