package zx.soft.spider.cache.redis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import zx.soft.spider.cache.dao.Cache;
import zx.soft.spider.cache.utils.Config;

public class RedisCache implements Cache {

	private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

	private final ValueShardedJedisPool pool;

	public RedisCache(String redisServers) {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		for (String server : redisServers.split(",")) {
			server = server.trim();
			int port = Integer.parseInt(Config.get("port"));
			if (server.indexOf(":") != -1) {
				String[] hostAndPort = server.split(":");
				server = hostAndPort[0];
				port = Integer.parseInt(hostAndPort[1]);
			}
			logger.info("Add redis server: {}:{}", server, port);
			JedisShardInfo si = new JedisShardInfo(server, port, 600_000);
			shards.add(si);
		}
		JedisPoolConfig config = new JedisPoolConfig();
		pool = new ValueShardedJedisPool(config, shards);
	}

	@Override
	public Long del(String... keys) {
		ValueShardedJedis jedis = pool.getResource();
		try {
			return jedis.del(keys);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public boolean exists(String key) {
		ValueShardedJedis jedis = pool.getResource();
		try {
			return jedis.exists(key);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public void eval(String script, String[] keys, String... members) {
		ValueShardedJedis jedis = pool.getResource();
		try {
			jedis.eval(script, keys, members);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public void sadd(String key, String... members) {
		ValueShardedJedis jedis = pool.getResource();
		try {
			jedis.sadd(key, members);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public Long scard(String key) {
		ValueShardedJedis jedis = pool.getResource();
		try {
			return jedis.scard(key);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public boolean sismember(String key, String member) {
		ValueShardedJedis jedis = pool.getResource();
		try {
			return jedis.sismember(key, member);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public Set<String> smembers(String key) {
		ValueShardedJedis jedis = pool.getResource();
		try {
			return jedis.smembers(key);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public String spop(String key) {
		ValueShardedJedis jedis = pool.getResource();
		try {
			return jedis.spop(key);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public String srandmember(String key) {
		ValueShardedJedis jedis = pool.getResource();
		try {
			return jedis.srandmember(key);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public Set<String> srandmember(String key, int count) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long srem(String key, String... members) {
		ValueShardedJedis jedis = pool.getResource();
		try {
			return jedis.srem(key, members);
		} finally {
			pool.returnResource(jedis);
		}
	}

}
