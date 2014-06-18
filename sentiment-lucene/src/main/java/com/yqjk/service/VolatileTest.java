package com.yqjk.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest {
	public volatile static int count = 0;
	public static AtomicInteger countX = new AtomicInteger(0);

	public static Object locobj=new Object();
	public static void inc() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		count++;
		
		// count--;
		countX.incrementAndGet();
		// countX.decrementAndGet();
		// if (countX.get()==0) {
		// System.out.println("运行结果X:Counter.count=0");
		// }
	}

	public static void main(String[] args) throws InterruptedException {

		// ExecutorService
		// service=Executors.newFixedThreadPool(Integer.MAX_VALUE);
		 ExecutorService service = Executors.newCachedThreadPool();
		for (int i = 0; i < 1000; i++) {
//			 service.execute(new Runnable() {
//			 @Override
//			 public void run() {
//			 VolatileTest.inc();
//			 }
//			 });
			new Thread(new Runnable() {
				@Override
				public void run() {
					VolatileTest.inc();
				}
			}).start();
		}

		 service.shutdown();
		// 给予一个关闭时间（timeout），但是实际关闭时间应该会这个小
		 service.awaitTermination(300,TimeUnit.SECONDS);
		 
		 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("运行结果:Counter.count=" + VolatileTest.count);
		System.out.println("运行结果:Counter.count=" + VolatileTest.countX);
	}
}
