package zx.soft.sent.web.application;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.sent.web.resource.SpecialResource;

/**
 * 专题模块应用类
 * 
 * @author wanggang
 *
 */
public class SpecialApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/special", SpecialResource.class);
		return router;
	}

}
