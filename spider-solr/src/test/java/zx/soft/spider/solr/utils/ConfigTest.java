package zx.soft.spider.solr.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

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

	@Test
	public void testSolrProps() {
		Properties props = Config.getProps("solr_params.properties");
		assertTrue(props.getProperty("zookeeper_cloud").length() > 0);
		assertTrue(props.getProperty("zookeeper_connect_timeout").length() > 0);
		assertTrue(props.getProperty("zookeeper_client_timeout").length() > 0);
		assertTrue(props.getProperty("fetch_size").length() > 0);
	}

}
