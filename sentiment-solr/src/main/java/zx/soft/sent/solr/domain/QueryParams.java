package zx.soft.sent.solr.domain;

import java.util.HashMap;

/**
 * 查询参数类
 * 
 * @author wanggang
 *
 */
public class QueryParams {

	private String q = "*:*";
	private String fq = "";
	private String sort = "";
	private int start = 0;
	private int rows = 10;
	private String fl = "";
	private String wt = "json";
	private String hlfl = "";
	private String hlsimple = "";
	private String facetQuery = "";
	private String facetField = "";
	// 增加按日期分类统计，主要有以下4个参数
	// facet.date，facet.date.start，facet.date.end，facet.date.gap
	// 默认，facet=true
	private HashMap<String, String> facetDate = new HashMap<>();

	public QueryParams() {
		//
	}

	@Override
	public String toString() {
		return "QueryParams:{q=" + q + ",fq=" + fq + ",sort=" + sort + ",start=" + start + ",rows=" + rows + ",fl="
				+ fl + ",wt=" + wt + ",hlfl=" + hlfl + ",hlsimple=" + hlsimple + ",facetQuery=" + facetQuery
				+ ",facetField=" + facetField + "}";
	}

	public void setQ(String q) {
		this.q = q;
	}

	public void setFq(String fq) {
		this.fq = fq;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setFl(String fl) {
		this.fl = fl;
	}

	public void setWt(String wt) {
		this.wt = wt;
	}

	public void setHlfl(String hlfl) {
		this.hlfl = hlfl;
	}

	public void setFacetQuery(String facetQuery) {
		this.facetQuery = facetQuery;
	}

	public void setFacetField(String facetField) {
		this.facetField = facetField;
	}

	public String getQ() {
		return q;
	}

	public String getFq() {
		return fq;
	}

	public String getSort() {
		return sort;
	}

	public int getStart() {
		return start;
	}

	public int getRows() {
		return rows;
	}

	public String getFl() {
		return fl;
	}

	public String getWt() {
		return wt;
	}

	public String getHlfl() {
		return hlfl;
	}

	public String getFacetQuery() {
		return facetQuery;
	}

	public String getFacetField() {
		return facetField;
	}

	public String getHlsimple() {
		return hlsimple;
	}

	public void setHlsimple(String hlsimple) {
		this.hlsimple = hlsimple;
	}

	public HashMap<String, String> getFacetDate() {
		return facetDate;
	}

	public void setFacetDate(HashMap<String, String> facetDate) {
		this.facetDate = facetDate;
	}

}
