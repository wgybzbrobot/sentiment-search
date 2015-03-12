package zx.soft.sent.solr.allinternet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.allinternet.AllInternet;
import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.allinternet.InternetTask;
import zx.soft.sent.dao.oracle.OracleJDBC;
import zx.soft.sent.solr.search.SearchingData;
import zx.soft.utils.checksum.CheckSumUtils;
import zx.soft.utils.log.LogbackUtil;
import zx.soft.utils.threads.ApplyThreadPool;
import zx.soft.utils.time.TimeUtils;

/**
 * 读取Oracle数据库中的全网任务信息，并查询结果存储缓存信息
 *
 * @author wanggang
 *
 */
public class TaskUpdate {

	private static Logger logger = LoggerFactory.getLogger(TaskUpdate.class);

	// 线程池
	private static ThreadPoolExecutor pool = ApplyThreadPool.getThreadPoolExector();

	// 读取Oracle中任务信息类
	private OracleJDBC oracleJDBC;
	// 搜索查询类
	private SearchingData search;
	// 持久化类
	private AllInternet allInternet;

	// 查询需要更新缓存信息的任务
	public static final String QUERY_UPDATED = "select t1.id,t1.gjc,t1.cjsj,t1.jssj,t3.sourceid from "
			+ "JHRW_RWDD t1,JHRW_RWZX t2,FLLB_CJLB t3 where t1.rwzt in (0,1) and t1.bz=1 and "
			+ "t1.id=t2.rwjhid and t2.sswz=t3.id"; // and t1.jssj>sysdate-30，因为有些没有结束时间，所以不能使用结束时间限定

	// 查询最近一天内已经完成的任务
	public static final String QUERY_FINISHED = "select t1.id,t1.gjc,t1.cjsj,t1.jssj,t3.sourceid from "
			+ "JHRW_RWDD t1,JHRW_RWZX t2,FLLB_CJLB t3 where t1.rwzt=2 and t1.bz=1 and t1.id=t2.rwjhid "
			+ "and t2.sswz=t3.id and t1.jssj>sysdate-1";

	public TaskUpdate() {
		this.oracleJDBC = new OracleJDBC();
		this.search = new SearchingData();
		this.allInternet = new AllInternet(MybatisConfig.ServerEnum.sentiment);
		// 遇到异常平滑关闭线程池
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				pool.shutdown();
			}
		}));
	}

	public static void main(String[] args) throws SQLException {
		TaskUpdate taskUpdate = new TaskUpdate();
		while (true) {
			taskUpdate.tackleExecutedTasks();
			taskUpdate.tackleFinishedTasks();
		}
		//		taskUpdate.close(); // 多线程不能关闭
	}

	/**
	 * 获取需要更新的任务
	 */
	public void tackleExecutedTasks() {
		HashMap<String, InternetTask> tasks;
		try {
			tasks = getTasks(QUERY_UPDATED);
			if (tasks == null) {
				return;
			}
			logger.info("Updating executed tasks' size={}", tasks.size());
			for (Entry<String, InternetTask> tmp : tasks.entrySet()) {
				pool.execute(new TaskUpdateRunnable(search, allInternet, tmp.getValue()));
			}
		} catch (SQLException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取一天内已经完成的任务
	 */
	public void tackleFinishedTasks() {
		HashMap<String, InternetTask> tasks;
		try {
			tasks = getTasks(QUERY_FINISHED);
			if (tasks == null) {
				return;
			}
			logger.info("Updating finished tasks' size={}", tasks.size());
			for (Entry<String, InternetTask> tmp : tasks.entrySet()) {
				pool.execute(new TaskUpdateRunnable(search, allInternet, tmp.getValue()));
			}
		} catch (SQLException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取需要更新的任务信息
	 */
	public HashMap<String, InternetTask> getTasks(String query) throws SQLException {
		HashMap<String, InternetTask> result = new HashMap<>();
		ResultSet rs = oracleJDBC.query(query);
		if (rs == null) {
			logger.info("No tasks!");
			return null;
		}
		String id = "";
		InternetTask task = null;
		while (rs.next()) {
			if (!id.equals(rs.getString("id"))) {
				if (task != null) {
					if (result.get(task.getIdentify()) == null) {
						result.put(task.getIdentify(), task);
					} else {
						InternetTask tmp = result.get(task.getIdentify());
						tmp.setSource_ids(tmp.getSource_ids() + "," + task.getSource_ids());
						result.put(task.getIdentify(), tmp);
					}
				}
				task = new InternetTask(CheckSumUtils.getMD5(
						rs.getString("id") + rs.getString("gjc")
								+ TimeUtils.transToSolrDateStr(rs.getTimestamp("cjsj").getTime())).toUpperCase(), //
						rs.getString("gjc"), //
						TimeUtils.transToSolrDateStr(rs.getTimestamp("cjsj").getTime()), //
						(rs.getTimestamp("jssj") == null) ? TimeUtils.transToSolrDateStr(System.currentTimeMillis())
								: TimeUtils.transToSolrDateStr(rs.getTimestamp("jssj").getTime()), //
						rs.getString("sourceid"));
			} else {
				task.setSource_ids(task.getSource_ids() + "," + rs.getString("sourceid"));
			}
			id = rs.getString("id");
		}

		return result;
	}

	public void close() {
		oracleJDBC.close();
		search.close();
		pool.shutdown();
		try {
			pool.awaitTermination(300, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

}
