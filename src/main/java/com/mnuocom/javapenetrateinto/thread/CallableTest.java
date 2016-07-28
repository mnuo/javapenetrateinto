/**
 * CallableTest.java created at Jun 11, 2016 11:21:11 AM
 */
package com.mnuocom.javapenetrateinto.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * @author saxon
 * 带有返回值的线程Callable
 */
public class CallableTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Callable<String> mc = new MyCallable();
		FutureTask<String> task = new FutureTask<String>(mc);
		Thread thread11 = new Thread(task);
		thread11.start();
		System.out.println(task.get());
	}
}

class MyCallable implements Callable<String>{
	private int ticker = 5;
	@Override
	public String call() {
		for (int i = 0; i < 10; i++) {
			if(ticker > 0 ){
				System.out.println("runing" + ticker--);
			}
		}
		return  "run over";
	}
}
