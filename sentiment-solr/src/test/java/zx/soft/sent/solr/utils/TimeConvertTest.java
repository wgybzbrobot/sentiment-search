package zx.soft.sent.solr.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeConvertTest {

	@Test
	public void testTransTimeStr() {
		System.out.println(TimeConvert.transTimeStr("2014-08-25 00:00:00"));
		assertEquals("2014-08-25T00:00:00Z", TimeConvert.transTimeStr("2014-08-25 00:00:00"));
	}
}
