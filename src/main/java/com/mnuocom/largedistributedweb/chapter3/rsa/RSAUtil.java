/**
 * RSAUtil.java created at 2016年8月9日 下午2:50:53
 */
package com.mnuocom.largedistributedweb.chapter3.rsa;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.mnuocom.largedistributedweb.chapter3.symmetricencryption.SymEncUtil;

/**
 * @author saxon
 */
public class RSAUtil {
	//生成公钥和私钥
	public static KeyPair getKeyPair() throws NoSuchAlgorithmException{
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(512);
		KeyPair keyPair = generator.generateKeyPair();
		return keyPair;
	}
	/**
	 * 生成公钥
	 * @param keyPair
	 * @return
	 */
	public static String getPublicKey(KeyPair keyPair){
		PublicKey publicKey = keyPair.getPublic();
		byte[] bytes = publicKey.getEncoded();
		return SymEncUtil.byte2base64(bytes);
	}
	/**
	 * 生成私钥
	 * @param keyPair
	 * @return
	 */
	public static String getPrivateKey(KeyPair keyPair){
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] bytes = privateKey.getEncoded();
		return SymEncUtil.byte2base64(bytes);
	}
	
	///////////转成base64
	/**
	 * 生成publickey
	 * @param pubStr
	 * @return
	 * @throws Exception
	 */
	public static PublicKey string2PublicKey(String pubStr) throws Exception{
		byte[] bytes = SymEncUtil.base642byes(pubStr);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}
	/**
	 * 生成privatekey
	 * @param pubStr
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey string2PrivateKey(String priStr) throws Exception{
		byte[] bytes = SymEncUtil.base642byes(priStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	/**
	 * 使用公钥加密
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}
	/**
	 * 使用私钥解密
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}
}
