package zx.soft.sent.dao.demo;

import java.util.Date;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.platform.SiteInfo;
import zx.soft.sent.dao.spidersite.SpiderSite;

public class SpiderSiteDemo {

	public static void main(String[] args) {

		SpiderSite spiderSite = new SpiderSite(MybatisConfig.ServerEnum.sentiment);

		SiteInfo siteInfo = new SiteInfo();
		siteInfo.setUrl("http://www.baidu.com");
		siteInfo.setZone(2);
		siteInfo.setDescription("站点描述");
		siteInfo.setSource_name("站点名称");
		siteInfo.setPlatform(8);
		siteInfo.setStatus(200);
		siteInfo.setSpider_id(123);
		siteInfo.setSpider_type("爬虫类型");
		siteInfo.setSource_id(456);
		siteInfo.setSource_type(4);
		siteInfo.setSource_code(10101);
		siteInfo.setContact("站点联系方式");
		siteInfo.setAdmin("站点负责人");
		siteInfo.setRoot(123456789L);
		siteInfo.setParams("参数名键值对列表");
		siteInfo.setUid(987654321L);
		siteInfo.setTimestamp(new Date(1419755627695L));
		siteInfo.setIdentify(111111);

		/**
		 * 插入站点信息
		 */
		spiderSite.insertSiteInfo(siteInfo);

		/**
		 * 根据站点Id返回站点名称
		 */
		System.out.println(spiderSite.getSourceName(456));

		/**
		 * 根据站点名称返回站点Id
		 */
		System.out.println(spiderSite.getSourceId("站点名称"));

		/**
		 * 根据站点Id删除站点信息
		 */
		spiderSite.deleteSiteById(456);

	}

}
