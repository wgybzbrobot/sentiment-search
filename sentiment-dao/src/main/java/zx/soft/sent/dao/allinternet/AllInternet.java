package zx.soft.sent.dao.allinternet;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.allinternet.InternetTask;
import zx.soft.utils.log.LogbackUtil;

/**
 * OA全网搜索任务DAO层操作
 *
 * @author wanggang
 *
 */
public class AllInternet {

	private static Logger logger = LoggerFactory.getLogger(AllInternet.class);

	private static SqlSessionFactory sqlSessionFactory;

	public AllInternet(MybatisConfig.ServerEnum server) {
		try {
			sqlSessionFactory = MybatisConfig.getSqlSessionFactory(server);
		} catch (RuntimeException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 任务信息插入
	 */
	public void insertInternetTask(InternetTask internetTask) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			mapper.insertInternetTask(internetTask);
		}
	}

	/**
	 * 任务信息查询：根据identify
	 */
	public InternetTask selectInternetTask(String identify) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			return mapper.selectInternetTask(identify);
		}
	}

	/**
	 * 任务信息查询：查询未完成任务，并按照最后更新时间降序
	 */
	public List<InternetTask> selectInternetTasks(Date startTime) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			return mapper.selectInternetTasks(startTime);
		}
	}

	/**
	 * 任务信息中isover字段更新
	 */
	public void updateInternetTaskIsOver(String identify, int isOver) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			mapper.updateInternetTaskIsOver(identify, isOver);
		}
	}

	/**
	 * 任务信息最近更新时间字段更新
	 */
	public void updateInternetTaskLasttime(String identify) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			mapper.updateInternetTaskLasttime(identify);
		}
	}

	/**
	 * 任务信息删除
	 */
	public void deleteInternetTask(String identify) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			mapper.deleteInternetTask(identify);
		}
	}

	/**
	 * 任务查询结果插入
	 */
	public void insertInternetTaskQuery(String identify, String queryResult) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			mapper.insertInternetTaskQuery(identify, queryResult);
		}
	}

	/**
	 * 任务查询结果查询
	 */
	public String selectInternetTaskQuery(String identify) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			return mapper.selectInternetTaskQuery(identify);
		}
	}

	/**
	 * 任务查询结果更新
	 */
	public void updateInternetTaskQuery(String identify, String queryResult) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			mapper.updateInternetTaskQuery(identify, queryResult);
		}
	}

	/**
	 * 任务查询结果删除
	 */
	public void deleteInternetTaskQuery(String identify) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			mapper.deleteInternetTaskQuery(identify);
		}
	}

}
