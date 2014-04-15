package zx.soft.spider.web.demo;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RouterApplication extends Application {
	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
		// Create a router Restlet that routes each call to a new respective instance of resource.
		Router router = new Router(getContext());
		// Defines only three routes
		router.attach("/users", UserResource.class);
		router.attach("/users/{uid}", UserResource.class);
		router.attach("/users/{uid}/items", UserItemResource.class);
		return router;
	}
}
