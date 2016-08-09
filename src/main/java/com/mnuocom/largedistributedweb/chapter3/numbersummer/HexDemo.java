/**
 * HexDemo.java created at 2016年8月9日 上午10:29:15
 */
package com.mnuocom.largedistributedweb.chapter3.numbersummer;

/**
 * @author saxon
 */
public class HexDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(NusumUtil.bytes2Hex("fuckjapanese".getBytes()));
		byte[] result = NusumUtil.hex2bytes("6675636b6a6170616e657365");
		System.out.println(new String(result));
	}

}
