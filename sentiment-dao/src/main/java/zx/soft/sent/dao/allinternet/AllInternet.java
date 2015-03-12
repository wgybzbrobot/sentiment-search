package zx.soft.sent.dao.allinternet;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.allinternet.InternetTask;
import zx.soft.sent.dao.domain.allinternet.TaskResult;
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
	 * 插入任务的缓存信息
	 */
	public void insertInternetTask(InternetTask internetTask) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			mapper.insertInternetTask(internetTask);
		}
	}

	/**
	 * 查询任务的缓存信息
	 */
	public TaskResult selectInternetTask(String identify) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			return mapper.selectInternetTask(identify);
		}
	}

	/**
	 * 判断任务是否存在
	 */
	public boolean isInternetTaskExisted(String identify) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			if (mapper.selectInternetTaskIdentify(identify) == null) {
				return Boolean.FALSE;
			} else {
				return Boolean.TRUE;
			}
		}
	}

	/**
	 * 更新任务的缓存信息
	 */
	public void updateInternetTask(InternetTask internetTask) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			mapper.updateInternetTask(internetTask);
		}
	}

	/**
	 * 更新任务的最新查询时间lasttime
	 */
	public void updateInternetTaskQuerytime(String identify) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			mapper.updateInternetTaskQuerytime(identify);
		}
	}

	/**
	 * 查询固定时间内的所有任务identify
	 */
	public List<String> selectInternetTaskIdentifys(Date startTime) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			return mapper.selectInternetTaskIdentifys(startTime);
		}
	}

	/**
	 * 删除任务的缓存信息
	 */
	public void deleteInternetTask(String identify) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			AllInternetMapper mapper = sqlSession.getMapper(AllInternetMapper.class);
			mapper.deleteInternetTask(identify);
		}
	}

}
