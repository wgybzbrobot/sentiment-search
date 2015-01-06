package zx.soft.sent.web.resource;

import java.util.concurrent.ThreadPoolExecutor;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.application.SentiIndexApplication;
import zx.soft.sent.web.domain.ErrorResponse;
import zx.soft.sent.web.domain.PostData;
import zx.soft.utils.codec.URLCodecUtils;
import zx.soft.utils.log.LogbackUtil;
import zx.soft.utils.threads.ApplyThreadPool;

/**
 * 舆情索引资源类
 * 
 * @author wanggang
 *
 */
public class SentIndexResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(SentIndexResource.class);

	private static SentiIndexApplication application;

	private static ThreadPoolExecutor pool = ApplyThreadPool.getThreadPoolExector();

	static {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				pool.shutdown();
			}
		}));
	}

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application = (SentiIndexApplication) getApplication();
	}

	@Post("json")
	public Object acceptData(final PostData data) {

		if (getReference().getRemainingPart().length() != 0) {
			logger.error("query params is illegal, Url:{}", getReference());
			return new ErrorResponse.Builder(20003, "your query params is illegal.").build();
		}

		if (data == null) {
			logger.info("Records' size=0");
			return new ErrorResponse.Builder(20003, "no post data.").build();
		}
		logger.info("Records' Size:{}", data.getRecords().size());

		try {
			// 添加到Mysql并Add索引
			// 注：将这句代码另外开一个线程后台执行，以免影响客户端响应时间
			pool.execute(new Thread(new Runnable() {
				@Override
				public void run() {
					// 这里面以及包含了错误日志记录
					application.persist(data.getRecords());
					// Add索引
					application.addDatasWithoutCommit(data.getRecords());
				}
			}));
			// 添加到Solr，这样会导致IOException，由于IO瓶颈导致。
			//			List<String> unsuccessful = application.addDatas(data.getRecords());
			//			if (unsuccessful.size() == 0) {
			//				return new ErrorResponse.Builder(0, "ok").build();
			//			} else {
			//				for (String id : unsuccessful) {
			//					logger.info("Indexing error data's ID:{}", id);
			//				}
			//				return new IndexErrResponse(-1, unsuccessful);
			//			}
			return new ErrorResponse.Builder(0, "ok").build();
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			return new ErrorResponse.Builder(-1, "persist error!").build();
		}
	}

	@Get("json")
	public Object getQueryResult() {
		return "no message!";
	}

}
