package zx.soft.sent.solr.allinternet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.allinternet.AllInternet;
import zx.soft.sent.dao.domain.allinternet.InternetTask;
import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.query.QueryCore;
import zx.soft.utils.log.LogbackUtil;

/**
 * OA全网任务查询线程类
 *
 * @author wanggang
 *
 */
public class TaskUpdateRunnable implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(TaskUpdateRunnable.class);

	// 搜索查询类
	private QueryCore queryCore;
	// 持久化类
	private AllInternet allInternet;
	// 一个任务信息
	private InternetTask task;

	// 全局计数器
	//	private static final AtomicInteger COUNT = new AtomicInteger(0);

	public TaskUpdateRunnable(QueryCore queryCore, AllInternet allInternet, InternetTask task) {
		this.queryCore = queryCore;
		this.allInternet = allInternet;
		this.task = task;
	}

	@Override
	public void run() {
		//		System.out.println(COUNT.addAndGet(1));
		try {
			/*
			 * 执行查询
			 */
			// 本地库总量
			QueryParams queryParams = new QueryParams();
			//			queryParams.setQ(task.getKeywords().replaceAll("\"", "")); // 防止单引号问题
			queryParams.setQ(task.getKeywords());
			queryParams.setFq("first_time:[2000-01-01T00:00:00Z TO " + task.getStart_time() + "]");
			queryParams.setRows(0);
			QueryResult result = queryCore.queryData(queryParams, true);
			int localCount = (int) result.getNumFound();
			// 元搜索总量
			queryParams.setFq("first_time:[" + task.getStart_time() + " TO " + task.getEnd_time() + "];" + "source_id:"
					+ task.getSource_ids());
			result = queryCore.queryData(queryParams, true);
			int autmCount = (int) result.getNumFound();

			/*
			 * 执行数据写入MySQL
			 */
			InternetTask newTask = new InternetTask(task.getIdentify(), task.getKeywords(), task.getStart_time(),
					task.getEnd_time(), task.getSource_ids(), localCount, autmCount);
			if (allInternet.isInternetTaskExisted(task.getIdentify())) {
				// 如果该任务缓存信息存在，则更新
				allInternet.updateInternetTask(newTask);
				logger.info("Updating Task:{}", newTask.getIdentify());
			} else {
				// 否则，插入
				allInternet.insertInternetTask(newTask);
				logger.info("Inserting Task:{}", newTask.getIdentify());
			}
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

}
