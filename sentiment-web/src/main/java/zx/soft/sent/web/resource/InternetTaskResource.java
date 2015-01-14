package zx.soft.sent.web.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.application.InternetTaskApplication;
import zx.soft.sent.web.domain.ErrorResponse;
import zx.soft.sent.web.domain.InternetTask;
import zx.soft.utils.codec.URLCodecUtils;

public class InternetTaskResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(InternetTaskResource.class);

	private InternetTaskApplication application;

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application = (InternetTaskApplication) getApplication();
	}

	@Get("json")
	public Object taskResult(InternetTask internetTask) {
		return new ErrorResponse.Builder(-1, "params error!").build();
	}

}
