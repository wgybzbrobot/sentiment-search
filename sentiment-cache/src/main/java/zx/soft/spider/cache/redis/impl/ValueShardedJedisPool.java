package zx.soft.spider.cache.redis.impl;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;

public class ValueShardedJedisPool extends Pool<ValueShardedJedis> {

	/**
	 * PoolableObjectFactory custom impl.
	 */
	private static class ShardedJedisFactory extends BasePoolableObjectFactory {

		private final List<JedisShardInfo> shards;
		private final Hashing algo;

		public ShardedJedisFactory(List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
			this.shards = shards;
			this.algo = algo;
		}

		@Override
		public void destroyObject(final Object obj) throws Exception {
			if ((obj != null) && (obj instanceof ValueShardedJedis)) {
				ValueShardedJedis shardedJedis = (ValueShardedJedis) obj;
				for (Jedis jedis : shardedJedis.getAllShards()) {
					try {
						try {
							jedis.quit();
						} catch (Exception e) {
							//
						}
						jedis.disconnect();
					} catch (Exception e) {
						//
					}
				}
			}
		}

		@Override
		public Object makeObject() throws Exception {
			ValueShardedJedis jedis = new ValueShardedJedis(shards, algo);
			return jedis;
		}

		@Override
		public boolean validateObject(final Object obj) {
			try {
				ShardedJedis jedis = (ShardedJedis) obj;
				for (Jedis shard : jedis.getAllShards()) {
					if (!shard.ping().equals("PONG")) {
						return false;
					}
				}
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
	}

	public ValueShardedJedisPool(final GenericObjectPool.Config poolConfig, List<JedisShardInfo> shards) {
		this(poolConfig, shards, Hashing.MURMUR_HASH);
	}

	public ValueShardedJedisPool(final GenericObjectPool.Config poolConfig, List<JedisShardInfo> shards, Hashing algo) {
		this(poolConfig, shards, algo, null);
	}

	public ValueShardedJedisPool(final GenericObjectPool.Config poolConfig, List<JedisShardInfo> shards, Hashing algo,
			Pattern keyTagPattern) {
		super(poolConfig, new ShardedJedisFactory(shards, algo, keyTagPattern));
	}

	public ValueShardedJedisPool(final GenericObjectPool.Config poolConfig, List<JedisShardInfo> shards,
			Pattern keyTagPattern) {
		this(poolConfig, shards, Hashing.MURMUR_HASH, keyTagPattern);
	}
}