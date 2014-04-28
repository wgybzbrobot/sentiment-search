package zx.soft.sent.control.tsdb;

import java.util.ArrayList;
import java.util.List;

import zx.soft.sent.cache.dao.Cache;
import zx.soft.sent.cache.tsdb.Reportable;
import zx.soft.sent.cache.tsdb.Tsdb;
import zx.soft.sent.control.constant.RecordsConstant;

/**
 * 统计Redis中的数据条数等实时数据。
 * @author wanggang
 *
 */
public class GatherQueueReport implements Reportable {

	private final Cache cache;

	public GatherQueueReport(final Cache cache) {
		this.cache = cache;
	}

	@Override
	public List<Tsdb> report() {
		List<Tsdb> result = new ArrayList<Tsdb>();
		long count;
		for (String key : RecordsConstant.REDIS_RECORDS_KEY) {
			count = cache.scard(key);
			result.add(new Tsdb("gather.spider." + key, count, "type", key + "-count"));
		}
		// 其他数据根据需求添加
		// ......
		// ......
		return result;
	}

}
