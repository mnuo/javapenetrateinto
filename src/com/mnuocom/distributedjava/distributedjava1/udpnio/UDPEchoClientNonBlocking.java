/**
 * UDPEchoClientNonBlocking.java created at 2016年7月26日 下午3:53:48
 */
package com.mnuocom.distributedjava.distributedjava1.udpnio;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author saxon
 */
public class UDPEchoClientNonBlocking {
	private static final int TIMEOUT = 3000;//resend timeout (milliseconds)
	private static final int MAXTRIES = 255;//mM
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// 输入数据
		byte[] bytesTosend = "0112345566777aaaddffs".getBytes();
		//创建channel 
		DatagramChannel datagramChannel = DatagramChannel.open();
		datagramChannel.configureBlocking(false);
		datagramChannel.socket().setSoTimeout(TIMEOUT);
		
		ByteBuffer write = ByteBuffer.wrap(bytesTosend);
		ByteBuffer read = ByteBuffer.allocate(MAXTRIES);
		
		datagramChannel = datagramChannel.connect(new InetSocketAddress("127.0.0.1", 7889));
		
		int receive = 0;//total bytes received so far
		int bytesReceive;//Bytes receive in last read
		
		while (receive < bytesTosend.length) {
			if(write.hasRemaining()){
				datagramChannel.write(write);
			}
			if((bytesReceive = datagramChannel.read(read))==-1){
				throw new SocketException("Connection closed premature");
			}
			receive = bytesReceive;
			System.out.println(".");//Do somethine else
		}
		System.out.println("received: "+ new String(read.array(),0, receive, "utf-8"));
		datagramChannel.close();

	}

}
