package zx.soft.sent.dao.special;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.special.SpecialResult;
import zx.soft.sent.dao.domain.special.SpecialTopic;
import zx.soft.utils.log.LogbackUtil;

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
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 插入专题信息
	 */
	public void insertSpecialInfo(String identify, String name, String keywords, String start, String end, int hometype) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			specialQueryMapper.insertSpecialInfo(new SpecialTopic(identify, name, keywords, start, end, hometype));
		}
	}

	/**
	 * 查询专题信息
	 */
	public SpecialTopic selectSpecialInfo(String identify) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			return specialQueryMapper.selectSpecialInfo(identify);
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
	public void insertSpecialResult(String identify, String type, String result) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			specialQueryMapper.insertSpecialResult(new SpecialResult(identify, type, result));
		}
	}

	/**
	 * 查询专题identify，按时间查询
	 */
	public List<String> selectSpecialIdentifyByTime(Date lasttime) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			return specialQueryMapper.selectSpecialIdentifyByTime(lasttime);
		}
	}

	/**
	 * 查询专题查询结果
	 */
	public String selectSpecialResult(String identify, String type) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			return specialQueryMapper.selectSpecialResult(identify, type);
		}
	}

	/**
	 * 更新专题查询结果的时间，在每次查询后更新时间
	 */
	public void updateSpecialResultLasttime(String identify) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			specialQueryMapper.updateSpecialResultLasttime(identify);
		}
	}

	/**
	 * 更新专题查询结果
	 */
	public void updateSpecialResult(String identify, String type, String result) {
		try (SqlSession session = sqlSessionFactory.openSession();) {
			SpecialQueryMapper specialQueryMapper = session.getMapper(SpecialQueryMapper.class);
			specialQueryMapper.updateSpecialResult(new SpecialResult(identify, type, result));
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
