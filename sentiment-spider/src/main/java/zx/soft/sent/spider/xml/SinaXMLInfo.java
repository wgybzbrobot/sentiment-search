package zx.soft.sent.spider.xml;

public class SinaXMLInfo {

	// 微博ID
	private String MBLOG_ID;
	// 相关人ID
	private String RELEVANT_USERID;
	// 相关人昵称
	private String RELEVANT_USER_NICKNAME;
	// 微博消息内容
	private String WEIBO_MESSAGE;
	// 采集地
	private String COLLECT_PLACE;
	// 首次采集时间
	private long FIRST_TIME;
	// 最近采集时间
	private long LAST_TIME;
	// 数据来源名称
	private String DATA_SOURCE;
	// 域名
	private String DOMAIN;
	// 数据截获时间
	private long CAPTURE_TIME;

	public String getMBLOG_ID() {
		return MBLOG_ID;
	}

	public void setMBLOG_ID(String mBLOG_ID) {
		MBLOG_ID = mBLOG_ID;
	}

	public String getRELEVANT_USERID() {
		return RELEVANT_USERID;
	}

	public void setRELEVANT_USERID(String rELEVANT_USERID) {
		RELEVANT_USERID = rELEVANT_USERID;
	}

	public String getRELEVANT_USER_NICKNAME() {
		return RELEVANT_USER_NICKNAME;
	}

	public void setRELEVANT_USER_NICKNAME(String rELEVANT_USER_NICKNAME) {
		RELEVANT_USER_NICKNAME = rELEVANT_USER_NICKNAME;
	}

	public String getWEIBO_MESSAGE() {
		return WEIBO_MESSAGE;
	}

	public void setWEIBO_MESSAGE(String wEIBO_MESSAGE) {
		WEIBO_MESSAGE = wEIBO_MESSAGE;
	}

	public String getCOLLECT_PLACE() {
		return COLLECT_PLACE;
	}

	public void setCOLLECT_PLACE(String cOLLECT_PLACE) {
		COLLECT_PLACE = cOLLECT_PLACE;
	}

	public long getFIRST_TIME() {
		return FIRST_TIME;
	}

	public void setFIRST_TIME(long fIRST_TIME) {
		FIRST_TIME = fIRST_TIME;
	}

	public long getLAST_TIME() {
		return LAST_TIME;
	}

	public void setLAST_TIME(long lAST_TIME) {
		LAST_TIME = lAST_TIME;
	}

	public String getDATA_SOURCE() {
		return DATA_SOURCE;
	}

	public void setDATA_SOURCE(String dATA_SOURCE) {
		DATA_SOURCE = dATA_SOURCE;
	}

	public String getDOMAIN() {
		return DOMAIN;
	}

	public void setDOMAIN(String dOMAIN) {
		DOMAIN = dOMAIN;
	}

	public long getCAPTURE_TIME() {
		return CAPTURE_TIME;
	}

	public void setCAPTURE_TIME(long cAPTURE_TIME) {
		CAPTURE_TIME = cAPTURE_TIME;
	}

}
