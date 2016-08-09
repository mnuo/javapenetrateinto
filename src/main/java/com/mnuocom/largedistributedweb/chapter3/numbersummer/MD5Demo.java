/**
 * MD5Demo.java created at 2016年8月9日 上午9:42:13
 */
package com.mnuocom.largedistributedweb.chapter3.numbersummer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author saxon
 */
public class MD5Demo {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String content = "123123";
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] result = md.digest(content.getBytes());
		System.out.println(MD5Util.byteToString(result));//16进制编码后
		System.out.println(MD5Util.getMD5Code(content));
	}

}
