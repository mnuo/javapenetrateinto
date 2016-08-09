/**
 * DigitalSignatureUtil.java created at 2016年8月9日 下午4:17:13
 */
package com.mnuocom.largedistributedweb.chapter3.digitalsignature;

import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import javax.crypto.Cipher;

import com.mnuocom.largedistributedweb.chapter3.symmetricencryption.SymEncUtil;

/**
 * @author saxon
 */
public class DigitalSignatureUtil {
	public static final String DIGITALSIGNATURE_SHA1 = "SHA1";
	public static final String DIGITALSIGNATURE_MD5 = "MD5";
	public static final String DIGITALSIGNATURE_RSA = "RSA";
	public static final String DIGITALSIGNATURE_MD5_RSA = "MD5withRSA";
	public static final String DIGITALSIGNATURE_SHA1_RSA = "SHA1withRSA";
	/**
	 * 签名1
	 * @param content
	 * @param privateKey
	 * @param mdtype
	 * @param sytype
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] content, PrivateKey privateKey, String mdtype,String sytype) throws Exception{
		MessageDigest md = MessageDigest.getInstance(mdtype);
		byte[] bytes = md.digest(content);
		Cipher cipher = Cipher.getInstance(sytype);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
		byte[] encryptBytes = cipher.doFinal(bytes);
		return encryptBytes;
	}
	/**
	 * 验证1
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @param mdtype
	 * @param sytype
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] content, byte[] sign, PublicKey publicKey, String mdtype,String sytype) throws Exception{
		MessageDigest md = MessageDigest.getInstance(mdtype);
		byte[] bytes = md.digest(content);
		Cipher cipher = Cipher.getInstance(sytype);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] decryptBytes = cipher.doFinal(sign);
		
		if(SymEncUtil.byte2base64(decryptBytes).equals(SymEncUtil.byte2base64(bytes))){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 签名2 (使用jdk Signature类)
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign1(byte[] content, PrivateKey privateKey, String type) throws Exception {
		Signature signature = Signature.getInstance(type);
		signature.initSign(privateKey);
		signature.update(content);
		return signature.sign();
	}
	/**
	 * 验证2 (使用jdk Signature类)
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static boolean verify1(byte[] content, byte[] sign, PublicKey publicKey, String type) throws Exception{
		Signature signature = Signature.getInstance(type);
		signature.initVerify(publicKey);
		signature.update(content);
		return signature.verify(sign);
	}
}
