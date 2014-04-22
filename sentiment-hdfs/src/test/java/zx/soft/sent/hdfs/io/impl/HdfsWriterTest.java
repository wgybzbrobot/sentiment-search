package zx.soft.sent.hdfs.io.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class HdfsWriterTest {

	final String path = "target/data/test";

	@Before
	public void setUp() {
		new File(path).delete();
	}

	@Test
	@Ignore
	public void test() throws IOException {
		HdfsWriterSimpleImpl writer = new HdfsWriterSimpleImpl(path);
		writer.write("key1", "value1");
		writer.write("key2", "1234567890");
		writer.write("key3", "1234567890");
		writer.write("key4", "12345678901234567890");
		writer.write("key5", "end");
		writer.flush();
		writer.close();

		FileSystem fs = FileSystem.get(URI.create(""), HdfsWriterSimpleImpl.conf);
		try (Reader reader = new SequenceFile.Reader(fs, new Path(writer.getFileName()), HdfsWriterSimpleImpl.conf);) {
			assertContent("[121]	key1	value1\n", reader);
			assertContent("[149]	key2	1234567890\n", reader);
			assertContent("[181]	key3	1234567890\n", reader);
			assertContent("[213]	key4	12345678901234567890\n", reader);
			assertContent("[246]	key5	end\n", reader);
			assertContentEOF(reader);
		}
	}

	private void assertContent(String string, Reader reader) throws IOException {
		Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(), HdfsWriterSimpleImpl.conf);
		Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), HdfsWriterSimpleImpl.conf);
		long position = reader.getPosition();
		assertTrue(reader.next(key, value));
		String syncSeen = reader.syncSeen() ? "*" : "";
		assertEquals(string, String.format("[%s%s]\t%s\t%s\n", position, syncSeen, key, value));
	}

	private void assertContentEOF(Reader reader) throws IOException {
		Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(), HdfsWriterSimpleImpl.conf);
		Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), HdfsWriterSimpleImpl.conf);
		assertFalse(reader.next(key, value));
	}

}
