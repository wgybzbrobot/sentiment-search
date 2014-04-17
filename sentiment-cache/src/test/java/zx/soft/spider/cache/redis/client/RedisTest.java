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

public class RedisTest {

	JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");

	@Test
	public void test() {
		Jedis jedis = pool.getResource();
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
		Jedis j1 = pool.getResource();

		j1.del("s");
		try {
			Object result = j1.eval("return 'hello world'");
			assertEquals("hello world", result);
			assertEquals(1L, j1.eval("return 1 == 0 or 0 == 0"));
			assertEquals(1L, j1.eval("return 0 == 0 or 1 == 0"));
			assertEquals(3L, j1.eval("local count = 0\n" //
					+ "for i, v in ipairs(ARGV) do count = count + 1 end\n" //
					+ "return count" //
			, 0, "a", "b", "c"));
		} finally {
			pool.returnResource(j1);
		}
		pool.destroy();
	}

	@Test
	public void testEval2() {
		Jedis j = pool.getResource();

		j.del("s1", "s2", "s3");
		try {
			j.sadd("s1", "1", "2", "3");
			j.sadd("s2", "4", "5");

			String src = "if redis.call('sismember', KEYS[1], ARGV[1]) == 0 and redis.call('sismember', KEYS[2], ARGV[1]) == 0 then\n" //
					+ "    redis.call('sadd', KEYS[3], ARGV[1])\n" //
					+ "    return 1\n" //
					+ "end\n" //
					+ "return 0";
			assertEquals(0L, j.eval(src, 3, "s1", "s2", "s3", "1"));
			assertEquals(0L, j.eval(src, 3, "s1", "s2", "s3", "4"));
			assertEquals(false, j.sismember("s3", "6"));
			assertEquals(1L, j.eval(src, 3, "s1", "s2", "s3", "6"));
			assertEquals(true, j.sismember("s3", "6"));
		} finally {
			pool.returnResource(j);
		}
		pool.destroy();
	}

	@Test
	public void testEval3() {
		Jedis j = pool.getResource();

		j.del("s1", "s2", "s3");
		try {
			j.sadd("s1", "1", "2", "3");
			j.sadd("s2", "4", "5");

			String src = "local count = 0\n" //
					+ "for i, uid in ipairs(ARGV) do\n" //
					+ "    if redis.call('sismember', KEYS[1], uid) == 0 and redis.call('sismember', KEYS[2], uid) == 0 then\n" //
					+ "        redis.call('sadd', KEYS[3], uid)\n" //
					+ "        count = count + 1\n" //
					+ "    end\n" //
					+ "end\n" //
					+ "return count";
			assertEquals(0L, j.eval(src, 3, "s1", "s2", "s3", "1"));
			assertEquals(0L, j.eval(src, 3, "s1", "s2", "s3", "4"));
			assertEquals(false, j.sismember("s3", "6"));
			assertEquals(1L, j.eval(src, 3, "s1", "s2", "s3", "6"));
			assertEquals(true, j.sismember("s3", "6"));

			assertEquals(2L, j.eval(src, 3, "s1", "s2", "s3", "1", "3", "4", "6", "8"));
			assertEquals(true, j.sismember("s3", "6"));
			assertEquals(true, j.sismember("s3", "8"));
		} finally {
			pool.returnResource(j);
		}
		pool.destroy();
	}

	@Test(expected = JedisDataException.class)
	public void testEvalsha() {
		Jedis j = pool.getResource();

		j.del("s1", "s2", "s3");
		try {
			j.evalsha("ffffffffffffffffffffffffffffffffffffffff", 3, "s1", "s2", "s3", "1", "3", "4", "6", "8");
		} finally {
			pool.returnResource(j);
		}
		pool.destroy();
	}

	@Test
	public void testEvalsha2() {
		Jedis j = pool.getResource();

		j.del("s1", "s2", "s3");
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
				j.evalsha(shaHex, 3, "s1", "s2", "s3", "1", "3", "4", "6", "8");
			} catch (JedisDataException e) {
				j.eval(src, 3, "s1", "s2", "s3", "1", "3", "4", "6", "8");
			}
		} finally {
			pool.returnResource(j);
		}
		pool.destroy();
	}

	@Test
	public void testSet() {
		Jedis j1 = pool.getResource();
		Jedis j2 = pool.getResource();
		j1.del("s");
		try {
			j1.sadd("s", "1");
			assertTrue(j1.sismember("s", "1"));
			assertFalse(j1.sismember("s", "2"));
			assertTrue(j2.sismember("s", "1"));

			assertEquals("1", j1.spop("s"));
			assertNull(j1.spop("s"));
		} finally {
			pool.returnResource(j1);
			pool.returnResource(j2);
		}
		pool.destroy();
	}

}
