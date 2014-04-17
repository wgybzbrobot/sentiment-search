package zx.soft.spider.web.demo;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import zx.soft.spider.solr.utils.JsonUtils;

public class ClientCall {

	public static void main(String[] args) {

		// Create the client resource
		ClientResource resource = new ClientResource("http://localhost:8182/users");

		Form form = new Form();
		form.add("uid", "123");
		form.add("uname", "John");
		System.out.println(JsonUtils.toJsonWithoutPretty(form));

		// Write the response entity on the console
		try {

			resource.post(form).write(System.out);

		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
