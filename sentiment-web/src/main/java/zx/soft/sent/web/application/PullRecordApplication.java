package zx.soft.sent.web.application;

import java.util.ArrayList;
import java.util.List;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.domain.sentiment.RecordSelect;
import zx.soft.sent.dao.sentiment.CreateTables;
import zx.soft.sent.dao.sentiment.SentimentRecord;
import zx.soft.sent.web.resource.PullRecordResource;
import zx.soft.utils.checksum.CheckSumUtils;

/**
 * MySQL数据提取应用类
 * 
 * @author wanggang
 *
 */
public class PullRecordApplication extends Application {

	//	private static Logger logger = LoggerFactory.getLogger(PullRecordApplication.class);

	private final SentimentRecord sentRecord;

	public PullRecordApplication() {
		sentRecord = new SentimentRecord(MybatisConfig.ServerEnum.sentiment);
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// 1、根据多个id查询记录：
		router.attach("/pull/ids/{ids}", PullRecordResource.class);
		// 2、根据lasttime时间段查询记录：
		//		router.attach("/get", PullRecordResource.class);
		return router;
	}

	public List<RecordSelect> getRecords(String ids) {
		List<RecordSelect> result = new ArrayList<>();
		for (String id : ids.split(",")) {
			result.add(getRecord(id));
		}
		return result;
	}

	private RecordSelect getRecord(String id) {
		return sentRecord.selectRecordById(CreateTables.SENT_TABLE + CheckSumUtils.getCRC32(id)
				% CreateTables.MAX_TABLE_NUM, id);
	}

	public void close() {
		//
	}

}
