package zx.soft.sent.hdfs.io.impl;

import java.io.IOException;
import java.util.List;

public class HdfsWriterDummy implements HdfsWriter {

	private final List<String[]> result;

	public HdfsWriterDummy(List<String[]> result) {
		this.result = result;
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public void write(String key, String value) {
		result.add(new String[] { key, value });
	}

}
