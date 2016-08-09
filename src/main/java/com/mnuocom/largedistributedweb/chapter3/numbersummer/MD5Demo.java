/**
 * MD5Demo.java created at 2016年8月9日 上午9:42:13
 */
package com.mnuocom.largedistributedweb.chapter3.numbersummer;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author saxon
 */
public class MD5Demo {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String content = "123123";
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] result = md.digest(content.getBytes());
		System.out.println(NusumUtil.byteToString(result));//16进制编码后
		System.out.println(NusumUtil.getMD5Code(content));
	}

}
