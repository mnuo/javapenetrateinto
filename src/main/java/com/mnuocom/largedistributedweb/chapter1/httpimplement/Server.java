/**
 * Server.java created at 2016年8月2日 下午2:51:34
 */
package com.mnuocom.largedistributedweb.chapter1.httpimplement;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author saxon
 */
public class Server {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(1231);
		while (true) {
			Socket client = server.accept();
			//读取响应数据
			InputStream input = client.getInputStream();
			Request request = ProtocolUtil.readRequest(input);
			
			OutputStream output = client.getOutputStream();
			Response response = new Response();
			response.setEncode(Encode.UTF8.getValue());
			
			if(request.getCommand().equals("HELLO")){
				response.setResponse("hello!");
			}else{
				response.setResponse("bye bye!");
			}
			response.setResponseLength(response.getResponse().length());
			ProtocolUtil.writeResponse(output, response);
			
			if(response.getResponse().equals("bye bye!")){
				server.close();
				break;
			}
		}

	}

}
