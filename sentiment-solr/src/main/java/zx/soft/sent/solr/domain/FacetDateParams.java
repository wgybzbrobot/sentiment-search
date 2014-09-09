package zx.soft.sent.solr.domain;

/**
 * 按日期分类查询参数
 * 
 * @author wanggang
 *
 */
public class FacetDateParams {

	private String q = "*:*";
	private String facetDate;
	private String facetDateStart;
	private String facetDateEnd;
	private String facetDateGap = "%2B1DAY"; // 默认是一天

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getFacetDate() {
		return facetDate;
	}

	public void setFacetDate(String facetDate) {
		this.facetDate = facetDate;
	}

	public String getFacetDateStart() {
		return facetDateStart;
	}

	public void setFacetDateStart(String facetDateStart) {
		this.facetDateStart = facetDateStart;
	}

	public String getFacetDateEnd() {
		return facetDateEnd;
	}

	public void setFacetDateEnd(String facetDateEnd) {
		this.facetDateEnd = facetDateEnd;
	}

	public String getFacetDateGap() {
		return facetDateGap;
	}

	public void setFacetDateGap(String facetDateGap) {
		this.facetDateGap = facetDateGap;
	}

}
