package zx.soft.sent.web.jackson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.engine.converter.DefaultConverter;
import org.restlet.ext.jackson.JacksonConverter;

/**
 * 替代转换工具
 * 
 * @author wanggang
 *
 */
public class ReplaceConvert {

	private static DateFormat sinaDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

	public static void configureJacksonConverter() {
		replaceConverter(DefaultConverter.class, new JacksonConverter());
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(sinaDateFormat);
		objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
	}

	static JacksonConverter getRegisteredJacksonConverter() {
		List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
		for (ConverterHelper converterHelper : converters) {
			if (converterHelper instanceof JacksonConverter) {
				return (JacksonConverter) converterHelper;
			}
		}
		throw new RuntimeException("Can not find JacksonCOnverter.");
	}

	/**
	 * 移除给定类的第一个注册的converter后，使用Restlet引擎注册一个converter类
	 * 参考:http://restlet.tigris.org/ds/viewMessage.do?dsForumId=4447&dsMessageId=2716118
	 */
	static void replaceConverter(Class<? extends ConverterHelper> converterClass, ConverterHelper newConverter) {

		ConverterHelper oldConverter = null;
		List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
		for (ConverterHelper converter : converters) {
			if (converter.getClass().equals(converterClass)) {
				converters.remove(converter);
				oldConverter = converter;
				break;
			}
		}

		converters.add(newConverter);
		if (oldConverter == null) {
			System.err.println("Added Converter to Restlet Engine: " + newConverter.getClass().getName());
		} else {
			System.err.println("Replaced Converter " + oldConverter.getClass().getName() + " with "
					+ newConverter.getClass().getName() + " in Restlet Engine");
		}
	}

}
