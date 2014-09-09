package zx.soft.sent.web.application;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.firstpage.FirstPage;
import zx.soft.sent.solr.firstpage.OAFirstPage;
import zx.soft.sent.web.resource.FirstPageResource;

public class FirstPageApplication extends Application {

	//	private static Logger logger = LoggerFactory.getLogger(FirstPageApplication.class);

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

	@Override
	public void stop() throws Exception {
		super.stop();
		oafirstPage.close();
	}

}
