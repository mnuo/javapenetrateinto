/**
 * ReentrantReadWriteLockDemo.java created at Jul 31, 2016 10:51:03 AM
 */
package com.mnuocom.distributedjava.distributedjava4;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * @author saxon
 * 此类用来测试ReentransReadWriteLock的性能,测试场景为同时启动102个线程,其中100个惊醒读操作,2个进行写操作
 */
public class ReentrantReadWriteLockDemo {
	private static ReentrantReadWriteLock lock = new 
			ReentrantReadWriteLock();
	private static WriteLock writeLock = lock.writeLock();
	private static ReadLock readLock = lock.readLock();
	
	private static Map<String, String> maps = new HashMap<String, String>();
	private static CountDownLatch latch = new CountDownLatch(102);
	private static CyclicBarrier barrier = new CyclicBarrier(102);
	
	public static void main(String[] args) throws InterruptedException {
		long beginTime = System.currentTimeMillis();
		for(int i = 0; i < 100; i ++){
			new Thread(new ReadThread()).start();
		}
		for(int i = 0; i < 2; i ++){
			new Thread(new WriteThread()).start();
		}
		latch.await();
		long endTime = System.currentTimeMillis();
		System.out.println("consume time is: " + (endTime - beginTime) + " ms");
		
	}
	static class WriteThread implements Runnable{
		@Override
		public void run() {
			try {
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				writeLock.lock();
				maps.put("1", "2");
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				writeLock.unlock();
			}
			latch.countDown();
		}
	}
	
	static class ReadThread implements Runnable{
		@Override
		public void run() {
			try {
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				readLock.lock();
				maps.get("1");
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				readLock.unlock();
			}
			latch.countDown();
			
		}
	}
}
