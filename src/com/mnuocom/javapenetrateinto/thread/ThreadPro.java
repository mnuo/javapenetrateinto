/**
 * ThreadPro.java created at Jun 11, 2016 12:25:02 PM
 */
package com.mnuocom.javapenetrateinto.thread;

/**
 * @author saxon
 * 线程优先级
 */
public class ThreadPro {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getPriority());
		TestPriority tp = new TestPriority();
		
		Thread t1 = new Thread(tp, "ThreadA");
		Thread t2 = new Thread(tp, "ThreadB");
		Thread t3 = new Thread(tp, "ThreadC");
		Thread t4 = new Thread(tp, "ThreadD");
		
		t1.setPriority(Thread.MAX_PRIORITY);
		t2.setPriority(Thread.MIN_PRIORITY);
		t3.setPriority(Thread.MIN_PRIORITY);
		t4.setPriority(Thread.NORM_PRIORITY);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
	}

}
class TestPriority implements Runnable{
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ": " + i);
		}
	}
}
