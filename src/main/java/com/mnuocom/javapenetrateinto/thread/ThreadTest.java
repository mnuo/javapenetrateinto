/**
 * ThreadTest.java created at Jun 11, 2016 10:07:30 AM
 */
package com.mnuocom.javapenetrateinto.thread;

/**
 * @author saxon
 */
public class ThreadTest {
	public static void main(String[] args) {
		Thread thread1 = new myThread("thread1");
		Thread thread2 = new myThread("thread2");
		Thread thread3 = new myThread("thread3");
		Thread thread4 = new myThread("thread4");
		Thread thread5 = new myThread("thread5");
		Thread thread6 = new myThread("thread6");
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		thread5.start();
		thread6.start();
	}
}
class myThread extends Thread{
	String name;
	public myThread(String name) {
		this.name = name;
	}
	@Override
	public void run() {
		for(int i = 0; i < 10; i ++){
			System.out.println(this.name +" runing");
		}
	}
}