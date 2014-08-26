package zx.soft.sent.web.application;

import java.util.List;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.core.persist.PersistCore;
import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.sent.web.resource.PushRecordResource;

/**
 * MySQL数据存储应用类
 * 
 * @author wanggang
 *
 */
public class PushRecordApplication extends Application {

	//	private static Logger logger = LoggerFactory.getLogger(PushRecordApplication.class);

	private final PersistCore persistCore;

	public PushRecordApplication() {
		persistCore = new PersistCore();
	}

	@Override
	public synchronized Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/push", PushRecordResource.class);
		return router;
	}

	public void pushRecords(List<RecordInfo> records) {
		for (RecordInfo record : records) {
			pushRecord(record);
		}
	}

	private void pushRecord(RecordInfo record) {
		persistCore.persist(record);
	}

	public void close() {
		persistCore.close();
	}

}
