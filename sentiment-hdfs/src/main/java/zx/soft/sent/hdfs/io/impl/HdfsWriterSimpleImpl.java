package zx.soft.sent.hdfs.io.impl;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单的HDFS写数据实现类
 * 
 * @author wanggang
 *
 */
public class HdfsWriterSimpleImpl implements HdfsWriter {

	private final Logger logger = LoggerFactory.getLogger(HdfsWriterSimpleImpl.class);

	private Writer writer;

	static final Configuration conf = new Configuration();;

	private final Text key = new Text();

	private final Text value = new Text();

	private final String path;

	private String fileName;

	/**
	 * 指定HDFS path，在其目录下将写入文件，以毫秒时间戳命名，如果文件写入失败，则自动更换新的文件
	 * @param path
	 */
	public HdfsWriterSimpleImpl(String path) {
		logger.info("HDFS Url: " + conf.get("fs.default.name"));
		this.path = path;
		createNewWriter();
	}

	@Override
	public synchronized void close() {
		logger.info("HDFS writer close.");
		try {
			writer.close();
		} catch (IOException e) {
			logger.error("Exception:{}, StackTrace:{}", e.getMessage(), e.getStackTrace());
		}
	}

	public synchronized void flush() {
		try {
			writer.syncFs();
		} catch (IOException e) {
			logger.error("Exception:{}, StackTrace:{}", e.getMessage(), e.getStackTrace());
			throw new RuntimeException(e);
		}
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public synchronized void write(String key, String value) {
		this.key.set(key);
		this.value.set(value);
		try {
			writer.append(this.key, this.value);
		} catch (IOException e) {
			logger.warn("write hdfs error. key={}, value={}", key, value, e);
			logger.error("Exception:{}, StackTrace:{}", e.getMessage(), e.getStackTrace());
			close();
			createNewWriter();
		}
	}

	private void createNewWriter() {
		try {
			this.fileName = this.path + "/" + System.currentTimeMillis();
			FileSystem fs = FileSystem.get(URI.create(""), conf);
			Path path = new Path(fileName);
			if (fs.exists(path)) {
				throw new FileExistsException(fileName);
			}
			logger.info("Create HDFS writer, fileName: " + fileName);
			writer = SequenceFile.createWriter(fs, conf, path, Text.class, Text.class);
		} catch (IOException e) {
			logger.error("Exception:{}, StackTrace:{}", e.getMessage(), e.getStackTrace());
			throw new RuntimeException(e);
		}
	}

}
