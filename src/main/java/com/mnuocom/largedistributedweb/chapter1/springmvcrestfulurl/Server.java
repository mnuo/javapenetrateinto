/**
 * Server.java created at 2016年8月3日 下午3:24:44
 */
package com.mnuocom.largedistributedweb.chapter1.springmvcrestfulurl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author saxon
 */
@Controller("/")
public class Server {
	@InitBinder
	public void initBinder(WebDataBinder bind){
		//设置日期转换
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		dateFormat.setLenient(false);
		bind.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	@ResponseBody
	@RequestMapping(value="/provider/{servicename}/{timestamp}", method=RequestMethod.POST)
	public JsonResult provider(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("servicename") String servicename, @PathVariable("timestamp")
			Date timestamp) throws Exception{
		
		Map<String, String[]> parameters = request.getParameterMap();
		Class<?> clazz = Class.forName("com.mnuocom.largedistributedweb.chapter1.springmvcrestfulurl.SayHelloService");
		BaseService service = (BaseService) clazz.newInstance();
		
		//BaseService service = serviceMap.get(servicename);
		Object result = service.execute(parameters);
		
		//生成JSON结果
		JsonResult jsonResult = new JsonResult();
		jsonResult.setMessage("success");
		jsonResult.setResult(result);
		jsonResult.setResultCode(200);
		
		return jsonResult;
	}
}
