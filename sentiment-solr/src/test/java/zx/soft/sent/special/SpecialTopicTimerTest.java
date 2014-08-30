package zx.soft.sent.special;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SpecialTopicTimerTest {

	@Test
	public void testGetTimestampFilterQuery() {
		String start = "2014-08-25 00:00:00";
		String end = "2014-08-25 23:59:59";
		assertEquals("timestamp:[2014-08-25T00:00:00Z TO 2014-08-25T23:59:59Z]",
				SpecialTopicTimer.SpecialTopicTasker.getTimestampFilterQuery(start, end));
	}

}
