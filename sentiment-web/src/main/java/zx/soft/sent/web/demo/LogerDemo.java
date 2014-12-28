package zx.soft.sent.web.demo;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.core.demo.CoreDemo;
import zx.soft.utils.log.LogbackUtil;

public class LogerDemo {

	private static Logger logger = LoggerFactory.getLogger(LogerDemo.class);

	public static void main(String[] args) throws UnsupportedEncodingException {

		logger.info("Start logging ...");
		logger.info("Finish logging ...");

		try {
			CoreDemo.main(null);
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

}
