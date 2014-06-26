package zx.soft.sent.dao.spidersite;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.SiteInfo;

public class SpiderSite {

	private static Logger logger = LoggerFactory.getLogger(SpiderSite.class);

	private static SqlSessionFactory sqlSessionFactory;

	public SpiderSite(MybatisConfig.ServerEnum server) {
		try {
			sqlSessionFactory = MybatisConfig.getSqlSessionFactory(server);
		} catch (RuntimeException e) {
			logger.error("SentimentRecord RuntimeException: " + e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 插入站点信息
	 */
	public void insertSiteInfo(SiteInfo siteInfo) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpiderSiteMapper spiderSiteMapper = session.getMapper(SpiderSiteMapper.class);
			spiderSiteMapper.insertSiteInfo(siteInfo);
		}
	}

	/**
	 * 根据站点Id返回站点名称
	 */
	public String getSourceName(int source_id) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpiderSiteMapper spiderSiteMapper = session.getMapper(SpiderSiteMapper.class);
			return spiderSiteMapper.getSourceName(source_id);
		}
	}

	/**
	 * 根据站点名称返回站点Id
	 */
	public String getSourceId(String source_name) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpiderSiteMapper spiderSiteMapper = session.getMapper(SpiderSiteMapper.class);
			return spiderSiteMapper.getSourceId(source_name);
		}
	}

	/**
	 * 根据站点Id删除站点信息
	 */
	public void deleteSiteById(int source_id) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpiderSiteMapper spiderSiteMapper = session.getMapper(SpiderSiteMapper.class);
			spiderSiteMapper.deleteSiteById(source_id);
		}
	}

}
