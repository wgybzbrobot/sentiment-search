package zx.soft.sent.utils.threads;

import static org.junit.Assert.assertEquals;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ApplyThreadPoolTest {

	class MyUnchecckedExceptionhandler implements UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			assertEquals("OMG!", e.getMessage());
		}

	}

	@Test
	public void test() throws InterruptedException {
		ExecutorService pool = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(5), new ThreadPoolExecutor.CallerRunsPolicy());
		for (int i = 0; i < 10; i++) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(300);
						System.out.println(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		pool.shutdown();
		pool.awaitTermination(5, TimeUnit.SECONDS);
		System.out.println("ok");
	}

	@Test
	public void testException() throws InterruptedException {
		ThreadFactory factory = new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread result = new Thread(r);
				result.setUncaughtExceptionHandler(new MyUnchecckedExceptionhandler());
				return result;
			}
		};
		ExecutorService pool = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(5), factory, new ThreadPoolExecutor.CallerRunsPolicy());

		for (int i = 0; i < 10; i++) {
			try {
				pool.execute(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						throw new RuntimeException("OMG!");
					}
				});
			} catch (Exception e) {
				assertEquals("OMG!", e.getMessage());
			}
		}
		pool.shutdown();
		pool.awaitTermination(5, TimeUnit.SECONDS);
	}

}
