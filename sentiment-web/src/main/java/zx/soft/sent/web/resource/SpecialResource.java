package zx.soft.sent.web.resource;

import java.util.ArrayList;

import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.application.SpecialApplication;
import zx.soft.sent.web.common.ErrorResponse;
import zx.soft.sent.web.domain.SpecialTopic;
import zx.soft.sent.web.utils.URLCodecUtils;

/**
 * 专题资源类
 * 
 * @author wanggang
 *
 */
public class SpecialResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(SpecialResource.class);

	private SpecialApplication application;

	@Override
	public void doInit() {
		application = (SpecialApplication) getApplication();
	}

	@Post("json")
	public Object addSpecialTopics(final SpecialTopics specialTopics) {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application.insertSpecialInfos(specialTopics);
		return new ErrorResponse.Builder(0, "ok").build();
	}

	@Delete
	public Object rmSpecialTopic(String identify) {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application.deleteSpecialInfo(identify);
		return new ErrorResponse.Builder(0, "ok").build();
	}

	/**
	 * 解决Jackson无法识别java.util.Collection问题。
	 *
	 */
	public static class SpecialTopics extends ArrayList<SpecialTopic> {

		private static final long serialVersionUID = 1144770130427640340L;

	}

}
