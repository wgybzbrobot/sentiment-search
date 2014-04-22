package zx.soft.sent.cache.redis.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;
import zx.soft.sent.cache.utils.Config;

public class ShardedJedisPoolTest {

	ShardedJedisPool pool;

	@Before
	public void setUp() {
		JedisPoolConfig config =new JedisPoolConfig();//Jedis池配置
		config.setMaxTotal(50); //最大活动的对象个数
		config.setMaxIdle(60_000); //对象最大空闲时间
		config.setMaxWaitMillis(10_000); //获取对象时最大等待时间
		config.setTestOnBorrow(true);
		String hostA = Config.get("redis.servers");
		int portA = Integer.parseInt(Config.get("redis.port"));
		String hostB = Config.get("redis.servers");
		int portB = Integer.parseInt(Config.get("redis.port"));
		List<JedisShardInfo> jdsInfoList =new ArrayList<JedisShardInfo>(2);
		JedisShardInfo infoA = new JedisShardInfo(hostA, portA);
		infoA.setPassword(Config.get("redis.password"));
		JedisShardInfo infoB = new JedisShardInfo(hostB, portB);
		infoB.setPassword(Config.get("redis.password"));
		jdsInfoList.add(infoA);
		jdsInfoList.add(infoB);
		pool = new ShardedJedisPool(config, jdsInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
	}

	@After
	public void down() {
		pool.destroy();
	}

	@Test
	public void simpleTest() {
		for (int i = 0; i < 10; i++) {
			String key = generateKey();
			key += "{aaa}";
			ShardedJedis jds = null;
			try {
				jds = pool.getResource();
				System.out.println(key + ":" + jds.getShard(key).getClient().getHost());
				System.out.println(jds.set(key, "testShardedJedis"));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.returnResource(jds);
			}
		}
	}

	private static int index = 1;

	public static String generateKey(){
		return String.valueOf(Thread.currentThread().getId()) + "_" + (index++);
	}

}
