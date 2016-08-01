/**
 * ObjectPoolDemo.java created at 2016年8月1日 下午2:43:04
 */
package com.mnuocom.distributedjava.distributedjava5;

import java.util.concurrent.CountDownLatch;

/**
 * @author saxon
 * 使用对象缓存池
 * 创建对象的实例要消耗一定的CPU以及内存,使用对象缓存池一定程度上可降低JVM Heap内存的使用
 */
public class ObjectPoolDemo {
	private static int maxFactor = 10;
	private static int threadCount = 100;
	private static final int NOTUSE_OBJECTPOOL = 1;
	private static final int USE_OBJECTPOOL = 2;
	private static int runMode = NOTUSE_OBJECTPOOL;
	private static CountDownLatch latch = null;
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(20000);
		if(args.length == 1)
			runMode = Integer.parseInt(args[0]);
		if(args.length == 2){
			runMode = Integer.parseInt(args[0]);
			
		}

	}

}
