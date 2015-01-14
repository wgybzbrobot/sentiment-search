package zx.soft.sent.web.application;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.solr.search.SearchingData;
import zx.soft.sent.web.domain.InternetTask;
import zx.soft.sent.web.resource.InternetTaskResource;

public class InternetTaskApplication extends Application {

	private static Logger logger = LoggerFactory.getLogger(InternetTaskApplication.class);

	private final SearchingData searchingData;

	public InternetTaskApplication() {
		searchingData = new SearchingData();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// POST专题信息
		router.attach("/{type}/{datestr}", InternetTaskResource.class);
		return router;
	}

	/**
	 * 查询OA首页查询数据
	 */
	public String taskResult(InternetTask internetTask) {
		List<String> result = new ArrayList<>();
		return null;
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		searchingData.close();
	}

}
