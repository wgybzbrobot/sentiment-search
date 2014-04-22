package zx.soft.sent.web.domain;

import java.util.List;

import zx.soft.sent.dao.domain.RecordInfo;

/**
 * POST的索引数据
 * @author wanggang
 *
 */
public class PostData {

	private int num;
	private List<RecordInfo> records;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<RecordInfo> getRecords() {
		return records;
	}

	public void setRecords(List<RecordInfo> records) {
		this.records = records;
	}

}
