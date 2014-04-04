package zx.soft.spider.solr.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConfigTest {

	@Test
	public void testGet() {
		assertTrue(Config.get("db_url").length() > 0);
		assertTrue(Config.get("db_username").length() > 0);
		assertTrue(Config.get("db_password").length() > 0);
	}

	@Test
	public void testGetProps() {
		assertNotNull(Config.getProps("data_db.properties"));
	}

}
