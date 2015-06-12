package zx.soft.sent.web.resource;

import java.util.List;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.application.SiteApplication;
import zx.soft.sent.web.domain.ErrorResponse;
import zx.soft.utils.codec.URLCodecUtils;
import zx.soft.utils.json.JsonUtils;

/**
 * 站点数据
 *
 * @author wanggang
 *
 */
public class SiteResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(SiteResource.class);

	private SiteApplication application;

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application = (SiteApplication) getApplication();
	}

	@Post("json")
	public Object siteDate(List<String> data) {
		logger.info("Site data is {}.", JsonUtils.toJsonWithoutPretty(data));
		application.insertSiteGroups(data);
		return new ErrorResponse.Builder(0, "ok").build();
	}

}
