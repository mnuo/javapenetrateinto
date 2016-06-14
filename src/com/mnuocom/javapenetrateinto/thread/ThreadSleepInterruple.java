/**
 * ThreadSleepInterruple.java created at Jun 11, 2016 12:07:53 PM
 */
package com.mnuocom.javapenetrateinto.thread;

/**
 * @author saxon
 */
public class ThreadSleepInterruple {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new ThreadSleep(), "ThreadA");
		t.start();
		new Thread(new ThreadSleep(), "ThreadB").start();
		new Thread(new ThreadSleep(), "ThreadC").start();
		Thread.sleep(3000);
		t.interrupt();
	}

}
class ThreadSleep implements Runnable {
	@Override
	public void run() {
		for(int i = 0; i < 10; i ++){
			try {
				Thread.sleep(1000);
				System.err.println(Thread.currentThread().getName() +":"+ i);
			} catch (InterruptedException e) {
				System.err.println("Interrupte " + Thread.currentThread().getName());
			}
		}
	}
}
