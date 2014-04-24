package zx.soft.sent.web.jackson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;

public class ReplaceConvert {

	private static DateFormat sinaDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

	public static void configureJacksonConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(sinaDateFormat);
		objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
	}

	/**
	* Registers a new converter with the Restlet engine, after removing the first
	* registered converter of the given class.
	*
	* Taken from:
	* http://restlet.tigris.org/ds/viewMessage.do?dsForumId=4447&dsMessageId=2716118
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
