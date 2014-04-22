package zx.soft.sent.dao.domain;

/**
 * 舆情数据表
 * @author wanggang
 *
 */
public class SentTablename {

	private String tablename;
	private String name;
	private long lasttime;

	public SentTablename() {
		//
	}

	public SentTablename(String tablename, String name) {
		this.tablename = tablename;
		this.name = name;
	}

	public SentTablename(String tablename, String name, long lasttime) {
		this.tablename = tablename;
		this.name = name;
		this.lasttime = lasttime;
	}

	public long getLasttime() {
		return lasttime;
	}

	public void setLasttime(long lasttime) {
		this.lasttime = lasttime;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
