package zx.soft.sent.web.demo;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class UserResource extends ServerResource {

	//	@Post
	//	public Representation acceptItem(Student entity) {
	//		Representation result = null;
	//		// Parse the given representation and retrieve data
	//
	//		System.out.println(entity.getName());
	//
	//		if (entity.getId() == 123) { // Assume that user id 123 is existed
	//			result = new StringRepresentation("User whose uid=" + entity.getId() + " is updated", MediaType.TEXT_PLAIN);
	//		} else { // otherwise add user
	//			result = new StringRepresentation("User " + entity.getName() + " is added", MediaType.TEXT_PLAIN);
	//		}
	//
	//		return result;
	//	}

	@Get("json")
	public Representation sendResponse() {
		String uid = (String) getRequest().getAttributes().get("uid");
		System.out.println(uid);
		//		String uid = (String) getRequestAttributes().get("uid");
		return new StringRepresentation("Information about user \"" + uid + "\" is: <nothing>");
		//		Student student = new Student();
		//		return new JacksonRepresentation<Student>(student);
	}

}
