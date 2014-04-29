package zx.soft.sent.web.application;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.web.resource.SentiSearchResource;

@Deprecated
// 未完待续
public class RetriveRecordApplication extends Application {

	public RetriveRecordApplication() {
		//
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// 1、根据单个id查询记录，2、根据多个id查询记录：
		router.attach("/ids/{ids}", SentiSearchResource.class);
		// 3、根据lasttime时间段查询记录：
		router.attach("/get", SentiSearchResource.class);
		return router;
	}

	public void close() {
		//
	}

}
