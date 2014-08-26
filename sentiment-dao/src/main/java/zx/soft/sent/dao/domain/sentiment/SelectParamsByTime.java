package zx.soft.sent.dao.domain.sentiment;

/**
 * 根据时间查询参数类
 * 
 * @author wanggang
 *
 */
public class SelectParamsByTime {

	private String tablename;
	private long low;
	private long high;

	public SelectParamsByTime() {
		//
	}

	public SelectParamsByTime(String tablename, long low, long high) {
		this.tablename = tablename;
		this.low = low;
		this.high = high;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public long getLow() {
		return low;
	}

	public void setLow(long low) {
		this.low = low;
	}

	public long getHigh() {
		return high;
	}

	public void setHigh(long high) {
		this.high = high;
	}

}
