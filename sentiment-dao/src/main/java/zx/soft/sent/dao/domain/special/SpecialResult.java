package zx.soft.sent.dao.domain.special;

/**
 * 插入专题的查询结果数据
 * 
 * @author wanggang
 *
 */
public class SpecialResult {

	private String identify; // 唯一标识
	private String result; // 查询结果

	public SpecialResult() {
		//
	}

	public SpecialResult(String identify, String result) {
		this.identify = identify;
		this.result = result;
	}

	@Override
	public String toString() {
		return "SpecialResult:[identify=" + identify + ",result=" + result + "]";
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
