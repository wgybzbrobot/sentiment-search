package zx.soft.sent.web.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.Key;

/**
 * Jackson混合接口
 * 
 * @author wanggang
 *
 */
public interface JacksonMixIn {

	@JsonIgnore
	<V> Key<V> getRoot();

}
