package zx.soft.sent.dao.sina;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.WeiboOldInfo;
import zx.soft.sent.dao.domain.WeiboSinaParams;

/**
 * 新浪微薄数据CURD类
 * 
 * @author wanggang
 *
 */
public class WeiboSina {

	private static Logger logger = LoggerFactory.getLogger(WeiboSina.class);

	private static SqlSessionFactory sqlSessionFactory;

	public WeiboSina(MybatisConfig.ServerEnum server) {
		try {
			sqlSessionFactory = MybatisConfig.getSqlSessionFactory(server);
		} catch (RuntimeException e) {
			logger.error("WeiboSina RuntimeException: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取最大Id
	 */
	public int getMaxId(String tablename) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			WeiboSinaDao weiboSinaDao = sqlSession.getMapper(WeiboSinaDao.class);
			return weiboSinaDao.getMaxId(new WeiboSinaParams(tablename, 0, 0));
		}
	}

	/**
	 * 获取所有数据表名
	 */
	public List<String> getAllTablenames() {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			WeiboSinaDao weiboSinaDao = sqlSession.getMapper(WeiboSinaDao.class);
			return weiboSinaDao.getAllTablenames();
		}
	}

	/*
	 * 旧数据表
	 */

	/**
	 * 获取新浪微博数据，批量
	 */
	public List<WeiboOldInfo> getBatchWeibos(String tablename, int low, int high) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			WeiboSinaDao weiboSinaDao = sqlSession.getMapper(WeiboSinaDao.class);
			return weiboSinaDao.getBatchWeibos(new WeiboSinaParams(tablename, low, high));
		}
	}

}
