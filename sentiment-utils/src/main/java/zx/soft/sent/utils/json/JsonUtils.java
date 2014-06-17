package zx.soft.sent.utils.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * JSON工具类
 * 
 * @author wanggang
 *
 */
public class JsonUtils {

	private static final ObjectMapper mapper = new ObjectMapper();

	//	private static DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
	private static DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

	static {
		mapper.setDateFormat(dateFormat);
	}

	public static ObjectMapper getObjectMapper() {
		return mapper;
	}

	public static String toJson(Object object) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toJsonWithoutPretty(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *  测试函数
	 */
	public static void main(String[] args) throws ParseException {

		//		System.out.println(dateFormat.format(new Date()));
		// Wed Oct 23 16:58:17 +0800 2013
		// Wed Oct 23 16:58:17 CST 2013
		Date parse = dateFormat.parse("Thu Apr 10 11:40:56 CST 2014");
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parse));
	}

}
