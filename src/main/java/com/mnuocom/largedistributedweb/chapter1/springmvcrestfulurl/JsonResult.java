/**
 * JsonResult.java created at 2016年8月3日 下午1:20:17
 */
package com.mnuocom.largedistributedweb.chapter1.springmvcrestfulurl;

/**
 * @author saxon
 */
public class JsonResult {
	//结果状态码
	private int resultCode;
	
	//状态码解释消息
	private String message;
	
	//结果
	private Object result;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
