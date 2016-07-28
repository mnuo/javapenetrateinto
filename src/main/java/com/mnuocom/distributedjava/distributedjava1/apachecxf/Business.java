/**
 * Business.java created at 2016年7月27日 下午2:50:25
 */
package com.mnuocom.distributedjava.distributedjava1.apachecxf;

import javax.jws.WebService;

/**
 * @author saxon
 */
@WebService
public interface Business {
	/**
	 * 显示客户端信息并返回
	 * @param message
	 * @return
	 */
	public String echo(String message);
}
