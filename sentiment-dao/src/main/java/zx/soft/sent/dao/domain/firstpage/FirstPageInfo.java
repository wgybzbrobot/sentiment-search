package zx.soft.sent.dao.domain.firstpage;

/**
 * OA首页查询信息
 * 
 * @author wanggang
 *
 */
public class FirstPageInfo {

	private int type;// 首页展现类型，如：1-今日概况、2-饼状图、3-舆情聚焦、4-微博当天数据趋势、5-重点关注
	private String timestr; // 记录时间字符串，如：2014-09-03 12:23:01;
	private String result; // 查询结果

	public FirstPageInfo() {
		//		
	}

	public FirstPageInfo(int type, String timestr, String result) {
		this.type = type;
		this.timestr = timestr;
		this.result = result;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTimestr() {
		return timestr;
	}

	public void setTimestr(String timestr) {
		this.timestr = timestr;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
