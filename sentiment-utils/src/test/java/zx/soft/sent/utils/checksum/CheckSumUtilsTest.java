package zx.soft.sent.utils.checksum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CheckSumUtilsTest {

	@Test
	public void testString2MD5() {
		assertEquals("a906449d5769fa7361d7ecc6aa3f6d28", CheckSumUtils.getMD5("123abc"));
	}

	@Test
	public void testCRC32() {
		assertTrue(CheckSumUtils.getCRC32("123abcfffeettt") == 4042279602L);
	}

}
