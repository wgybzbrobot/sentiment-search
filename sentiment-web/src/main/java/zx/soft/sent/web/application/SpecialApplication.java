package zx.soft.sent.web.application;

import java.util.List;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.special.SpecialQuery;
import zx.soft.sent.web.domain.SpecialTopic;
import zx.soft.sent.web.resource.SpecialResource;

/**
 * 专题模块应用类
 * 
 * @author wanggang
 *
 */
public class SpecialApplication extends Application {

	private static Logger logger = LoggerFactory.getLogger(SpecialApplication.class);

	private final SpecialQuery specialQuery;

	public SpecialApplication() {
		specialQuery = new SpecialQuery(MybatisConfig.ServerEnum.sentiment);
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// POST专题信息
		router.attach("/add", SpecialResource.class);
		// DELETE专题信息
		router.attach("/{identify}/remove", SpecialResource.class);
		return router;
	}

	/**
	 * 插入专题信息
	 */
	public void insertSpecialInfo(SpecialTopic specialTopic) {
		if (specialTopic.getIdentify() == null || specialTopic.getIdentify().length() == 0) {
			logger.error("SpecialTopic has null Identify.");
		} else {
			specialQuery.insertSpecialInfo(specialTopic.getIdentify(), specialTopic.getKeywords(),
					specialTopic.getStart(), specialTopic.getEnd(), specialTopic.getHometype());
		}
	}

	public void insertSpecialInfos(List<SpecialTopic> specialTopics) {
		for (SpecialTopic specialTopic : specialTopics) {
			insertSpecialInfo(specialTopic);
		}
	}

	/**
	 * 删除专题信息
	 */
	public void deleteSpecialInfo(String identify) {
		specialQuery.deleteSpecialInfo(identify);
	}

	/**
	 * 插入专题查询结果
	 */
	public void insertSpecialResult(String identify, String result) {
		specialQuery.insertSpecialResult(identify, result);
	}

	/**
	 * 查询专题查询结果
	 */
	public String selectSpecialResult(String identify) {
		return specialQuery.selectSpecialResult(identify);
	}

	/**
	 * 更新专题查询结果
	 */
	public void updateSpecialResult(String identify, String result) {
		specialQuery.updateSpecialResult(identify, result);
	}

	/**
	 * 删除专题查询结果
	 */
	public void deleteSpecialResult(String identify) {
		specialQuery.deleteSpecialResult(identify);
	}

}
