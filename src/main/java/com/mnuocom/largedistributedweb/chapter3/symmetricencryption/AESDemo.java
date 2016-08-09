/**
 * AESDemo.java created at 2016年8月9日 下午2:35:37
 */
package com.mnuocom.largedistributedweb.chapter3.symmetricencryption;

import javax.crypto.SecretKey;

/**
 * @author saxon
 */
public class AESDemo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String content = "hello,i am chenkangxian,good night!";
		//生成密钥
		String keyDes = SymEncUtil.genKey(SymEncUtil.ENCRYPTTYPE_AES);
		SecretKey key = SymEncUtil.loadKey(keyDes, SymEncUtil.ENCRYPTTYPE_AES);
		//DES 加密
		byte[] bytes = SymEncUtil.encryptAES(content.getBytes(), key);
		//输出密文经过base64编码后的字符串
		System.out.println(SymEncUtil.byte2base64(bytes));
		//DES解密
		byte[] dbs = SymEncUtil.decryptAES(bytes, key);
		System.out.println(new String(dbs));
	}

}
