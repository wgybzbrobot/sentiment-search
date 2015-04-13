package zx.soft.sent.dao.firstpage;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.firstpage.FirstPageInfo;
import zx.soft.utils.log.LogbackUtil;

/**
 * OA首页查询信息
 *
 * @author wanggang
 *
 */
public class FirstPageHarmful {

	private static Logger logger = LoggerFactory.getLogger(FirstPageHarmful.class);

	private final SqlSessionFactory sqlSessionFactory;

	public FirstPageHarmful(MybatisConfig.ServerEnum server) {
		try {
			sqlSessionFactory = MybatisConfig.getSqlSessionFactory(server);
		} catch (RuntimeException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 插入OA首页查询数据
	 */
	public void insertFirstPage(int type, String timestr, String result) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			FirstPageHarmfulMapper firstPageMapper = sqlSession.getMapper(FirstPageHarmfulMapper.class);
			firstPageMapper.insertFirstPage(new FirstPageInfo(type, timestr, result));
		}
	}

	/**
	 * 更新OA首页查询数据
	 */
	public void updateFirstPage(int type, String timestr, String result) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			FirstPageHarmfulMapper firstPageMapper = sqlSession.getMapper(FirstPageHarmfulMapper.class);
			firstPageMapper.updateFirstPage(new FirstPageInfo(type, timestr, result));
		}
	}

	/**
	 * 查询OA首页查询数据
	 */
	public String selectFirstPage(int type, String timestr) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			FirstPageHarmfulMapper firstPageMapper = sqlSession.getMapper(FirstPageHarmfulMapper.class);
			return firstPageMapper.selectFirstPage(type, timestr);
		}
	}

	/**
	 * 删除OA首页查询数据
	 */
	public void deleteFirstPage(int type, String timestr) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			FirstPageHarmfulMapper firstPageMapper = sqlSession.getMapper(FirstPageHarmfulMapper.class);
			firstPageMapper.deleteFirstPage(type, timestr);
		}
	}

}
