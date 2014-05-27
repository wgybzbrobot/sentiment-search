package zx.soft.sent.web.application;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.web.domain.QueryResult;
import zx.soft.sent.web.resource.SentGetResource;
import zx.soft.sent.web.resource.SentSearchResource;
import zx.soft.sent.web.sentiment.QueryParams;
import zx.soft.sent.web.sentiment.SearchingData;

public class SentiSearchApplication extends Application {

	private final SearchingData searchingData;

	public SentiSearchApplication() {
		searchingData = new SearchingData();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/search", SentSearchResource.class);
		router.attach("/get", SentGetResource.class);
		return router;
	}

	public QueryResult queryData(QueryParams queryParams) {
		return searchingData.queryData(queryParams);
	}

	public void close() {
		searchingData.close();
	}

}
