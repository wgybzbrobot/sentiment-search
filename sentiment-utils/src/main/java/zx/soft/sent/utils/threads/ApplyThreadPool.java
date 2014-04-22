package zx.soft.sent.utils.threads;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多线程工具类
 * @author wanggang
 *
 */
public class ApplyThreadPool {

	private static Logger logger = LoggerFactory.getLogger(ApplyThreadPool.class);

	/**
	 * 申请线程池
	 */
	public static ThreadPoolExecutor getThreadPoolExector() {

		final ThreadPoolExecutor result = new ThreadPoolExecutor(64, 64, 0L, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(128), new ThreadPoolExecutor.CallerRunsPolicy());
		result.setThreadFactory(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

					@Override
					public void uncaughtException(Thread t, Throwable e) {
						e.printStackTrace();
						logger.error("Thread exception: " + t.getName(), e);
						result.shutdown();
					}

				});
				return t;
			}
		});

		return result;
	}

}
