package zx.soft.spider.web.sentiment;


public class QueryParams {

	private String q = "";
	private String fq = "";
	private String sort = "";
	private int start = -1;
	private int rows = -1;
	private String fl = "";
	private String wt = "";
	private String hlfl = "";
	private String facetQuery = "";
	private String facetField = "";

	public QueryParams() {
		//
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

}
