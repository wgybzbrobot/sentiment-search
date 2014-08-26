package zx.soft.sent.dao.special;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.special.InsertSpecialInfo;
import zx.soft.sent.dao.domain.special.InsertSpecialResult;

/**
 * 专题信息查询缓存类
 * 
 * @author wanggang
 *
 */
public class SpecialQuery {

	private static Logger logger = LoggerFactory.getLogger(SpecialQuery.class);

	private static SqlSessionFactory sqlSessionFactory;

	public SpecialQuery(MybatisConfig.ServerEnum server) {
		try {
			sqlSessionFactory = MybatisConfig.getSqlSessionFactory(server);
		} catch (RuntimeException e) {
			logger.error("SpecialQuery RuntimeException: " + e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 插入专题信息
	 */
	public void insertSpecialInfo(String identify, String keywords, String start, String end, int hometype) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			specialQueryMapper.insertSpecialInfo(new InsertSpecialInfo(identify, keywords, start, end, hometype));
		}
	}

	/**
	 * 删除专题信息
	 */
	public void deleteSpecialInfo(String identify) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			specialQueryMapper.deleteSpecialInfo(identify);
		}
	}

	/**
	 * 插入专题查询结果
	 */
	public void insertSpecialResult(String identify, String result) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			specialQueryMapper.insertSpecialResult(new InsertSpecialResult(identify, result));
		}
	}

	/**
	 * 查询专题查询结果
	 */
	public String selectSpecialResult(String identify) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			return specialQueryMapper.selectSpecialResult(identify);
		}
	}

	/**
	 * 更新专题查询结果
	 */
	public void updateSpecialResult(String identify, String result) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			specialQueryMapper.updateSpecialResult(new InsertSpecialResult(identify, result));
		}
	}

	/**
	 * 删除专题查询结果
	 */
	public void deleteSpecialResult(String identify) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			specialQueryMapper.deleteSpecialResult(identify);
		}
	}

}
