/**
 * CPUSYDemo.java created at Jul 31, 2016 10:05:25 PM
 */
package com.mnuocom.distributedjava.distributedjava5;

import java.util.Random;

/**
 * @author saxon
 */
public class CPUSYDemo {
	private static int threadCount = 500;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length == 1){
			threadCount = Integer.parseInt(args[0]);
		}
		CPUSYDemo demo = new CPUSYDemo();
		demo.runTest();
	}

	private Random random = new Random();
	private Object[] locks;
	private void runTest(){
		locks = new Object[threadCount];
		for(int i = 0; i < threadCount; i ++){
			locks[i] = new Object();
		}
		for(int i = 0; i < threadCount; i ++){
			new Thread(new ATask(i)).start();
			new Thread(new BTask(i)).start();
		}
	}
	class ATask implements Runnable {
		private Object lockObject = null;
		
		public ATask(int i){
			lockObject = locks[i];
		}
		@Override
		public void run() {
			while (true) {
				try {
					synchronized (lockObject) {
						lockObject.wait(random.nextInt(10));
					}
				} catch (Exception e) {
				}
				
			}
		}
	}
	class BTask implements Runnable {
		private Object lockObject = null;
		
		public BTask(int i){
			lockObject = locks[i];
		}
		@Override
		public void run() {
			while (true) {
				synchronized (lockObject) {
					lockObject.notify();
				}
				try {
					Thread.sleep(random.nextInt(5));
				} catch (Exception e) {
				}
				
			}
		}
	}
}
