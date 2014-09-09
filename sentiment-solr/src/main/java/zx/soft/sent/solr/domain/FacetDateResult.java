package zx.soft.sent.solr.domain;

import java.util.HashMap;

/**
 * 按时间分类查询结果
 * 
 * @author wanggang
 *
 */
public class FacetDateResult {

	private String start;
	private String end;
	private String gap;
	private HashMap<String, Long> dateCounts = new HashMap<>();

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getGap() {
		return gap;
	}

	public void setGap(String gap) {
		this.gap = gap;
	}

	public HashMap<String, Long> getDateCounts() {
		return dateCounts;
	}

	public void setDateCounts(HashMap<String, Long> dateCounts) {
		this.dateCounts = dateCounts;
	}

}
