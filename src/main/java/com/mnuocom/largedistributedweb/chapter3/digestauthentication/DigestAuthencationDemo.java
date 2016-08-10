/**
 * DigestAuthencationDemo.java created at 2016年8月10日 上午9:58:39
 */
package com.mnuocom.largedistributedweb.chapter3.digestauthentication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.mnuocom.largedistributedweb.chapter3.numbersummer.NusumUtil;
import com.mnuocom.largedistributedweb.chapter3.symmetricencryption.SymEncUtil;

/**
 * @author saxon
 */
public class DigestAuthencationDemo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//1 客户端参数摘要生成
		Map<String, String> params = new HashMap<>();
		params.put("username", "zhangsan");
		params.put("password", "123123");
		String digest = getDigest(params);//生成的摘要
		System.out.println("客户端生成的摘要: " + digest);
		
		//2  服务端参数摘要校验
		boolean serverResult = validate(params, digest);
		System.out.println("服务端参数摘要校验结果: " + serverResult);
		
		//3  服务器端响应摘要生成
		String content = "登录成功";
		String serverdigest = getDigest(content);
		System.out.println(" 服务器端响应摘要: " + serverdigest);
		
		//4 客户端响应摘要检验
		boolean clientResult = validate(content, serverdigest);
		System.out.println("客户端响应摘要检验结果: "+ clientResult);
	}
	/**
	 * 1 客户端参数摘要生成:
	 * 	请求参数需要先排好序,服务端与客户端需要事先约定好排序的方式,否则生成的摘要可能就相差了,排好序后,将参数名称与
	 * 参数值串起来,加上约定好的secret,生成带摘要的字符串,最后使用如MD5之类的摘要算法生成摘要串,当然摘要算法也
	 * 需要事先约定好
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getDigest(Map<String, String> params) throws Exception{
		String secret = "abcdefghijkl";
		
		Set<String> keySet = params.keySet();
		
		//使用treeset排序
		TreeSet<String> sortSet = new TreeSet<>();
		sortSet.addAll(keySet);
		String keyvalueStr = "";
		Iterator<String> iterator = sortSet.iterator();
		
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = params.get(key);
			keyvalueStr += key + value;
		}
		
		keyvalueStr += secret;
		String base64 = SymEncUtil.byte2base64(NusumUtil.getMD5Code(keyvalueStr).getBytes());
		return base64;
	}
	/**
	 * 2 服务端参数摘要校验:
		 * 	服务端接收到请求的参数后,按照与客户端相同的方式将参数排序,然后将参数的名称与参数的值串起来,生成待
		 * 摘要字符串,并且使用与客户端相同的摘要算法生成摘要串,最后将服务端生成的摘要串与客户端通过header或者其他形式
		 * 传递过来的摘要串进行比较,如果一致,表示参数没有遭到篡改,反之,则说明参数遭到篡改.
	 * @param params
	 * @param digest
	 * @return
	 * @throws Exception
	 */
	public static boolean validate(Map<String, String> params, String digest) throws Exception{
		String secret = "abcdefghijkl";
		Set<String> keySet = params.keySet();
		TreeSet<String> sortSet = new TreeSet<>();
		sortSet.addAll(keySet);
		
		String keyvalueStr = "";
		
		Iterator<String> it = sortSet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			String values = params.get(key);
			keyvalueStr += key + values;
		}
		keyvalueStr += secret;
		String base64Str = SymEncUtil.byte2base64(NusumUtil.getMD5Code(keyvalueStr).getBytes());
		
		if(base64Str.equals(digest))
			return true;
		else 
			return false;
	}
	/**
	 * 3 服务器端响应摘要生成:
	 * 服务端生成响应内容以后,在响应内容的后面加上secret,便是待摘要串,然后MD5等摘要算法生成响应的摘要串
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String getDigest(String content) throws Exception{
		String secret = "abcdefghijkl";
		content += secret;
		String base64Str = SymEncUtil.byte2base64(NusumUtil.getMD5Code(content).getBytes());
		return base64Str;
	}
	/**
	 * 4 客户端响应摘要检验
	 * @param responseContent
	 * @param digest
	 * @return
	 * @throws Exception
	 */
	public static boolean validate(String responseContent, String digest) throws Exception{
		String secret = "abcdefghijkl";
		byte[] bytes = NusumUtil.getMD5Code(responseContent + secret).getBytes();
		String responseDigest = SymEncUtil.byte2base64(bytes);
		if(responseDigest.equals(digest))
			return true;
		else
			return false;
	}
	
}
