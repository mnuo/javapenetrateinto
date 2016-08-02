/**
 * ByteUtil.java created at 2016年8月2日 下午3:15:41
 */
package com.mnuocom.largedistributedweb.chapter1.httpimplement;

/**
 * @author saxon
 */
public class ByteUtil {
	public static int bytes2Int(byte[] bytes){
		int num = bytes[3] & 0XFF;
		num |= ((bytes[2] << 8) & 0xFF00);
		num |= ((bytes[1] << 16) & 0xFF0000);
		num |= ((bytes[0] << 24) & 0xFF000000);
		return num;
	}
	public static byte[] int2ByteArray(int i){
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		
		return result;
	}
}
