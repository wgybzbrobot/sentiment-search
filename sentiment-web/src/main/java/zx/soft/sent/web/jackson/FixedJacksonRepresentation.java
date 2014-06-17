package zx.soft.sent.web.jackson;

import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 修复后的Jackson表达类
 * 
 * @author wanggang
 *
 * @param <T>
 */
public class FixedJacksonRepresentation<T> extends JacksonRepresentation<T> {

	public FixedJacksonRepresentation(MediaType mediaType, T object) {
		super(mediaType, object);
	}

	public FixedJacksonRepresentation(T object) {
		super(object);
	}

	public FixedJacksonRepresentation(Representation representation, Class<T> objectClass) {
		super(representation, objectClass);
	}

	@Override
	protected ObjectMapper createObjectMapper() {

		ObjectMapper ret = super.createObjectMapper();

		//		 inject the mixin that will allow us to properly serialize Objectify Key objects...
		//		ret.getSerializationConfig().addMixInAnnotations(Key.class, JacksonMixIn.class);
		//		ret.getDeserializationConfig().addMixInAnnotations(Key.class, JacksonMixIn.class);

		return ret;

	}

}
