package zx.soft.spider.cache.redis.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisDataException;
import zx.soft.spider.cache.utils.Config;

public class RedisTest {

	JedisPool pool = new JedisPool(new JedisPoolConfig(), Config.get("redisServers"));

	final String PASSWORD = Config.get("password");

	@Test
	public void test() {
		Jedis jedis = pool.getResource();
		jedis.auth(PASSWORD);
		try {
			/// ... do stuff here ... for example
			jedis.set("foo", "bar");
			assertEquals("bar", jedis.get("foo"));
			jedis.zadd("sose", 0, "car");
			jedis.zadd("sose", 0, "bike");
			assertEquals("[bike, car]", jedis.zrange("sose", 0, -1).toString());
		} finally {
			/// ... it's important to return the Jedis instance to the pool once you've finished using it
			pool.returnResource(jedis);
		}
		/// ... when closing your application:
		pool.destroy();
	}

	@Test
	public void testErval() {
		Jedis jedis = pool.getResource();
		jedis.auth(PASSWORD);
		jedis.del("s");
		try {
			Object result = jedis.eval("return 'hello world'");
			assertEquals("hello world", result);
			assertEquals(1L, jedis.eval("return 1 == 0 or 0 == 0"));
			assertEquals(1L, jedis.eval("return 0 == 0 or 1 == 0"));
			assertEquals(3L, jedis.eval("local count = 0\n" //
					+ "for i, v in ipairs(ARGV) do count = count + 1 end\n" //
					+ "return count" //
			, 0, "a", "b", "c"));
		} finally {
			pool.returnResource(jedis);
		}
		pool.destroy();
	}

	@Test
	public void testEval2() {
		Jedis jedis = pool.getResource();
		jedis.auth(PASSWORD);
		jedis.del("s1", "s2", "s3");
		try {
			jedis.sadd("s1", "1", "2", "3");
			jedis.sadd("s2", "4", "5");

			String src = "if redis.call('sismember', KEYS[1], ARGV[1]) == 0 and redis.call('sismember', KEYS[2], ARGV[1]) == 0 then\n" //
					+ "    redis.call('sadd', KEYS[3], ARGV[1])\n" //
					+ "    return 1\n" //
					+ "end\n" //
					+ "return 0";
			assertEquals(0L, jedis.eval(src, 3, "s1", "s2", "s3", "1"));
			assertEquals(0L, jedis.eval(src, 3, "s1", "s2", "s3", "4"));
			assertEquals(false, jedis.sismember("s3", "6"));
			assertEquals(1L, jedis.eval(src, 3, "s1", "s2", "s3", "6"));
			assertEquals(true, jedis.sismember("s3", "6"));
		} finally {
			pool.returnResource(jedis);
		}
		pool.destroy();
	}

	@Test
	public void testEval3() {
		Jedis jedis = pool.getResource();
		jedis.auth(PASSWORD);
		jedis.del("s1", "s2", "s3");
		try {
			jedis.sadd("s1", "1", "2", "3");
			jedis.sadd("s2", "4", "5");

			String src = "local count = 0\n" //
					+ "for i, uid in ipairs(ARGV) do\n" //
					+ "    if redis.call('sismember', KEYS[1], uid) == 0 and redis.call('sismember', KEYS[2], uid) == 0 then\n" //
					+ "        redis.call('sadd', KEYS[3], uid)\n" //
					+ "        count = count + 1\n" //
					+ "    end\n" //
					+ "end\n" //
					+ "return count";
			assertEquals(0L, jedis.eval(src, 3, "s1", "s2", "s3", "1"));
			assertEquals(0L, jedis.eval(src, 3, "s1", "s2", "s3", "4"));
			assertEquals(false, jedis.sismember("s3", "6"));
			assertEquals(1L, jedis.eval(src, 3, "s1", "s2", "s3", "6"));
			assertEquals(true, jedis.sismember("s3", "6"));

			assertEquals(2L, jedis.eval(src, 3, "s1", "s2", "s3", "1", "3", "4", "6", "8"));
			assertEquals(true, jedis.sismember("s3", "6"));
			assertEquals(true, jedis.sismember("s3", "8"));
		} finally {
			pool.returnResource(jedis);
		}
		pool.destroy();
	}

	@Test(expected = JedisDataException.class)
	public void testEvalsha() {
		Jedis jedis = pool.getResource();
		jedis.auth(PASSWORD);
		jedis.del("s1", "s2", "s3");
		try {
			jedis.evalsha("ffffffffffffffffffffffffffffffffffffffff", 3, "s1", "s2", "s3", "1", "3", "4", "6", "8");
		} finally {
			pool.returnResource(jedis);
		}
		pool.destroy();
	}

	@Test
	public void testEvalsha2() {
		Jedis jedis = pool.getResource();
		jedis.auth(PASSWORD);
		jedis.del("s1", "s2", "s3");
		final String src = "local count = 0\n" //
				+ "for i, uid in ipairs(ARGV) do\n" //
				+ "    if redis.call('sismember', KEYS[1], uid) == 0 and redis.call('sismember', KEYS[2], uid) == 0 then\n" //
				+ "        redis.call('sadd', KEYS[3], uid)\n" //
				+ "        count = count + 1\n" //
				+ "    end\n" //
				+ "end\n" //
				+ "return count";
		String shaHex = DigestUtils.shaHex(src);
		try {
			try {
				jedis.evalsha(shaHex, 3, "s1", "s2", "s3", "1", "3", "4", "6", "8");
			} catch (JedisDataException e) {
				jedis.eval(src, 3, "s1", "s2", "s3", "1", "3", "4", "6", "8");
			}
		} finally {
			pool.returnResource(jedis);
		}
		pool.destroy();
	}

	@Test
	public void testSet() {
		Jedis jedis1 = pool.getResource();
		Jedis jedis2 = pool.getResource();
		jedis1.auth(PASSWORD);
		jedis2.auth(PASSWORD);
		jedis1.del("s");
		try {
			jedis1.sadd("s", "1");
			assertTrue(jedis1.sismember("s", "1"));
			assertFalse(jedis1.sismember("s", "2"));
			assertTrue(jedis2.sismember("s", "1"));

			assertEquals("1", jedis1.spop("s"));
			assertNull(jedis1.spop("s"));
		} finally {
			pool.returnResource(jedis1);
			pool.returnResource(jedis2);
		}
		pool.destroy();
	}

}
