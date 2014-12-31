package zx.soft.sent.web.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.core.persist.PersistCore;
import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.sent.solr.index.IndexCloudSolr;
import zx.soft.sent.web.resource.SentIndexResource;

/**
 * 舆情索引应用类
 * 
 * @author wanggang
 *
 */
public class SentiIndexApplication extends Application {

	private static Logger logger = LoggerFactory.getLogger(SentiIndexApplication.class);

	private final IndexCloudSolr indexCloudSolr;

	private final PersistCore persistCore;

	static Thread commitThread;

	public SentiIndexApplication() {
		indexCloudSolr = new IndexCloudSolr();
		persistCore = new PersistCore();
		/**
		 * 原来每分钟定时提交更新，由于数据量很大改为10秒
		 */
		commitThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Timer timer = new Timer();
				timer.schedule(new TimerCommit(), 0, 10 * 1000);
			}
		});
		commitThread.start();
		logger.info("SolrCloud committed start ...");
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("", SentIndexResource.class);
		return router;
	}

	/**
	 * 添加索引数据
	 * @return 未索引成功的ID列表
	 */
	public List<String> addDatas(List<RecordInfo> records) {
		List<String> result = new ArrayList<>();
		for (RecordInfo record : records) {
			if (!indexCloudSolr.addSentimentDocToSolr(record)) {
				result.add(record.getId());
			}
		}
		return result;
	}

	/**
	 * 数据持久化到Mysql
	 */
	public void persist(List<RecordInfo> records) {
		for (RecordInfo record : records) {
			persistCore.persist(record);
		}
	}

	/**
	 * 定时提交更新索引
	 */
	public class TimerCommit extends TimerTask {

		private final AtomicInteger count = new AtomicInteger(0);

		@Override
		public void run() {
			indexCloudSolr.commitToSolr();
			logger.info("SolrCloud committed " + count.addAndGet(1) + ".");
		}

	}

	public void close() {
		commitThread.interrupt(); // 可能需要修改
		indexCloudSolr.close();
	}

}
