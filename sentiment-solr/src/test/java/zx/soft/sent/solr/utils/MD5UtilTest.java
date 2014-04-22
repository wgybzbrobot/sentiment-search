package zx.soft.sent.solr.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import zx.soft.sent.solr.utils.MD5Util;

public class MD5UtilTest {

	@Test
	public void testString2MD5() {
		assertEquals("a906449d5769fa7361d7ecc6aa3f6d28", MD5Util.str2MD5("123abc"));
	}

}
