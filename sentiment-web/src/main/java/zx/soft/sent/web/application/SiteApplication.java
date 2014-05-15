package zx.soft.sent.web.application;

import java.util.List;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.cache.dao.Cache;
import zx.soft.sent.cache.factory.CacheFactory;
import zx.soft.sent.utils.checksum.CheckSumUtils;
import zx.soft.sent.web.resource.SiteResource;

public class SiteApplication extends Application {

	private static Cache cache;

	public static final String SITE_GROUPS = "sent:site:groups";

	public SiteApplication() {
		cache = CacheFactory.getInstance();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("", SiteResource.class);
		return router;
	}

	/**
	 * 插入站点组合数据
	 */
	public void insertSiteGroups(List<String> data) {
		for (String sites : data) {
			// 设置hash表
			cache.hset(SITE_GROUPS, CheckSumUtils.getMD5(sites), sites);
		}
	}

	public void close() {
		cache.close();
	}

}
