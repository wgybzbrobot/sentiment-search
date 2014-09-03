package zx.soft.sent.dao.firstpage;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.firstpage.FirstPageInfo;

/**
 * OA首页查询信息
 * 
 * @author wanggang
 *
 */
public class FirstPage {

	private static Logger logger = LoggerFactory.getLogger(FirstPage.class);

	private final SqlSessionFactory sqlSessionFactory;

	public FirstPage(MybatisConfig.ServerEnum server) {
		try {
			sqlSessionFactory = MybatisConfig.getSqlSessionFactory(server);
		} catch (RuntimeException e) {
			logger.error("SpecialQuery RuntimeException: " + e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 插入OA首页查询数据
	 */
	public void insertFirstPage(int type, String timestr, String result) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			FirstPageMapper firstPageMapper = sqlSession.getMapper(FirstPageMapper.class);
			firstPageMapper.insertFirstPage(new FirstPageInfo(type, timestr, result));
		}
	}

	/**
	 * 更新OA首页查询数据
	 */
	public void updateFirstPage(int type, String timestr, String result) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			FirstPageMapper firstPageMapper = sqlSession.getMapper(FirstPageMapper.class);
			firstPageMapper.updateFirstPage(new FirstPageInfo(type, timestr, result));
		}
	}

	/**
	 * 查询OA首页查询数据
	 */
	public String selectFirstPage(int type, String timestr) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			FirstPageMapper firstPageMapper = sqlSession.getMapper(FirstPageMapper.class);
			return firstPageMapper.selectFirstPage(type, timestr);
		}
	}

	/**
	 * 删除OA首页查询数据
	 */
	public void deleteFirstPage(int type, String timestr) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			FirstPageMapper firstPageMapper = sqlSession.getMapper(FirstPageMapper.class);
			firstPageMapper.deleteFirstPage(type, timestr);
		}
	}

}
