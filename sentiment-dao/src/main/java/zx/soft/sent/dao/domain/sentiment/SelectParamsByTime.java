package zx.soft.sent.dao.domain.sentiment;

import java.util.Date;

/**
 * 根据时间查询参数类
 * 
 * @author wanggang
 *
 */
public class SelectParamsByTime {

	private String tablename;
	private Date low;
	private Date high;

	public SelectParamsByTime() {
		//
	}

	public SelectParamsByTime(String tablename, Date low, Date high) {
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

	public Date getLow() {
		return low;
	}

	public void setLow(Date low) {
		this.low = low;
	}

	public Date getHigh() {
		return high;
	}

	public void setHigh(Date high) {
		this.high = high;
	}

}
