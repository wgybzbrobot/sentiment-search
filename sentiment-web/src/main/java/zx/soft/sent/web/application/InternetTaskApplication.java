package zx.soft.sent.web.application;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.query.QueryCore;
import zx.soft.sent.web.domain.Task;
import zx.soft.sent.web.resource.InternetTaskResource;

public class InternetTaskApplication extends Application {

	private final QueryCore queryCore;

	public InternetTaskApplication() {
		queryCore = new QueryCore();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// POST专题信息
		router.attach("", InternetTaskResource.class);
		return router;
	}

	/**
	 * 查询OA首页查询数据
	 */
	public List<Long> taskResult(List<Task> tasks) {
		List<Long> result = new ArrayList<>();
		QueryParams queryParams = null;
		QueryResult queryResult = null;
		for (Task task : tasks) {
			queryParams = new QueryParams();
			queryParams.setQ(task.getKeywords());
			if (task.getFq().length() > 0) {
				queryParams.setFq(task.getFq());
			}
			queryResult = queryCore.queryData(queryParams, Boolean.FALSE);
			result.add(queryResult.getNumFound());
		}
		return result;
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		queryCore.close();
	}

}
