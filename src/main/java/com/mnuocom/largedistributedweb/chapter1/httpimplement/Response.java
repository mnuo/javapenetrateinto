/**
 * Response.java created at 2016年8月2日 下午2:15:41
 */
package com.mnuocom.largedistributedweb.chapter1.httpimplement;

/**
 * @author saxon
 */
public class Response {
	//编码
	private byte encode;
	//响应长度
	private int responseLength;
	//响应
	private String response;
	
	public byte getEncode() {
		return encode;
	}
	public void setEncode(byte encode) {
		this.encode = encode;
	}
	public int getResponseLength() {
		return responseLength;
	}
	public void setResponseLength(int responseLength) {
		this.responseLength = responseLength;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
}
