package zx.soft.sent.web.demo;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogerDemo {

	private static Logger logger = LoggerFactory.getLogger(LogerDemo.class);

	public static void main(String[] args) throws UnsupportedEncodingException {

		logger.info("Start logging ...");
		logger.info("Finish logging ...");
	}

}
