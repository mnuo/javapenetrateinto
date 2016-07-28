/**
 * UDPEchoProtocol.java created at 2016年7月26日 下午3:45:33
 */
package com.mnuocom.distributedjava.distributedjava1.udpnio;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

/**
 * @author saxon
 * 在JAVA中可通过DatagramChannel和byteBuffer来实现UDP/IP + NIO方式的系统间通信,DatagramChannel
 * 负责监听端口及进行读写,ByteBuffer则用于数据流传输
 * 
 */
public class UDPEchoProtocol implements EchoProtocol {
	private static final int ECHOMAX = 255;//maxnum size of echo datagram	
	
	static class ClientRecord{
		public SocketAddress clientAddress;
		public ByteBuffer buffer = ByteBuffer.allocate(ECHOMAX);
	}
	@Override
	public void handleAccept(SelectionKey selectionKey) throws IOException {

	}

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		DatagramChannel channel = (DatagramChannel) key.channel();
		ClientRecord record = (ClientRecord) key.attachment();
		record.buffer.clear();//prepare buffer for receive
		record.clientAddress = channel.receive(record.buffer);
		if(record.clientAddress != null){
			key.interestOps(SelectionKey.OP_WRITE);
		}
	}

	@Override
	public void handleWrite(SelectionKey key) throws IOException {
		DatagramChannel channel = (DatagramChannel) key.channel();
		ClientRecord record = (ClientRecord) key.attachment();
		record.buffer.clear();
		int bytesSent = channel.send(record.buffer, record.clientAddress);
		if(bytesSent != 0){//Buffer completely written?
			//No longer interested in writers
			key.interestOps(SelectionKey.OP_READ);
		}
		
	}
}
