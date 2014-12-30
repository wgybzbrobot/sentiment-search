package zx.soft.sent.solr.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SearchingDataTest {

	@Test
	public void testTransFq() {

		String fqs = SearchingData.transFq("nickname:罗永浩;content:锤子");
		assertEquals(fqs, "nickname:罗永浩;content:锤子");

		fqs = SearchingData.transFq("nickname:罗永浩;-content:锤子");
		assertEquals(fqs, "nickname:罗永浩;-content:锤子");

		fqs = SearchingData.transFq("nickname:罗永浩,北京");
		assertEquals(fqs, "nickname:罗永浩 OR nickname:北京");

		fqs = SearchingData.transFq("-content:锤子,手机");
		assertEquals(fqs, "-content:锤子 AND -content:手机");

		fqs = SearchingData.transFq("a:b,c,d");
		assertEquals(fqs, "a:b OR a:c OR a:d");

		fqs = SearchingData.transFq("-a:b,c,d");
		assertEquals(fqs, "-a:b AND -a:c AND -a:d");

		fqs = SearchingData.transFq("timestamp:[2014-4-22T13:39:26Z TO 2014-4-29T13:39:26Z]");
		assertEquals(fqs, "timestamp:[2014-4-22T13:39:26Z TO 2014-4-29T13:39:26Z]");

	}

}
