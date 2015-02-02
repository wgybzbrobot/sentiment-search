package zx.soft.sent.solr.utils;

import org.junit.Ignore;

public class RedisCacheExpiredTest {

	@Ignore
	public void testExpired() {
		RedisCacheExpired pool = new RedisCacheExpired(60);
		for (int i = 0; i < 30; i++) {
			pool.addRecord("test" + i, i + "");
			try {
				Thread.sleep(1_000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		pool.close();
	}

}
