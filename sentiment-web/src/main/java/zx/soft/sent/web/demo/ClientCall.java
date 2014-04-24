package zx.soft.sent.web.demo;

import java.io.IOException;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import zx.soft.sent.solr.utils.JsonUtils;

public class ClientCall {

	public static void main(String[] args) {

		// Create the client resource
		ClientResource resource = new ClientResource("http://localhost:8182/users");

		//		Form form = new Form();
		//		form.add("uid", "123");
		//		form.add("uname", "John");
		Student s = new Student();
		s.setId(1234555);
		s.setName("搜索速度飞快辜负了光谱感光评估苹果派随即宋江的方法光谱感光苹果派...,。。，，。77348595/");
		System.out.println(JsonUtils.toJsonWithoutPretty(s));

		// Write the response entity on the console
		try {

			resource.post(s).write(System.out);

		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
