/**
 * MD5Util.java created at 2016年8月9日 上午9:48:11
 */
package com.mnuocom.largedistributedweb.chapter3.numbersummer;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author saxon
 * MD5 算法
 */
public class NusumUtil {
   
   // 全局数组
   private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
           "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

   // 返回形式为数字跟字符串
   private static String byteToArrayString(byte bByte) {
       int iRet = bByte;
       // System.out.println("iRet="+iRet);
       if (iRet < 0) {
           iRet += 256;
       }
       int iD1 = iRet / 16;
       int iD2 = iRet % 16;
       return strDigits[iD1] + strDigits[iD2];
   }

   // 返回形式只为数字
   public static String byteToNum(byte bByte) {
       int iRet = bByte;
       System.out.println("iRet1=" + iRet);
       if (iRet < 0) {
           iRet += 256;
       }
       return String.valueOf(iRet);
   }

   // 转换字节数组为16进制字串
   public static String byteToString(byte[] bByte) {
       StringBuffer sBuffer = new StringBuffer();
       for (int i = 0; i < bByte.length; i++) {
           sBuffer.append(byteToArrayString(bByte[i]));
       }
       return sBuffer.toString();
   }
   /**
    * MD5 加密
    * @param in
    * @return
    * @throws UnsupportedEncodingException
    * @throws NoSuchAlgorithmException
    */
   public static String getMD5Code(String in) throws UnsupportedEncodingException, NoSuchAlgorithmException {
      return getCode(in, "MD5");
   }
   /**
    * SHA-1 加密
    * @param in
    * @return
    * @throws UnsupportedEncodingException
    * @throws NoSuchAlgorithmException
    */
   public static String getSHA1Code(String in) throws UnsupportedEncodingException, NoSuchAlgorithmException {
	   return getCode(in, "SHA-1");
   }
   /**
    * 加密
    * @param in
    * @param algorithm
    * @return
    * @throws UnsupportedEncodingException
    * @throws NoSuchAlgorithmException
    */
   public static String getCode(String in, String algorithm) throws UnsupportedEncodingException, NoSuchAlgorithmException{
       MessageDigest md = MessageDigest.getInstance(algorithm);
       // md.digest() 该函数返回值为存放哈希值结果的byte数组
       return byteToString(md.digest(in.getBytes("utf8")));
   }
   
   /**
    * bytes转16进制
    * @param bytes
    * @return
    */
   public static String bytes2Hex(byte[] bytes){
	   StringBuffer hex = new StringBuffer();
	   for(int i = 0; i < bytes.length; i ++){
		   byte b = bytes[i];
		   boolean negative = false;//是否为负数
		   if(b < 0) negative = true;
		   int inte = Math.abs(b);
		   if(negative) inte = inte | 0x80;
		   //负数会转成整数(最高位的负号变成数值计算),再转十六进制
		   String temp = Integer.toHexString(inte & 0xFF);
		   if(temp.length() == 1){
			   hex.append("0");
		   }
		   hex.append(temp.toLowerCase());
	   }
	   return hex.toString();
   }
   /**
    * 16进制转bytes
    * @param hex
    * @return
    */
   public static byte[] hex2bytes(String hex){
	   byte[] bytes = new byte[hex.length()/2];
	   for(int i = 0; i < hex.length(); i = i+2){
		   String subStr = hex.substring(i, i + 2);
		   boolean negative = false;//是否为负数
		   int inte = Integer.parseInt(subStr, 16);
		   if(inte > 127) negative = true;
		   if(inte == 128){
			   inte = -128;
		   }else if(negative){
			   inte = 0 - (inte & 0x7F);
		   }
		   byte b = (byte) inte;
		   bytes[i/2] = b;
	   }
	   return bytes;
   }
}