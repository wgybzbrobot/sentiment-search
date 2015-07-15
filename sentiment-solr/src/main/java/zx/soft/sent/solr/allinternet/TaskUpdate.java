package zx.soft.sent.solr.allinternet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.allinternet.AllInternet;
import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.allinternet.InternetTask;
import zx.soft.sent.dao.oracle.OracleJDBC;
import zx.soft.sent.solr.query.QueryCore;
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
	private QueryCore queryCore;
	// 持久化类
	private AllInternet allInternet;

	// 查询需要更新缓存信息的任务
	public static final String QUERY_EXECUTED = "select id,gjc,cjsj,jssj,cjzid,sourceid from JHRW_WWC";

	// 查询最近一天内已经完成的任务
	public static final String QUERY_FINISHED = "select id,gjc,cjsj,jssj,cjzid,sourceid from JHRW_YWC";

	// 所有任务
	public static final String ALL_TASKS = "select id,gjc,cjsj,jssj,cjzid,sourceid from JHRW_ALL";

	public TaskUpdate() {
		this.oracleJDBC = new OracleJDBC();
		this.queryCore = new QueryCore();
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
		// 只进行一次
		//		taskUpdate.tackleAllTasks();
		// 循环更新
		while (true) {
			taskUpdate.tackleExecutedTasks();
			taskUpdate.tackleFinishedTasks();
			// 休息30秒
			try {
				Thread.sleep(30_000);
			} catch (InterruptedException e) {
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			}
		}
		//		taskUpdate.close(); // 多线程不能关闭
	}

	/**
	 * 获取全部任务
	 */
	public void tackleAllTasks() {
		logger.info("Updating all tasks ...");
		tackleTasks(ALL_TASKS);
	}

	/**
	 * 获取需要更新的任务
	 */
	public void tackleExecutedTasks() {
		logger.info("Updating executed tasks ...");
		tackleTasks(QUERY_EXECUTED);
	}

	/**
	 * 获取一天内已经完成的任务
	 */
	public void tackleFinishedTasks() {
		logger.info("Updating finished tasks ...");
		tackleTasks(QUERY_FINISHED);
	}

	private void tackleTasks(String query) {
		HashMap<String, InternetTask> tasks;
		try {
			tasks = getTasks(query);
			if (tasks == null) {
				return;
			}
			// 增加每个任务中source_id去重功能
			tasks = duplicateTask(tasks);
			// 插入任务，并更新数据
			logger.info("Updating tasks' size={}", tasks.size());
			int count = 1;
			for (Entry<String, InternetTask> tmp : tasks.entrySet()) {
				logger.info("Updating at: {}/{}", count++, tasks.size());
				pool.execute(new TaskUpdateRunnable(queryCore, allInternet, tmp.getValue()));
			}
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	private HashMap<String, InternetTask> duplicateTask(HashMap<String, InternetTask> tasks) {
		HashMap<String, InternetTask> result = new HashMap<>();
		InternetTask it = null;
		Set<String> sids = null;
		StringBuffer sb = null;
		for (Entry<String, InternetTask> task : tasks.entrySet()) {
			it = task.getValue();
			sids = new TreeSet<>();
			sb = new StringBuffer();
			for (String sourceId : it.getSource_ids().split(",")) {
				sids.add(sourceId);
			}
			for (String sid : sids) {
				sb.append(sid).append(",");
			}
			result.put(
					task.getKey(),
					new InternetTask(it.getIdentify(), it.getKeywords(), it.getStart_time(), it.getEnd_time(), sb
							.substring(0, sb.length() - 1)));
		}
		return result;
	}

	/**
	 * 获取需要更新的任务信息
	 */
	private HashMap<String, InternetTask> getTasks(String query) throws SQLException {
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
						rs.getString("id") + rs.getString("cjzid")
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
		// 任务为空
		if ((task == null) || (result.size() == 0)) {
			return null;
		}
		// 最后一个添加
		if (result.get(task.getIdentify()) == null) {
			result.put(task.getIdentify(), task);
		} else {
			InternetTask tmp = result.get(task.getIdentify());
			tmp.setSource_ids(tmp.getSource_ids() + "," + task.getSource_ids());
			result.put(task.getIdentify(), tmp);
		}

		return result;
	}

	public void close() {
		oracleJDBC.close();
		queryCore.close();
		pool.shutdown();
		try {
			pool.awaitTermination(300, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

}
