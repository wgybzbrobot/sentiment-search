package zx.soft.sent.web.application;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.solr.domain.QueryParams;
import zx.soft.sent.solr.domain.QueryResult;
import zx.soft.sent.solr.query.QueryCore;
import zx.soft.sent.web.resource.SentSearchResource;

/**
 * 舆情搜索应用类
 *
 * @author wanggang
 *
 */
public class SentiSearchApplication extends Application {

	private final QueryCore queryCore;

	public SentiSearchApplication() {
		queryCore = new QueryCore();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		getContext().getParameters().add("maxThreads", "512");
		getContext().getParameters().add("minThreads", "100");
		getContext().getParameters().add("lowThreads", "200");
		getContext().getParameters().add("maxConnectionsPerHost", "128");
		getContext().getParameters().add("initialConnections", "255");
		getContext().getParameters().add("maxTotalConnections", "1024");
		getContext().getParameters().add("maxIoIdleTimeMs", "100");
		router.attach("/search", SentSearchResource.class);
		//		router.attach("/get", SentGetResource.class);
		return router;
	}

	public QueryResult queryData(QueryParams queryParams) {
		return queryCore.queryData(queryParams, Boolean.TRUE);
	}

	public void close() {
		queryCore.close();
	}

}
