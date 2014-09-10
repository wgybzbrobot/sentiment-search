package zx.soft.sent.web.application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.firstpage.FirstPage;
import zx.soft.sent.solr.firstpage.OAFirstPage;
import zx.soft.sent.utils.json.JsonNodeUtils;
import zx.soft.sent.web.resource.FirstPageResource;

public class FirstPageApplication extends Application {

	//	private static Logger logger = LoggerFactory.getLogger(FirstPageApplication.class);

	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat FORMATTER_HH = new SimpleDateFormat("yyyy-MM-dd,HH");

	private final FirstPage firstPage;
	private final OAFirstPage oafirstPage;

	public FirstPageApplication() {
		firstPage = new FirstPage(MybatisConfig.ServerEnum.sentiment);
		oafirstPage = new OAFirstPage();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// POST专题信息
		router.attach("/{type}/{datestr}", FirstPageResource.class);
		return router;
	}

	/**
	 * 查询OA首页查询数据
	 */
	public String selectFirstPage(int type, String timestr) {
		return firstPage.selectFirstPage(type, timestr);
	}

	/**
	 * 查询OA首页查询数据，type=1,2
	 */
	public HashMap<String, Long> selectFirstPageType12(int type) {
		return trans2Map(firstPage.selectFirstPage(type, getCurrentHH()));
	}

	/**
	 * 查询OA首页查询数据，type=4
	 */
	public HashMap<String, Long> selectFirstPageType4() {
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		HashMap<String, Long> result = new HashMap<>();
		HashMap<String, Long> temp = null;
		for (int i = 0; i <= hour; i = i + 3) {
			String timestr = getCurrentYearMonthDay();
			if (i < 10) {
				timestr += ",0" + i;
			} else {
				timestr += "," + i;
			}
			temp = trans2Map(firstPage.selectFirstPage(4, timestr));
			System.out.println(temp);
			for (Entry<String, Long> t : temp.entrySet()) {
				if (result.get(t.getKey()) == null) {
					result.put(t.getKey(), t.getValue());
				} else {
					result.put(t.getKey(), t.getValue() + result.get(t.getKey()));
				}
			}
		}
		return result;
	}

	private HashMap<String, Long> trans2Map(String jsonStr) {
		HashMap<String, Long> result = new HashMap<>();
		JsonNode node = JsonNodeUtils.getJsonNode(jsonStr);
		Iterator<String> keys = node.getFieldNames();
		String key = null;
		while (keys.hasNext()) {
			key = keys.next();
			result.put(key, Long.parseLong(node.get(key).toString()));
		}

		return result;
	}

	private String getCurrentYearMonthDay() {
		return FORMATTER.format(new Date());
	}

	private String getCurrentHH() {
		return FORMATTER_HH.format(new Date());
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		oafirstPage.close();
	}

}
