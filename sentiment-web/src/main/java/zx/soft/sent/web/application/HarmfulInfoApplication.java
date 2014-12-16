package zx.soft.sent.web.application;

import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.solr.firstpage.OAFirstPage;
import zx.soft.sent.solr.utils.HarmfulInfoUtil;
import zx.soft.sent.web.resource.HarmfulInfoResource;

public class HarmfulInfoApplication extends Application {

	private final OAFirstPage firstPage;

	public HarmfulInfoApplication() {
		firstPage = new OAFirstPage();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/{keywords}/{num}", HarmfulInfoResource.class);
		return router;
	}

	/**
	 * 当天有害信息
	 * @param num：返回条数
	 * @param q：查询关键词
	 * @return
	 */
	public List<SolrDocument> getTodayNegativeRecords(int num, String q) {
		List<SolrDocument> result = firstPage.getTodayNegativeRecords(7, 100, q);
		result = HarmfulInfoUtil.getTopNNegativeRecords(result, num);
		return result;
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		firstPage.close();
	}

}
