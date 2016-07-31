/**
 * AtomicintegerDemo.java created at Jul 31, 2016 9:09:37 AM
 */
package com.mnuocom.distributedjava.distributedjava4;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author saxon
 */
public class AtomicintegerDemo {
	private static int id = 0;
	private static AtomicInteger atomicId = new AtomicInteger();
	private static CountDownLatch latch = null;
	
	public synchronized static int qetNextId(){
		return ++id;
	}
	
	public static int getNextIdWithAtomic(){
		return atomicId.incrementAndGet();
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		latch = new CountDownLatch(50);
		long beginTime = System.nanoTime();
		for(int i = 0 ; i < 50; i ++){
			new Thread(new Task(false)).start();
		}
		latch.await();
		System.out.println("Synchronized syle consume time:" + (System.nanoTime() - beginTime));
		latch = new CountDownLatch(50);
		beginTime = System.nanoTime();
		for(int i = 0; i < 50; i ++){
			new Thread(new Task(true)).start();
		}
		latch.await();
		System.out.println("CAS style consume time: " + (System.nanoTime() - beginTime));
	}
	static class Task implements Runnable{
		private boolean isAtomic;
		
		public Task (boolean isAtomic) {
			this.isAtomic = isAtomic;
		}
		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				if(isAtomic){
					getNextIdWithAtomic();
				}else{
					qetNextId();
				}
			}
			latch.countDown();
		}
	}
}

