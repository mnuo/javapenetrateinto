/**
 * SHA1Demo.java created at 2016年8月9日 上午10:04:52
 */
package com.mnuocom.largedistributedweb.chapter3.numbersummer;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author saxon
 */
public class SHA1Demo {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String content = "hello,i am chenkangxian,good night!";
		
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] resut = md.digest(content.getBytes("utf8"));
		System.out.println(NusumUtil.byteToString(resut));
		System.out.println(NusumUtil.getSHA1Code(content));
	}

}
