/**
 * DESUtil.java created at 2016年8月9日 下午1:27:10
 */
package com.mnuocom.largedistributedweb.chapter3.symmetricencryption;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author saxon
 */
public class DESUtil {
	/**
	 * 生成经过base64编码后的密钥
	 * @return
	 * @throws Exception
	 */
	public static String genKeyDES() throws Exception{
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		keyGenerator.init(56);
		SecretKey key = keyGenerator.generateKey();
		String base64Str = byte2base64(key.getEncoded());
		return base64Str;
	}
	/**
	 * 根据base64编码后的字符串生成密钥
	 * @param base64Key
	 * @return
	 */
	public static SecretKey loadKeyDES(String base64Key){
		byte[] bytes = base642byes(base64Key);
		SecretKey key = new SecretKeySpec(bytes, "DES");
		return key;
	}
	/**
	 * 将byte[]转成base64字符
	 * @param bytes
	 * @return
	 */
	public static String byte2base64(byte[] bytes){
		//加密
		return Base64.getEncoder().encodeToString(bytes);
	}
	/**
	 * 将base64字符串转成byte[]
	 * @param base64Key
	 * @return
	 */
	public static byte[] base642byes(String base64Key){
		//解密
		return Base64.getDecoder().decode(base64Key);
	}
	
	/**
	 * DES 加密
	 * @param source
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptDES(byte[] source, SecretKey key) throws Exception{
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] bytes = cipher.doFinal(source);
		return bytes;
	}
	/**
	 * DES 解密
	 * @param source
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptDES(byte[] source, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] bytes = cipher.doFinal(source);
		return bytes;
	}
}
