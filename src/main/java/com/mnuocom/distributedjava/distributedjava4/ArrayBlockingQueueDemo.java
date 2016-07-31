/**
 * ArrayBlockingQueueDemo.java created at Jul 30, 2016 10:53:31 PM
 */
package com.mnuocom.distributedjava.distributedjava4;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author saxon
 */
public class ArrayBlockingQueueDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayBlockingQueue<Integer> aq = new ArrayBlockingQueue<Integer>(3);
		aq.offer(4);
		aq.offer(5);
		aq.offer(7);
		aq.offer(6);
		System.out.println(aq);
		System.out.println(aq.poll());
		System.out.println(aq);
		aq.offer(6);
		System.out.println(aq);
	}

}
