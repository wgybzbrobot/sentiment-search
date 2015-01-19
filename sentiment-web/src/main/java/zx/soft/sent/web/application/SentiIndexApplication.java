package zx.soft.sent.web.application;

import java.util.List;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.core.persist.PersistCore;
import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.sent.solr.utils.RedisMQ;
import zx.soft.sent.web.resource.SentIndexResource;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.log.LogbackUtil;

/**
 * 舆情索引应用类
 * 
 * @author wanggang
 *
 */
public class SentiIndexApplication extends Application {

	private static Logger logger = LoggerFactory.getLogger(SentiIndexApplication.class);

	//	private final IndexCloudSolr indexCloudSolr;

	private final PersistCore persistCore;

	private final RedisMQ redisMQ;

	static Thread commitThread;

	public SentiIndexApplication() {
		//		indexCloudSolr = new IndexCloudSolr();
		persistCore = new PersistCore();
		redisMQ = new RedisMQ();
		/**
		 * 原来每分钟定时提交更新，由于数据量很大改为1秒
		 */
		//		commitThread = new Thread(new Runnable() {
		//			@Override
		//			public void run() {
		//				Timer timer = new Timer();
		//				timer.schedule(new TimerCommit(), 0, 10 * 1000);
		//			}
		//		});
		//		commitThread.start();
		//		logger.info("SolrCloud committed start ...");
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		getContext().getParameters().add("maxThreads", "1024");
		getContext().getParameters().add("minThreads", "128");
		getContext().getParameters().add("lowThreads", "256");
		getContext().getParameters().add("maxConnectionsPerHost", "1024");
		getContext().getParameters().add("initialConnections", "256");
		getContext().getParameters().add("maxTotalConnections", "1024");
		getContext().getParameters().add("maxIoIdleTimeMs", "10000");
		//		System.out.println(getContext().getParameters().getValues("maxTotalConnections"));
		router.attach("", SentIndexResource.class);
		return router;
	}

	/**
	 * 添加索引数据，这样对于每条数据都进行Add操作，会导致IO瓶颈
	 * @return 未索引成功的ID列表
	 */
	/*	@Deprecated
		public List<String> addDatas(List<RecordInfo> records) {
			List<String> result = new ArrayList<>();
			for (RecordInfo record : records) {
				if (!indexCloudSolr.addSentimentDocToSolr(record)) {
					result.add(record.getId());
				}
			}
			return result;
		}*/

	/**
	 * 添加索引数据，调一次接口索引Add一次，这样减少IO
	 */
	/*	@Deprecated
		public void addDatasWithoutCommit(List<RecordInfo> records) {
			indexCloudSolr.addDocsToSolr(records);
		}*/

	/**
	 * 数据持久化到Redis
	 */
	public void addToRedis(List<RecordInfo> records) {
		String[] data = new String[records.size()];
		for (int i = 0; i < records.size(); i++) {
			if (records.get(i).getPic_url().length() > 500) {
				records.get(i).setPic_url(records.get(i).getPic_url().substring(0, 500));
			}
			data[i] = JsonUtils.toJsonWithoutPretty(records.get(i));
		}
		try {
			redisMQ.addRecord(data);
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

	/**
	 * 数据持久化到Mysql
	 */
	public void persist(List<RecordInfo> records) {
		for (RecordInfo record : records) {
			if (record.getPic_url().length() > 500) {
				record.setPic_url(record.getPic_url().substring(0, 500));
			}
			persistCore.persist(record);
		}
	}

	/**
	 * 定时提交更新索引
	 */
	/*	@Deprecated
		public class TimerCommit extends TimerTask {

			private final AtomicInteger count = new AtomicInteger(0);

			@Override
			public void run() {
				indexCloudSolr.commitToSolr();
				logger.info("SolrCloud committed " + count.addAndGet(1) + ".");
			}

		}*/

	public void close() {
		//		commitThread.interrupt(); // 可能需要修改
		//		indexCloudSolr.close();
	}

}
