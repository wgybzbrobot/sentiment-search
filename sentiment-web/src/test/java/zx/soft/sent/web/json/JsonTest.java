package zx.soft.sent.web.json;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public class JsonTest {

	private ObjectMapper om;

	@Before
	public void initOM() {
		om = new ObjectMapper();
		om.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
	}

	@Test
	public void test1() throws java.io.IOException {
		om.readTree("/* (C) 2099 Yoyodyne Inc. */\n{ \"foo\": \"barÂ©\" }\n".getBytes("UTF-8"));
	}

	@Test
	public void test2() throws java.io.IOException {
		om.readTree("/* Â© 2099 Yoyodyne Inc. */\n{ \"foo\": \"barÂ©\" }\n".getBytes("UTF-8"));
	}

}
