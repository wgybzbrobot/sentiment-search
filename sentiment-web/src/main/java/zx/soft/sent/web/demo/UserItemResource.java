package zx.soft.sent.web.demo;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class UserItemResource extends ServerResource {
	@Override
	@Get
	public String toString() {
		String uid = (String) getRequestAttributes().get("uid");
		return "The items that user \"" + uid + "\" bought are: <nothing>";
	}
}