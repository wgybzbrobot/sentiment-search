package zx.soft.sent.hdfs.io.impl;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HDFS写数据队列实现类
 * 
 * @author wanggang
 *
 */
public class HdfsWriterQueueImpl implements HdfsWriter {

	private final Logger logger = LoggerFactory.getLogger(HdfsWriterQueueImpl.class);

	static final Configuration conf = new Configuration();

	/**
	 * 写入HDFS的队列，每个对象是String[]{uid, uid的关注拼成的字符串}。
	 * 假设平均每个用户的关注为1000个，队列长度为1000时，此队列共消耗内存约10MB
	 */
	private final BlockingQueue<String[]> queue = new ArrayBlockingQueue<String[]>(1000);

	private Writer writer;

	private transient boolean isRunning = true;

	private final Thread hdfsWriterThread;

	public HdfsWriterQueueImpl(String name) {
		try {
			logger.info("HDFS Url: " + conf.get("fs.default.name"));
			FileSystem fs = FileSystem.get(URI.create(""), conf);
			Path path = new Path(name);
			if (fs.exists(path)) {
				throw new FileExistsException(name);
			}
			writer = SequenceFile.createWriter(fs, conf, path, Text.class, Text.class);
			logger.info("Create HDFS writer, fileName: " + name);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		hdfsWriterThread = new Thread(new Runnable() {

			@Override
			public void run() {
				String[] kv = null;
				try {
					while (true) {
						kv = queue.take();
						writer.append(new Text(kv[0]), new Text(kv[1]));
					}
				} catch (InterruptedException e) {
					logger.error("HDFS writer thread interrupted.");
					closeNow();
				} catch (IOException e) {
					logger.error("HDFS write error: " + Arrays.toString(kv), e);
					closeNow();
				}
			}
		}, "HdfsWriterThread");
		hdfsWriterThread.start();

		long now = System.currentTimeMillis();
		new Timer(true).schedule(new TimerTask() {
			@Override
			public void run() {
				logger.info("HDFS writer queue length: " + getQueueLength());
			}
		} //
				, new Date(now - now % (1000 * 60) + 1000 * 60) // 下一个整点分钟触发
				, 1000 * 60);
	}

	@Override
	public void close() {
		try {
			isRunning = false;
			int count = 0;
			while (!queue.isEmpty() && count < 20) {
				TimeUnit.MILLISECONDS.sleep(500);
				count++;
			}
			hdfsWriterThread.interrupt();
		} catch (InterruptedException e) {
			hdfsWriterThread.interrupt();
		}
	}

	public void flush() {
		try {
			writer.syncFs();
		} catch (IOException e) {
			isRunning = false;
			throw new RuntimeException(e);
		}
	}

	public int getQueueLength() {
		return queue.size();
	}

	@Override
	public void write(String key, String value) {
		if (!isRunning) {
			throw new RuntimeException("HDFS writer is closed.");
		}
		try {
			queue.put(new String[] { key, value });
		} catch (InterruptedException e) {
			isRunning = false;
			throw new RuntimeException(e);
		}
	}

	private void closeNow() {
		logger.info("HDFS writer close...");
		try {
			writer.sync();
			writer.syncFs();
			writer.close();
		} catch (IOException e1) {
			logger.error("HDFS writer close Exception.", e1);
		}
	}

}
