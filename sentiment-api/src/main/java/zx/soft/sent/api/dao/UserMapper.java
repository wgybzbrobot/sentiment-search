package zx.soft.sent.api.dao;

import org.apache.ibatis.annotations.Update;

public interface UserMapper {

	@Update("update user set update_time = now(), is_member = 0 " //
			+ " where uid = #{0} and mid = #{1}")
	void exitMember(long uid, long mid);

}
