package zx.soft.sent.web.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.application.FirstPageApplication;
import zx.soft.sent.web.common.ErrorResponse;
import zx.soft.sent.web.utils.JavaPattern;
import zx.soft.sent.web.utils.URLCodecUtils;

public class FirstPageResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(FirstPageResource.class);

	private FirstPageApplication application;

	private String type = "";
	private String timestr = "";

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application = (FirstPageApplication) getApplication();
		type = (String) this.getRequest().getAttributes().get("type");
		timestr = (String) this.getRequest().getAttributes().get("day");
	}

	@Get("json")
	public Object getSpecialResult() {
		if (type == null || type.length() == 0 || timestr == null || timestr.length() == 0
				|| !JavaPattern.isAllNum(type)) {
			logger.error("Params `type` or `timestr` is null.");
			return new ErrorResponse.Builder(-1, "params error!").build();
		}
		return application.selectFirstPage(Integer.parseInt(type), timestr);
	}

}
