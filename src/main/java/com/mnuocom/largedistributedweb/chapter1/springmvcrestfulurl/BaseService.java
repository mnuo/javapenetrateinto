/**
 * BaseService.java created at 2016年8月3日 下午1:13:41
 */
package com.mnuocom.largedistributedweb.chapter1.springmvcrestfulurl;

import java.util.Map;

/**
 * @author saxon
 */
public interface BaseService {
	public Object execute(Map<String, String[]> parameters);
}
