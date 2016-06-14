/**
 * GetThreadName.java created at Jun 11, 2016 11:56:19 AM
 */
package com.mnuocom.javapenetrateinto.thread;

/**
 * @author saxon
 */
public class GetThreadName {
	public static void main(String[] args) {
		GetThreadNameTest gt = new GetThreadNameTest();
		new Thread(gt,"MyThread").start();
		gt.run();
	}

}
class GetThreadNameTest implements Runnable{
	@Override
	public void run() {
		System.out.println("Thread:" + Thread.currentThread().getName());
	}
}