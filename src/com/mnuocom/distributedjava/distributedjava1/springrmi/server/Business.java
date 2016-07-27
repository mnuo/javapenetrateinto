/**
 * Business.java created at 2016年7月27日 下午1:26:36
 */
package com.mnuocom.distributedjava.distributedjava1.springrmi.server;

/**
 * @author saxon
 */
public interface Business {
	/**
	 * 显示客户端提供的信息,并返回
	 * @param message
	 * @return
	 */
	public String echo(String message);
}
