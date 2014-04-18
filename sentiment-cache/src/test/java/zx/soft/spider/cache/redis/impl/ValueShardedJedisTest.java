package zx.soft.spider.cache.redis.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import zx.soft.spider.cache.utils.Config;

public class ValueShardedJedisTest {

	List<JedisShardInfo> shards;

	ValueShardedJedisPool pool;

	@After
	public void down() {
		pool.destroy();
	}

	@Before
	public void setUp() {
		shards = new ArrayList<JedisShardInfo>();
		String password = Config.get("password");
		JedisShardInfo jsi1 = new JedisShardInfo("127.0.0.1", 6381);
		jsi1.setPassword(password);
		JedisShardInfo jsi2 = new JedisShardInfo("127.0.0.1", 6382);
		jsi1.setPassword(password);
		JedisShardInfo jsi3 = new JedisShardInfo("127.0.0.1", 6383);
		jsi1.setPassword(password);
		JedisShardInfo jsi4 = new JedisShardInfo("127.0.0.1", 6384);
		jsi1.setPassword(password);
		shards.add(jsi1);
		shards.add(jsi2);
		shards.add(jsi3);
		shards.add(jsi4);
		pool = new ValueShardedJedisPool(new JedisPoolConfig(), shards);
	}

	@Test
	@Ignore
	public void testBalance() {
		ValueShardedJedis resource = pool.getResource();
		String key = "ValueShardedJedisTest:1";
		resource.del(key);

		long count = 0;
		for (int i = 0; i < 40000; i++) {
			count += resource.sadd(key, String.valueOf(i));
		}
		assertEquals(40000, count);

		for (JedisShardInfo info : shards) {
			count = info.createResource().scard(key);
			assertTrue(count > 9000 && count < 11000);
			// System.out.println(info.createResource().scard(key));
		}
	}

	@Test
	@Ignore
	public void testDel() {
		ValueShardedJedis resource = pool.getResource();

		final String key = "ValueShardedJedisTest:NoThisKey";
		assertEquals(new Long(0), resource.del(key));
		resource.sadd(key, "1");
		assertEquals(new Long(1), resource.del(key));
		assertEquals(new Long(0), resource.del(key));
	}

}

