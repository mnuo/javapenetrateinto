/**
 * SHA1withRSADemo.java created at 2016年8月9日 下午4:14:25
 */
package com.mnuocom.largedistributedweb.chapter3.digitalsignature;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.mnuocom.largedistributedweb.chapter3.rsa.RSAUtil;

/**
 * @author saxon
 */
public class SHA1withRSADemo {

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
		byte[] sign = DigitalSignatureUtil.sign1(content.getBytes(), priKey, DigitalSignatureUtil.DIGITALSIGNATURE_SHA1_RSA);
		
		//验证签名,此处接收并验证
		boolean result = DigitalSignatureUtil.verify1(content.getBytes(), sign, pubKey, DigitalSignatureUtil.DIGITALSIGNATURE_SHA1_RSA);
		if(result){
			System.out.println("正常数字签名");
		}else{
			System.out.println("非正常数字签名");
		}
	}
}
