package zx.soft.sent.hdfs.io.impl;

import java.io.Closeable;

public interface HdfsWriter extends Closeable {

	void write(String key, String value);

}
