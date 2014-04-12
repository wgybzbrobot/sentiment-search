package zx.soft.spider.web.resource;

import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.spider.web.application.SentiSearchApplication;
import zx.soft.spider.web.common.ErrorResponse;
import zx.soft.spider.web.sentiment.QueryParams;

public class SentiSearchResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(SentiSearchResource.class);

	private static SentiSearchApplication application;

	private final QueryParams queryParams = new QueryParams();

	@Override
	public void doInit() {
		application = (SentiSearchApplication) getApplication();
		Form form = getRequest().getReferrerRef().getQueryAsForm();
		for (Parameter param : form) {
			if ("q".equalsIgnoreCase(param.getName())) {
				queryParams.setQ(param.getValue());
			}
			// 待处理
		}
	}

	@Get("json")
	public Object getQueryResult() {
		logger.info("Request Url: " + getReference() + ".");
		if (getReference().getRemainingPart() == null) {
			return new ErrorResponse.Builder(20003, "your query params is illegal.").build();
		}
		return application.queryData(queryParams);
	}

}
