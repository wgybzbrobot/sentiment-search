package zx.soft.sent.solr.demo;

import org.apache.solr.client.solrj.beans.Field;

public class Item {

	@Field
	String id;

	@Field
	String name;

	@Field("cat")
	String[] categories;

}
