package zx.soft.sent.web.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.application.HarmfulInfoApplication;
import zx.soft.sent.web.common.ErrorResponse;
import zx.soft.utils.chars.JavaPattern;
import zx.soft.utils.codec.URLCodecUtils;

public class HarmfulInfoResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(HarmfulInfoResource.class);

	private HarmfulInfoApplication application;

	private String keywords = "";
	private String num = "";

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application = (HarmfulInfoApplication) getApplication();
		keywords = (String) this.getRequest().getAttributes().get("keywords");
		num = (String) this.getRequest().getAttributes().get("num");
	}

	@Get("json")
	public Object getSpecialResult() {
		if (keywords == null || keywords.length() == 0 || num == null || num.length() == 0
				|| !JavaPattern.isAllNum(num)) {
			logger.error("Params `keywords` or `num` is null.");
			return new ErrorResponse.Builder(-1, "params error!").build();
		}
		try {
			return application.getTodayNegativeRecords(Integer.parseInt(num), URLCodecUtils.decoder(keywords, "UTF-8"));
		} catch (Exception e) {
			logger.error("Exception:{}, StackTrace:{}", e.getMessage(), e.getStackTrace());
			return new ErrorResponse.Builder(-1, "params error!").build();
		}
	}

}
