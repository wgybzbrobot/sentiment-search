package zx.soft.sent.lucene.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import zx.soft.sent.lucene.search.SearchModel;

public class ReturnModel implements Serializable {

	private static final long serialVersionUID = 1000555555L;

	/**
	 * 总记录数
	 */
	public int allcount = 0;

	/**
	 * 搜索对象结果集
	 */
	public List<SearchModel> modle = new ArrayList<SearchModel>();

	/**
	 * 分词结果集合
	 */
	public List<String> analyzerlist = new ArrayList<String>();

}
