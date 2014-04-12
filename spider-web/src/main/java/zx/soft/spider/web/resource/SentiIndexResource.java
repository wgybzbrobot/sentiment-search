package zx.soft.spider.web.resource;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.spider.web.application.SentiIndexApplication;
import zx.soft.spider.web.common.ApplyThreadPool;
import zx.soft.spider.web.common.ErrorResponse;
import zx.soft.spider.web.common.StringPattern;

public class SentiIndexResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(SentiIndexResource.class);

	private static SentiIndexApplication application;

	private static ThreadPoolExecutor pool = ApplyThreadPool.getThreadPoolExector();

	private String platform;

	@Override
	public void doInit() {
		application = (SentiIndexApplication) getApplication();
		platform = (String) this.getRequest().getAttributes().get("platform");
	}

	@Post("json")
	public Object acceptData(final List<Object> datas) {
		logger.info("Request Url: " + getReference() + ".");
		if (!StringPattern.isAllNum(platform)) {
			return new ErrorResponse.Builder(20003, "your query params is illegal.").build();
		}

		// 另开一个线程索引数据，以免影响客户端响应时间
		pool.execute(new Runnable() {
			@Override
			public void run() {
				application.addDatas(Integer.parseInt(platform), datas);
			}
		});

		return new ErrorResponse.Builder(0, "ok").build();
	}

	@Get("json")
	public Object getQueryResult() {
		//
		return "no message!";
	}

}
