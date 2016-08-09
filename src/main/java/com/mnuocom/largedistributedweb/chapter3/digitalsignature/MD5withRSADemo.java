/**
 * MD5withRSADemo.java created at 2016年8月9日 下午3:42:26
 */
package com.mnuocom.largedistributedweb.chapter3.digitalsignature;

import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import javax.crypto.Cipher;

import com.mnuocom.largedistributedweb.chapter3.rsa.RSAUtil;
import com.mnuocom.largedistributedweb.chapter3.symmetricencryption.SymEncUtil;

/**
 * @author saxon
 */
public class MD5withRSADemo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		//生成公钥和私钥
		KeyPair keyPair = RSAUtil.getKeyPair();//共同的keyPair
		
		String pubStr = RSAUtil.getPublicKey(keyPair);//生成公钥
		System.out.println("公钥: "+ pubStr);
		String priStr = RSAUtil.getPrivateKey(keyPair);
		System.out.println("私钥: "+ priStr);
		
		PublicKey pubKey = RSAUtil.string2PublicKey(pubStr);
		PrivateKey priKey = RSAUtil.string2PrivateKey(priStr);
		
		//签名, 此处相当于发送
		String content = "hello,i am chenkangxian,good night!";
		byte[] sign = sign1(content.getBytes(), priKey);
		
		//验证签名,此处接收并验证
		boolean result = verify1(content.getBytes(), sign, pubKey);
		if(result){
			System.out.println("正常数字签名");
		}else{
			System.out.println("非正常数字签名");
		}
	}
	/**
	 * 签名1
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] content, PrivateKey privateKey) throws Exception{
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] bytes = md.digest(content);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		byte[] encryptBytes = cipher.doFinal(bytes);
		return encryptBytes;
	}
	/**
	 * 验证1
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] content, byte[] sign, PublicKey publicKey) throws Exception{
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] bytes = md.digest(content);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] decryptBytes = cipher.doFinal(sign);
		
		if(SymEncUtil.byte2base64(decryptBytes).equals(SymEncUtil.byte2base64(bytes))){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 签名2
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign1(byte[] content, PrivateKey privateKey) throws Exception {
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(privateKey);
		signature.update(content);
		return signature.sign();
	}
	/**
	 * 验证2
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static boolean verify1(byte[] content, byte[] sign, PublicKey publicKey) throws Exception{
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(publicKey);
		signature.update(content);
		return signature.verify(sign);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
