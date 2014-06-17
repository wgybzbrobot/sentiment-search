package zx.soft.sent.dao.domain;

/**
 * 查询缓存类
 * 
 * @author wanggang
 *
 */
public class SelectCacheQuery {

	private String query_id; // 查询ID
	private String query_url; // 查询URL
	private String query_result; // 查询结果

	public SelectCacheQuery() {
		//
	}

	public SelectCacheQuery(String query_id, String query_url, String query_result) {
		this.query_id = query_id;
		this.query_url = query_url;
		this.query_result = query_result;
	}

	public String getQuery_id() {
		return query_id;
	}

	public void setQuery_id(String query_id) {
		this.query_id = query_id;
	}

	public String getQuery_url() {
		return query_url;
	}

	public void setQuery_url(String query_url) {
		this.query_url = query_url;
	}

	public String getQuery_result() {
		return query_result;
	}

	public void setQuery_result(String query_result) {
		this.query_result = query_result;
	}

}
