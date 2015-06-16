package zx.soft.sent.solr.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.RedisCache;
import zx.soft.redis.client.common.Config;
import zx.soft.sent.dao.oracle.OracleJDBC;
import zx.soft.utils.log.LogbackUtil;

/**
 * 将Oracle中的站点数据读取到Redis中: hefei09
 *
 * 运行目录：/home/zxdfs/run-work/site
 * 运行命令：./timer.sh &
 *
 * @author wanggang
 *
 */
public class OracleToRedis {

	private static Logger logger = LoggerFactory.getLogger(OracleToRedis.class);

	public OracleToRedis() {
		//
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		//		Timer timer = new Timer();
		//		// 每小时执行一次
		//		timer.schedule(new OracleToRedisTask(), 0, 3600 * 1000);
		OracleToRedis oracleToRedis = new OracleToRedis();
		oracleToRedis.siteMapToRedis();
	}

	/**
	 * 定时执行任务
	 */
	public static class OracleToRedisTask extends TimerTask {

		public OracleToRedisTask() {
			//
		}

		@Override
		public void run() {
			OracleToRedis oracleToRedis = new OracleToRedis();
			oracleToRedis.siteMapToRedis();
		}

	}

	public void siteMapToRedis() {
		logger.info("Start importing data ...");
		Cache cache = new RedisCache(Config.get("redis.rp.master"), Integer.parseInt(Config.get("redis.rp.port")),
				Config.get("redis.password"));
		OracleJDBC oracleJDBC = new OracleJDBC();
		ResultSet rs = oracleJDBC.query("SELECT ID,ZDMC FROM FLLB_CJLB");
		try {
			while (rs.next()) {
				//				System.out.println(rs.getInt("ID") + "," + rs.getString("ZDMC"));
				cache.hset(zx.soft.sent.dao.common.SentimentConstant.SITE_MAP, rs.getInt("ID") + "",
						rs.getString("ZDMC"));
			}
		} catch (SQLException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		} finally {
			oracleJDBC.close();
			cache.close();
		}
		logger.info("Finish importing data ...");
	}

}
