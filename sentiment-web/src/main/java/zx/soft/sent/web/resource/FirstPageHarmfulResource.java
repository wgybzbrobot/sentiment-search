package zx.soft.sent.web.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.application.FirstPageHarmfulApplication;
import zx.soft.sent.web.domain.ErrorResponse;
import zx.soft.utils.chars.JavaPattern;
import zx.soft.utils.codec.URLCodecUtils;

public class FirstPageHarmfulResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(FirstPageHarmfulResource.class);

	private FirstPageHarmfulApplication application;

	private String type = "";
	private String datestr = "";

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application = (FirstPageHarmfulApplication) getApplication();
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
		Object result = application.selectFirstPageType(Integer.parseInt(type), datestr);
		if (result == null) {
			return new ErrorResponse.Builder(-1, "params error!").build();
		} else {
			return result;
		}
	}

}
