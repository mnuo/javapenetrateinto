
package com.mnuocom.largedistributedweb.chapter3.rsa;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.mnuocom.largedistributedweb.chapter3.symmetricencryption.SymEncUtil;

/**
 * 
 * @author saxon
 */
public class RSADemo {
	public static void main(String[] args) throws Exception {
		String content = "hello,i am chenkangxian,good night!";
		KeyPair keyPair = RSAUtil.getKeyPair();//共同的keyPair
		
		String pubStr = RSAUtil.getPublicKey(keyPair);//生成公钥
		System.out.println("公钥: "+ pubStr);
		String priStr = RSAUtil.getPrivateKey(keyPair);
		System.out.println("私钥: "+ priStr);
		
		PublicKey pubKey = RSAUtil.string2PublicKey(pubStr);
		PrivateKey priKey = RSAUtil.string2PrivateKey(priStr);
		//加密
		byte[] bs = RSAUtil.publicEncrypt(content.getBytes(), pubKey);
		System.out.println(SymEncUtil.byte2base64(bs));//加密后base64
		//解密
		byte[] bs2 = RSAUtil.privateDecrypt(bs, priKey);
		System.out.println(new String(bs2));
	}
}
