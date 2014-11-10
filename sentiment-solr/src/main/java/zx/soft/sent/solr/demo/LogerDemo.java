package zx.soft.sent.solr.demo;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.utils.demo.CharsDemo;

public class LogerDemo {

	private static Logger logger = LoggerFactory.getLogger(LogerDemo.class);

	public static void main(String[] args) throws UnsupportedEncodingException {

		logger.info("Start logging ...");
		String str = "测试转码";
		byte[] utf_16be = str.getBytes("utf-16be");
		String utf8str = CharsDemo.visualUtf8Str(utf_16be);
		System.out.println(utf8str);
		logger.info("Finish logging ...");
	}

}
