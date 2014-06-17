package zx.soft.sent.web.jackson;

import java.util.List;

import org.restlet.data.MediaType;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;

/**
 * 修复后的Jackson转换器
 * 
 * @author wanggang
 *
 */
public class FixedJacksonConverter extends JacksonConverter {

	private static final VariantInfo VARIANT_JSON = new VariantInfo(MediaType.APPLICATION_JSON);

	@Override
	protected <T> JacksonRepresentation<T> create(MediaType mediaType, T source) {
		return new FixedJacksonRepresentation<T>(mediaType, source);
	}

	@Override
	protected <T> JacksonRepresentation<T> create(Representation source, Class<T> objectClass) {
		return new FixedJacksonRepresentation<T>(source, objectClass);
	}

	@Override
	public List<Class<?>> getObjectClasses(Variant source) {
		List<Class<?>> result = null;
		if (VARIANT_JSON.isCompatible(source)) {
			result = addObjectClass(result, Object.class);
			result = addObjectClass(result, FixedJacksonRepresentation.class);
		}
		return result;
	}

}
