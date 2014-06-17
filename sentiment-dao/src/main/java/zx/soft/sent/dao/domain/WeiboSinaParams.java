package zx.soft.sent.dao.domain;

/**
 * 新浪微博查询参数类
 * 
 * @author wanggang
 *
 */
public class WeiboSinaParams {

	private String tablename;
	private int low;
	private int high;

	public WeiboSinaParams() {
		//
	}

	public WeiboSinaParams(String tablename, int low, int high) {
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

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

}
