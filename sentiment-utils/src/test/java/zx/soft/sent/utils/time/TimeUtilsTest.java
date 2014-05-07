package zx.soft.sent.utils.time;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeUtilsTest {

	@Test
	public void testTransToSolrDateStr() {
		String str = TimeUtils.transToSolrDateStr(System.currentTimeMillis());
		assertTrue(str.length() == 20);
		assertTrue(str.contains("T"));
		assertTrue(str.contains("Z"));
	}

	@Test
	public void testTransStrToCommonDateStr() {
		String str = TimeUtils.transToCommonDateStr(TimeUtils.transToSolrDateStr(System.currentTimeMillis()));
		assertTrue(str.length() == 19);
		assertFalse(str.contains("T"));
		assertFalse(str.contains("Z"));

		assertTrue(TimeUtils.transStrToCommonDateStr("Thu Apr 10 11:40:56 CST 2014").contains("10 11:40:56"));
	}

	@Test
	public void testTransLongToCommonDateStr() {
		String str = TimeUtils.transToCommonDateStr(System.currentTimeMillis());
		assertTrue(str.length() == 19);
		assertFalse(str.contains("T"));
		assertFalse(str.contains("Z"));
	}

}
