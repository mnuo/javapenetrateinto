/**
 * DistrictByteBufferDemo.java created at 2016年8月1日 下午1:38:22
 */
package com.mnuocom.distributedjava.distributedjava5;

import java.nio.ByteBuffer;

/**
 * @author saxon
 * 对物理内存德尔消耗:
 * 基于DirectByteBuffer可以很容易的实现对物理内存的直接操作,而无须耗费JVM Heap区
 */
public class DistrictByteBufferDemo {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(20000);
		System.out.println("read to create bytes,so JVM heap "
				+ "will be used.");
		byte[] bytes = new byte[128*1000*1000];
		bytes[0] = 1;
		bytes[1] = 2;
		Thread.sleep(10000);
		System.out.println("read to allocated & put direct bytebuffers,no JVM heap "
				+ " should be used.");
		ByteBuffer buffer = ByteBuffer.allocate(128*1000*1000);
		buffer.put(bytes);
		buffer.flip();
		Thread.sleep(10000);
		System.out.println("read to gc, JVM heap will be fredd.");
		bytes = null;
		System.gc();
		Thread.sleep(10000);
		System.out.println("read to get bytes,then JVM heap will be used");
		byte[] resultbytes = new byte[128*1000*1000];
		buffer.get(resultbytes);
		System.out.println("resultbytes[1] is: " + resultbytes[1]);
		Thread.sleep(10000);
		System.out.println("read to gc all");
		buffer = null;
		resultbytes = null;
		System.gc();
		Thread.sleep(10000);
	}

}
