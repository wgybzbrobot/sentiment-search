package zx.soft.sent.web.jackson;

import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.engine.Engine;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Restlet的{@link JacksonConverter}本地化扩展。
 * 使用{@link JacksonRepresentation}的一个扩展，通过独立的注入获取它的{@link ObjectMapper}。
 * Singleton（单例模式）首先被创建，否则不会被应用。
 * 
 * @author wanggang
 *
 */
@Singleton
public class LocalJacksonConverter extends JacksonConverter {

	@Override
	protected <T> JacksonRepresentation<T> create(MediaType mediaType, T source) {
		return new Rep<T>(mediaType, source);
	}

	@Override
	protected <T> JacksonRepresentation<T> create(Representation source, Class<T> objectClass) {
		return new Rep<T>(source, objectClass);
	}

	class Rep<T> extends JacksonRepresentation<T> {

		Rep(MediaType mediaType, T source) {
			super(mediaType, source);
			this.object = source;
			this.jsonRepresentation = null;
		}

		Rep(Representation source, Class<T> objectClass) {
			super(source, objectClass);
			this.object = null;
			this.jsonRepresentation = source;
		}

		/**
		 * Returns the wrapped object, deserializing the representation with Jackson
		 * if necessary.
		 *
		 * @return The wrapped object.
		 */
		@Override
		public T getObject() {
			T result = null;

			if (this.object != null) {
				result = this.object;
			} else if (this.jsonRepresentation != null) {
				try {
					result = objectMapper.readValue(this.jsonRepresentation.getStream(), getObjectClass());
				} catch (IOException e) {
					Context.getCurrentLogger().log(Level.WARNING, "Unable to parse the object with Jackson.", e);
				}
			}

			return result;
		}

		/**
		 * Sets the object to format.
		 *
		 * @param object The object to format.
		 */
		@Override
		public void setObject(T object) {
			super.setObject(object);
			this.object = object;
		}

		@Override
		public void write(Writer writer) throws IOException {
			if (jsonRepresentation != null) {
				jsonRepresentation.write(writer);
			} else if (object != null) {
				objectMapper.writerWithView(Object.class).writeValue(writer, object);
			}
		}

		/** The (parsed) object to format. */
		private T object;

		/** The JSON representation to parse. */
		private final Representation jsonRepresentation;
	}

	@Inject
	public LocalJacksonConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Inject
	void registerWithEngine() {
		Engine.getInstance().getRegisteredConverters().add(this);
		// XXX Not shown here: Remove default JacksonConverter, if present.
	}

	private final ObjectMapper objectMapper;

}
