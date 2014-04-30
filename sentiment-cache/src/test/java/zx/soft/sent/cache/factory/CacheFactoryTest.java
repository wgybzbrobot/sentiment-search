package zx.soft.sent.cache.factory;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import zx.soft.sent.cache.dao.Cache;

@Ignore
public class CacheFactoryTest {

	@Test
	public void testEval() {
		Cache cache = CacheFactory.getInstance();
		cache.del("k1", "k2", "k3");

		cache.sadd("k1", "v1", "v2");
		cache.sadd("k2", "v3", "v4");
		assertEquals(0L, cache.scard("k3").longValue());
	}
}
