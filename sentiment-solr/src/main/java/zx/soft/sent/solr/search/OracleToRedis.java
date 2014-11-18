package zx.soft.sent.solr.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.cache.dao.Cache;
import zx.soft.sent.cache.factory.CacheFactory;
import zx.soft.sent.dao.oracle.OracleJDBC;

/**
 * 将Oracle中的站点数据读取到Redis中
 * 
 * @author wanggang
 *
 */
public class OracleToRedis {

	private static Logger logger = LoggerFactory.getLogger(OracleToRedis.class);

	public static final String SITE_MAP = "sent:site:map";

	public static final String SITE_GROUPS = "sent:site:groups";

	public OracleToRedis() {
		//
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		Timer timer = new Timer();
		// 每小时执行一次
		timer.schedule(new OracleToRedisTask(), 0, 3600 * 1000);
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
		Cache cache = CacheFactory.getInstance();
		OracleJDBC oracleJDBC = new OracleJDBC();
		ResultSet rs = oracleJDBC.query("SELECT ID,ZDMC FROM FLLB_CJLB");
		try {
			while (rs.next()) {
				cache.hset(SITE_MAP, rs.getInt("ID") + "", rs.getString("ZDMC"));
			}
		} catch (SQLException e) {
			logger.error("SQLException at OracleToRedis: " + e.getMessage());
		} finally {
			oracleJDBC.close();
			cache.close();
		}
		logger.info("Finish importing data ...");
	}

}
