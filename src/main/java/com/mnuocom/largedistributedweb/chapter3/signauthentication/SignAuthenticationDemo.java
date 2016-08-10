/**
 * SignAuthenticationDemo.java created at 2016年8月10日 上午11:02:02
 */
package com.mnuocom.largedistributedweb.chapter3.signauthentication;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.mnuocom.largedistributedweb.chapter3.digitalsignature.DigitalSignatureUtil;
import com.mnuocom.largedistributedweb.chapter3.numbersummer.NusumUtil;
import com.mnuocom.largedistributedweb.chapter3.rsa.RSAUtil;

/**
 * @author saxon
 */
public class SignAuthenticationDemo {
	public static KeyPair keyPair = null;
	public static PublicKey pubKey = null;
	public static PrivateKey priKey = null;
	
	static {
		try {
			keyPair = RSAUtil.getKeyPair();//共同的keyPair
			String pubStr = RSAUtil.getPublicKey(keyPair);//生成公钥
			String priStr = RSAUtil.getPrivateKey(keyPair);
			

			pubKey = RSAUtil.string2PublicKey(pubStr);
			priKey = RSAUtil.string2PrivateKey(priStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//1 客户端参数签名生成
		Map<String, String> params = new HashMap<>();
		params.put("username", "zhangsan");
		params.put("password", "123123");
		String sign = getSign(params);//生成的摘要
		System.out.println("客户端生成的签名: " + sign);
		
		//2  服务端参数签名校验
		boolean serverResult = validate(params, sign);
		System.out.println("服务端参数签名校验结果: " + serverResult);
		
		//3  服务器端响应签名生成
		String content = "登录成功";
		String serverdigest = getSign(content);
		System.out.println(" 服务器端响应签名: " + serverdigest);
		
		//4 客户端响应签名检验
		boolean clientResult = validate(content, serverdigest);
		System.out.println("客户端响应签名检验结果: "+ clientResult);

	}

	/**
	 * 1 客户端参数签名生成
	 * 	请求参数排序后将参数的名称和值拼接起来,形成待摘要字符串,然后使用与服务端约定好的摘要算法生成摘要串,生成的摘要串
	 * 在使用客户端的私钥进行及加密,形成数字签名
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getSign(Map<String, String> params) throws Exception{
		Set<String> keySet = params.keySet();
		//使用treeset排序
		TreeSet<String> sortSet = new TreeSet<>();
		sortSet.addAll(keySet);
		String keyvalueStr = "";
		Iterator<String> it = sortSet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = params.get(key);
			keyvalueStr += key + value;
		}
		
		//签名, 此处相当于发送
		byte[] sign = DigitalSignatureUtil.sign1(keyvalueStr.getBytes(), priKey, DigitalSignatureUtil.DIGITALSIGNATURE_MD5_RSA);
		return NusumUtil.bytes2Hex(sign);		
	}
	/**
	 * 2 服务端参数签名校验
	 * 
	 * @param params
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static boolean validate(Map<String, String> params, String sign) throws Exception{
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
		boolean result = DigitalSignatureUtil.verify1(keyvalueStr.getBytes(), NusumUtil.hex2bytes(sign), pubKey, DigitalSignatureUtil.DIGITALSIGNATURE_MD5_RSA);
		return result;
	}
	/**
	 * 3 服务端生成响应
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String getSign(String content) throws Exception{
		byte[] contents = DigitalSignatureUtil.sign1(content.getBytes(), priKey, DigitalSignatureUtil.DIGITALSIGNATURE_MD5_RSA);
		return NusumUtil.bytes2Hex(contents);
	}
	/**
	 * 4 客户端验证
	 * @param responseContent
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static boolean validate(String responseContent, String sign) throws Exception{
		boolean result = DigitalSignatureUtil.verify1(responseContent.getBytes(), NusumUtil.hex2bytes(sign), pubKey, DigitalSignatureUtil.DIGITALSIGNATURE_MD5_RSA);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
}
