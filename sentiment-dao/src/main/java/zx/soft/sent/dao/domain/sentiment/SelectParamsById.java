package zx.soft.sent.dao.domain.sentiment;

/**
 * 根据ID查询类
 * 
 * @author wanggang
 *
 */
public class SelectParamsById {

	private String tablename;
	private String id;

	public SelectParamsById() {
		//
	}

	public SelectParamsById(String tablename, String id) {
		this.tablename = tablename;
		this.id = id;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
