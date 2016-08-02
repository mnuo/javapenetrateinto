/**
 * Endcode.java created at 2016年8月2日 下午3:48:05
 */
package com.mnuocom.largedistributedweb.chapter1.httpimplement;

/**
 * @author saxon
 */
public enum Encode {
	GBK((byte)1),
	UTF8((byte)2);
	
	private byte value;
	
	private Encode(byte value) {
		this.value = value;
	}
	public byte getValue() {
		return value;
	}
}
