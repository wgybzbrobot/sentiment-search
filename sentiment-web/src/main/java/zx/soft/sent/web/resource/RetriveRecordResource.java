package zx.soft.sent.web.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.application.RetriveRecordApplication;
import zx.soft.sent.web.common.ErrorResponse;
import zx.soft.sent.web.utils.URLCodecUtils;

public class RetriveRecordResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(RetriveRecordResource.class);

	private static RetriveRecordApplication application;

	private String ids;

	@Override
	public void doInit() {
		ids = (String) getRequest().getAttributes().get("ids");
		logger.info("ids=" + ids);
		application = (RetriveRecordApplication) getApplication();
		//		HashMap<String, String> params = new HashMap<>();
		//		Form form = getRequest().getResourceRef().getQueryAsForm();
		//		for (Parameter p : form) {
		//			if (params.get(p.getName()) == null) {
		//				params.put(p.getName(), p.getValue());
		//			} else { // 重复参数以最后一个为准
		//				params.put(p.getName(), p.getValue());
		//			}
		//		}
	}

	@Get("json")
	public Object getRecords() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		logger.info("ids=" + ids);
		if (ids == null || ids.length() == 0) {
			return new ErrorResponse.Builder(20003, "your query params is illegal.").build();
		}
		return application.getRecords(ids);
	}

}
