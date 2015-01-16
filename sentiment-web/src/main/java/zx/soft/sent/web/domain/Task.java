package zx.soft.sent.web.domain;

import java.io.Serializable;

public class Task implements Serializable {

	private static final long serialVersionUID = -1391283603740904033L;

	// 关键词
	private String keywords;
	// 过滤条件
	private String fq;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getFq() {
		return fq;
	}

	public void setFq(String fq) {
		this.fq = fq;
	}

}
