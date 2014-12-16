package zx.soft.sent.web.resource;

import java.util.HashMap;

import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.web.common.ErrorResponse;
import zx.soft.utils.codec.URLCodecUtils;
import zx.soft.utils.http.HttpUtils;

/**
 * 與请数据获取资源类
 * 
 * @author wanggang
 *
 */
public class SentGetResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(SentGetResource.class);

	private static final String BASE_URL = "http://192.168.3.21:8983/solr/sentiment/get?id=";

	private String id = "";

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		HashMap<String, String> params = new HashMap<>();
		Form form = getRequest().getResourceRef().getQueryAsForm();
		for (Parameter p : form) {
			if (params.get(p.getName()) == null) {
				params.put(p.getName(), p.getValue());
			} else { // 重复参数以最后一个为准
				params.put(p.getName(), p.getValue());
			}
		}
		if (params.get("id") != null) {
			id = params.get("id");
		} else {
			logger.error("id is null.");
		}
		logger.info("id=" + id);
	}

	@Get("json")
	public Object getQueryResult() {
		if (id.length() == 0) {
			return new ErrorResponse.Builder(20003, "your query params is illegal.").build();
		}
		return HttpUtils.doGet(BASE_URL + id, "utf-8");
	}

}
