package zx.soft.sent.solr.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;

import zx.soft.sent.utils.config.ConfigUtil;

public class ConfigTest {

	@Test
	public void testGetProps() {
		assertNotNull(ConfigUtil.getProps("data_db.properties"));
	}

	@Ignore
	public void testSolrProps() {
		Properties props = ConfigUtil.getProps("solr_params.properties");
		assertTrue(props.getProperty("zookeeper_cloud").length() > 0);
		assertTrue(props.getProperty("zookeeper_connect_timeout").length() > 0);
		assertTrue(props.getProperty("zookeeper_client_timeout").length() > 0);
		assertTrue(props.getProperty("fetch_size").length() > 0);
	}

}
