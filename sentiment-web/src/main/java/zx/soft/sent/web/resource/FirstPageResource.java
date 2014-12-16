package zx.soft.sent.web.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.application.FirstPageApplication;
import zx.soft.sent.web.common.ErrorResponse;
import zx.soft.utils.chars.JavaPattern;
import zx.soft.utils.codec.URLCodecUtils;

public class FirstPageResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(FirstPageResource.class);

	private FirstPageApplication application;

	private String type = "";
	private String datestr = "";

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application = (FirstPageApplication) getApplication();
		type = (String) this.getRequest().getAttributes().get("type");
		datestr = (String) this.getRequest().getAttributes().get("datestr");
	}

	@Get("json")
	public Object getSpecialResult() {
		if (type == null || type.length() == 0 || datestr == null || datestr.length() == 0
				|| !JavaPattern.isAllNum(type)) {
			logger.error("Params `type` or `datestr` is null.");
			return new ErrorResponse.Builder(-1, "params error!").build();
		}
		int t = Integer.parseInt(type);
		Object result = null;
		if (t == 1 || t == 2) {
			result = application.selectFirstPageType12(t, datestr);
		} else if (t == 4) {
			result = application.selectFirstPageType4(datestr);
		} else if (t == 52 || t == 53) {
			result = application.selectFirstPageType5(t, datestr);
		}
		if (result == null) {
			return new ErrorResponse.Builder(-1, "params error!").build();
		} else {
			return result;
		}
	}

}
