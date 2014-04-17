package zx.soft.sentiment.hdfs.common;

import java.io.Closeable;

public interface HdfsWriter extends Closeable {

	void write(String key, String value);

}
