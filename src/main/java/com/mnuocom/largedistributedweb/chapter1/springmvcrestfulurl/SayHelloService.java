/**
 * SayHelloService.java created at 2016年8月3日 下午1:15:13
 */
package com.mnuocom.largedistributedweb.chapter1.springmvcrestfulurl;

import java.util.Map;

/**
 * @author saxon
 */
public class SayHelloService implements BaseService {

	@Override
	public Object execute(Map<String, String[]> args) {
		String[] helloArg = (String[]) args.get("arg1");
		if("hello".equals(helloArg[0])){
			return "hello";
		}else{
			return "bye";
		}
	}

}
