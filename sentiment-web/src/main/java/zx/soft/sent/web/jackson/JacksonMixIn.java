package zx.soft.sent.web.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Key;

public interface JacksonMixIn {

	@JsonIgnore
	<V> Key<V> getRoot();
}
