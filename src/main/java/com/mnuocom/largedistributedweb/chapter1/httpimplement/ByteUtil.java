/**
 * ByteUtil.java created at 2016年8月2日 下午3:15:41
 */
package com.mnuocom.largedistributedweb.chapter1.httpimplement;

/**
 * @author saxon
 * OutputStream 中直接写入一个int类型,会截取其低8位,丢弃其高24位,因此,需要将基本类型先转换为字节流.Java采用的BigEndian字节序(字节在电脑
 * 中存放的序列与输入(输出)时的序列是先到的在前,还是厚道的在前).所有的网络协议也是采用Big Endian字节序来进行传输.因此,我们在进行数据的传输时,需要现将其
 * 转换成Big Endian字节序;同理,在数据接收时,也需要进行相应的转换
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
