package zx.soft.sent.cache.factory;

import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.exceptions.JedisConnectionException;
import zx.soft.sent.cache.dao.Cache;
import zx.soft.sent.cache.redis.impl.RedisCache;
import zx.soft.sent.cache.utils.Config;
import zx.soft.sent.utils.retry.RetryHandler;

/**
 * 缓存工厂类
 * 
 * @author wanggang
 *
 */
public class CacheFactory {

	private static Logger logger = LoggerFactory.getLogger(CacheFactory.class);

	private static Cache instance;

	static {
		try {
			instance = (Cache) Proxy.newProxyInstance(
					Cache.class.getClassLoader(),
					new Class[] { Cache.class },
					new RetryHandler<Cache>(new RedisCache(Config.get("redis.servers"), Integer.parseInt(Config
							.get("redis.port")), Config.get("redis.password")), 5000, 10) {
						@Override
						protected boolean isRetry(Throwable e) {
							return e instanceof JedisConnectionException;
						}
					});
		} catch (Exception e) {
			logger.error("CacheFactory Exception is " + e);
			throw new RuntimeException(e);
		}
	}

	public static Cache getInstance() {
		return instance;
	}

}
