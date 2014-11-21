package zx.soft.sent.web.resource;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.sent.web.application.SentiIndexApplication;
import zx.soft.sent.web.common.ErrorResponse;
import zx.soft.sent.web.domain.IndexErrResponse;
import zx.soft.sent.web.domain.PostData;
import zx.soft.utils.threads.ApplyThreadPool;

/**
 * 與请索引资源类
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
		logger.info("Request Url:{}, Records' Size:{}", getReference(), data.getRecords().size());

		for (RecordInfo d : data.getRecords()) {
			logger.info("Indexing data's ID:{}", d.getId());
		}

		try {
			// 添加到Solr
			List<String> unsuccessful = application.addDatas(data.getRecords());
			// 添加到Mysql，注：将这句代码另外开一个线程后台执行，以免影响客户端响应时间
			pool.execute(new Thread(new Runnable() {
				@Override
				public void run() {
					// 这里面以及包含了错误日志记录
					application.persist(data.getRecords());
				}
			}));
			//		System.out.println(JsonUtils.toJson(data));
			if (unsuccessful.size() == 0) {
				return new ErrorResponse.Builder(0, "ok").build();
			} else {
				return new IndexErrResponse(-1, unsuccessful);
			}
		} catch (Exception e) {
			logger.error("Indexing error, Exception:{}", e.getMessage());
			return new ErrorResponse.Builder(-1, "persist error!").build();
		}
	}

	@Get("json")
	public Object getQueryResult() {
		return "no message!";
	}

}
