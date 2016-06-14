/**
 * RunableTest.java created at Jun 11, 2016 10:15:49 AM
 */
package com.mnuocom.javapenetrateinto.thread;

/**
 * @author saxon
 */
public class RunableTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test2();
		test1();

	}
	public static void test1(){
		String name = "out test1";
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(name + " running " + name);
			}
		}).start();
	}
	
	public static void test2(){
		String name = "test2";
		new Thread(()->{
			System.out.println(name + "running");
		}).start();
	}
}

