/**
 * ProtocolUtil.java created at 2016年8月2日 下午2:57:59
 */
package com.mnuocom.largedistributedweb.chapter1.httpimplement;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author saxon
 * OutputStream 中直接写入一个int类型,会截取其低8位,丢弃其高24位,因此,需要将基本类型先转换为字节流.Java采用的BigEndian字节序(字节在电脑
 * 中存放的序列与输入(输出)时的序列是先到的在前,还是厚道的在前).所有的网络协议也是采用Big Endian字节序来进行传输.因此,我们在进行数据的传输时,需要现将其
 * 转换成Big Endian字节序;同理,在数据接收时,也需要进行相应的转换
 */
public class ProtocolUtil {
	public static Request readRequest(InputStream input) throws IOException{
		//读取编码
		byte[] encodeByte = new byte[1];
		input.read(encodeByte);
		byte encode = encodeByte[0];
		
		//读取命令长度
		byte[] commandLengthBytes = new byte[4];
		input.read(commandLengthBytes);
		int commandLength = ByteUtil.bytes2Int(commandLengthBytes);
		
		//读取命令
		byte[] commandBytes = new byte[commandLength];
		input.read(commandBytes);
		String command = "";
		if(Encode.GBK.getValue() == encode){
			command = new String(commandBytes, "GBK");
		}else{
			command = new String(commandBytes, "UTF8");
		}
		
		//组装请求返回
		Request request = new Request();
		request.setCommand(command);
		request.setEncode(encode);
		request.setCommandLength(commandLength);
		return request;
	}
	
	public static void writeResponse(OutputStream output, Response response) throws IOException{
		//将response响应返回给客户端
		output.write(response.getEncode());
		//output.write(response.getResponseLength());直接write一个int类型,会截取低8位传输,丢弃高24位
		output.write(ByteUtil.int2ByteArray(response.getResponseLength()));
		
		if(Encode.GBK.getValue() == response.getEncode()){
			output.write(response.getResponse().getBytes("GBK"));
		}else{
			output.write(response.getResponse().getBytes("UTF8"));
		}
		output.flush();
	}

	public static void writeRequest(OutputStream output, Request request) throws Exception {
		//将response响应返回给客户端
		output.write(request.getEncode());
		//output.write(response.getResponseLength());直接write一个int类型,会截取低8位传输,丢弃高24位
		output.write(ByteUtil.int2ByteArray(request.getCommandLength()));
		
		if(Encode.GBK.getValue() == request.getEncode()){
			output.write(request.getCommand().getBytes("GBK"));
		}else{
			output.write(request.getCommand().getBytes("UTF8"));
		}
		output.flush();
	}

	public static Response readResponse(InputStream input) throws IOException {
		//读取编码
		byte[] encodeByte = new byte[1];
		input.read(encodeByte);
		byte encode = encodeByte[0];
		
		//读取命令长度
		byte[] responseLengthBytes = new byte[4];
		input.read(responseLengthBytes);
		int responseLength = ByteUtil.bytes2Int(responseLengthBytes);
		
		//读取命令
		byte[] responseBytes = new byte[responseLength];
		input.read(responseBytes);
		String responseStr = "";
		if(Encode.GBK.getValue() == encode){
			responseStr = new String(responseBytes, "GBK");
		}else{
			responseStr = new String(responseBytes, "UTF8");
		}
		
		//组装请求返回
		Response response = new Response();
		response.setResponse(responseStr);
		response.setEncode(encode);
		response.setResponseLength(responseLength);
		return response;
	}
}
