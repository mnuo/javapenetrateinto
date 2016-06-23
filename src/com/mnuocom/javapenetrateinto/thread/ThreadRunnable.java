/**
 * ThreadRunnable.java created at Jun 11, 2016 10:45:59 AM
 */
package com.mnuocom.javapenetrateinto.thread;


/**
 * @author saxon
 * <p>线程共享</p>
 */
public class ThreadRunnable {
	
	public static void main(String[] args) {
		try {
			new ThreadRunnable().test3();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void test1(){
		Thread t1 = new MyThread2();
		Thread t2 = new MyThread2();
		Thread t3 = new MyThread2();
		Thread t4 = new MyThread2();
		Thread t5 = new MyThread2();
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}
	public void test2() throws Exception {
		MyThread1 mt = new MyThread1();
		new Thread(mt).start();
		new Thread(mt).start();
		new Thread(mt).start();
		new Thread(mt).start();
		new Thread(mt).start();
		new Thread(mt).start();
	}
	public void test3() throws Exception {
		MyThread2 mt = new MyThread2();
		new Thread(mt).start();
		new Thread(mt).start();
		new Thread(mt).start();
		new Thread(mt).start();
		new Thread(mt).start();
		new Thread(mt).start();
	}
	
}

class MyThread2 extends Thread{
	private int ticker = 5;
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			if(ticker > 0){
				System.out.println("run" + ticker --);
			}
		}
	}
}
class MyThread1 implements Runnable{
	private int ticker = 5;
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			if(ticker > 0){
				System.out.println("run" + ticker --);
			}
		}
	}
}