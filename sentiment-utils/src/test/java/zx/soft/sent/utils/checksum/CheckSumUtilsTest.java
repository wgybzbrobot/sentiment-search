package zx.soft.sent.utils.checksum;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CheckSumUtilsTest {

	@Test
	public void testString2MD5() {
		assertEquals("a906449d5769fa7361d7ecc6aa3f6d28", CheckSumUtils.getMD5("123abc"));
	}

}
