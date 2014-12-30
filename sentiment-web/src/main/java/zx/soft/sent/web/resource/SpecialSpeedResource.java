package zx.soft.sent.web.resource;

import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.application.SpecialSpeedApplication;
import zx.soft.sent.web.application.SpecialSpeedApplication.SpecialResult;
import zx.soft.sent.web.domain.ErrorResponse;
import zx.soft.utils.codec.URLCodecUtils;

/**
 * 专题资源类
 * 
 * @author wanggang
 *
 */
public class SpecialSpeedResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(SpecialSpeedResource.class);

	private SpecialSpeedApplication application;

	private String identifys = "";

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application = (SpecialSpeedApplication) getApplication();
		identifys = (String) this.getRequest().getAttributes().get("identifys");
	}

	@Get("json")
	public Object getSpecialResult() {
		if (identifys == null || identifys.length() == 0) {
			logger.error("Params `identifys` is null.");
			return new ErrorResponse.Builder(-1, "params error!").build();
		}
		List<SpecialResult> queryResult = application.selectSpecialResult(identifys);
		return queryResult;
	}

}
