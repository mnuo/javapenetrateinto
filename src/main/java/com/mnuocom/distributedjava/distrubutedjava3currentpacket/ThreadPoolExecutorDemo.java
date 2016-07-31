/**
 * ThreadPoolExecutorDemo.java created at Jul 31, 2016 9:45:46 AM
 */
package com.mnuocom.distributedjava.distrubutedjava3currentpacket;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author saxon
 * 例子同时发起1000个请求,这些请求的处理交由ThreadPoolExcutor来执行,每次的处理消耗3秒左右
 * 采用以上第一种配置:CallerRunsPolicy
 */
public class ThreadPoolExecutorDemo {
	
	final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10);
	final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 600, 30, TimeUnit.SECONDS, queue, Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
	
	final AtomicInteger completedTask = new AtomicInteger();
	final AtomicInteger rejectedTask = new AtomicInteger();
	static long beginTime;
	final int count = 1000;
	
	public static void main(String[] args) {
		beginTime = System.currentTimeMillis();
		ThreadPoolExecutorDemo demo = new ThreadPoolExecutorDemo();
		demo.start();
	}
	public void start(){
		CountDownLatch latch = new CountDownLatch(count);
		CyclicBarrier barrier = new CyclicBarrier(count);
		for(int i =0; i < count; i ++){
			new Thread(new TestThread(latch, barrier)).start();
		}
		
		try {
			latch.await();
			executor.shutdownNow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class TestThread implements Runnable{
		private CountDownLatch latch;
		private CyclicBarrier barrier;
		
		public TestThread(CountDownLatch latch, CyclicBarrier barrier) {
			this.latch = latch;
			this.barrier = barrier;
		}
		
		@Override
		public void run() {
			try {
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				executor.equals(new Task1(latch));
			} catch (RejectedExecutionException e) {
				latch.countDown();
				System.err.println("被拒绝的任务数为: 用地" + rejectedTask.incrementAndGet());
			}
		}
	}
	
	class Task1 implements Runnable{
		private CountDownLatch latch;
		public Task1(CountDownLatch latch){
			this.latch = latch;
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("执行的任务数为: " + completedTask.incrementAndGet());
			System.out.println("任务耗时为: " + (System.currentTimeMillis() - beginTime) + " ms");
			latch.countDown();
			
		}
	}
}
