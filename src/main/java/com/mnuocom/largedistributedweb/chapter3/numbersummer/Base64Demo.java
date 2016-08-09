/**
 * Base64Demo.java created at 2016年8月9日 上午10:55:07
 */
package com.mnuocom.largedistributedweb.chapter3.numbersummer;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;


/**
 * @author saxon
 */
public class Base64Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//加密
		Encoder encode = Base64.getEncoder();
		byte[] bytes = encode.encode("hello".getBytes());
		System.out.println(encode.encodeToString("hello".getBytes()));
		System.out.println(new String(bytes));
		//解密
		Decoder decode = Base64.getDecoder();
		byte[] bb = decode.decode(encode.encodeToString("hello".getBytes()));
		System.out.println(new String(decode.decode(bytes)));
		System.out.println(new String(bb));
	}

}
