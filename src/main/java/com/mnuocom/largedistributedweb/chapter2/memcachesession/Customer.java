/**
 * Customer.java created at 2016年8月3日 下午1:18:45
 */
package com.mnuocom.largedistributedweb.chapter2.memcachesession;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * @author saxon
 */
@Controller
public class Customer {
	@RequestMapping(value="/consumer")
	public String consume(HttpServletRequest request) throws ClientProtocolException, IOException{
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") == null)
			session.setAttribute("user", "zhangsan");
		
		return "index";
	}
}
