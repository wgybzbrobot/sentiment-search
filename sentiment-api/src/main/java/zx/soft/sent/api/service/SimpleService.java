package zx.soft.sent.api.service;

import org.springframework.stereotype.Service;

/**
 * 索引服务类
 *
 * @author wanggang
 *
 */
@Service
public class SimpleService {

	public String getName(long uid, String name) {
		return "Uid: " + uid + "'s name is " + name;
	}

}
