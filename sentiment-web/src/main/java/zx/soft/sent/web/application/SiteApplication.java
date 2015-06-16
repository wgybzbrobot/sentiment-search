package zx.soft.sent.web.application;

import java.util.List;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.redis.client.cache.Cache;
import zx.soft.redis.client.cache.RedisCache;
import zx.soft.redis.client.common.Config;
import zx.soft.sent.dao.common.SentimentConstant;
import zx.soft.sent.web.resource.SiteResource;
import zx.soft.utils.checksum.CheckSumUtils;

/**
 * 站点应用类
 *
 * @author wanggang
 *
 */
public class SiteApplication extends Application {

	private static Cache cache;

	public SiteApplication() {
		// 写入数据用master节点
		cache = new RedisCache(Config.get("redis.rp.master"), Integer.parseInt(Config.get("redis.rp.port")),
				Config.get("redis.password"));
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
			cache.hset(SentimentConstant.SITE_GROUPS, CheckSumUtils.getMD5(sites), sites);
		}
	}

	public void close() {
		cache.close();
	}

}
