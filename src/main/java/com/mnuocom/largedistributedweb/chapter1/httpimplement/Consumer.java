/**
 * Consumer.java created at 2016年8月2日 下午2:17:21
 */
package com.mnuocom.largedistributedweb.chapter1.httpimplement;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author saxon
 */
public class Consumer {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Request request = new Request();
		request.setCommand("HELLO");
		request.setCommandLength(5);
		
		request.setEncode(Encode.UTF8.getValue());
		Socket client = new Socket("127.0.0.1", 1231);
		
		OutputStream output = client.getOutputStream();
		ProtocolUtil.writeRequest(output, request);
		
		InputStream input = client.getInputStream();
		Response response = ProtocolUtil.readResponse(input);
		
		System.out.println(response.getResponse());
		client.close();
	}

}
