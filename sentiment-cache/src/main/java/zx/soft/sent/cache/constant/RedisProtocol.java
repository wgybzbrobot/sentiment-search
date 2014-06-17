package zx.soft.sent.cache.constant;

/**
 * Redis通信常量
 * 
 * @author wanggang
 *
 */
public class RedisProtocol {

	/*
	 * 默认端口
	 */
	public static final int DEFAULT_PORT = 6379;

	/*
	 * 默认的检测端口
	 */
	public static final int DEFAULT_SENTINEL_PORT = 26379;

	/*
	 * 默认的最大总连接数
	 */
	public static final int DEFAULT_MAX_TOTAL = 300;

	/*
	 * 默认的最大空闲数
	 */
	public static final int DEFAULT_MAX_IDLE = 100;

	/*
	 * 默认的超时时间
	 */
	public static final int DEFAULT_TIMEOUT = 2_000;

	/*
	 * 默认的最小项空闲时间
	 */
	public static final int DEFAULT_MIN_EVICTABLE_IDLETIME = 60_000;

	/*
	 * 默认的最大等待时间
	 */
	public static final int DEFAULT_MAX_WAITTIME = 30_000;

	/*
	 * 默认项运行间隔时间
	 */
	public static final int DEFAULT_TIME_BETWEEN_EVICTION_RUNS = 30_000;

	/*
	 * 默认的数据库
	 */
	public static final int DEFAULT_DATABASE = 0;

	/*
	 * 默认编码
	 */
	public static final String CHARSET = "UTF-8";

}
