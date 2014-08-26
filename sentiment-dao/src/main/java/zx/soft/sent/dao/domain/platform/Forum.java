package zx.soft.sent.dao.domain.platform;

import java.util.Date;

/**
 * 论坛类
 * 
 * @author wanggang
 *
 */
public class Forum {

	/*
	 Forum与Record映射关系：

	first_time=FXSJ
	update_time=GXSJ
	source_id=LY
	lasttime=JCSJ
	server_id=LZ
	identify_id=BZ
	ip=IP
	location=IPDZ
	url=WYDZ
	type=BKMC
	title=WYBT
	content=WYLR
	video_url=SPURL
	pic_url=TPURL
	voice_url=YPURL
	timestamp=FBSJ
	read_count=LLL
	comment_count=GTL
	nickname=FBYH
	keyword=GJC

	 */

	private final Date FXSJ; // 首次发现时间(不配)
	private final Date GXSJ; //最新更新时间(不配)
	private final int LY; //来源(来源网站名)(不配)
	private final Date JCSJ; //最新监测时间(不配)
	private final int LZ; //来自(来自前置机编号)(不配)
	private final int BZ; //标识(不配)
	private final String IP; //IP地址
	private final String IPDZ; //IP地址
	private final String WYDZ; //论坛地址URL
	private final String BKMC; //板块名称(都市生活)
	private final String WYBT; //论坛标题
	private final String WYLR; //论坛内容
	private final String SPURL; //视频URL(http://xxx.xxx.xxx)
	private final String TPURL; //图片URL(http://xxx.xxx.xxx)
	private final String YPURL; //音频URL(http://xxx.xxx.xxx)
	private final Date FBSJ; //页面发布时间(2013-06-06 10:10:10)
	private final int LLL; //浏览量(0)
	private final int GTL; //跟帖量(0)
	private final String FBYH; //发布用户(张三)
	private final String GJC; //关键词(张学友)

	@Override
	public String toString() {
		return "Forum:{FXSJ=" + FXSJ + ",GXSJ=" + GXSJ + ",LY=" + LY + ",JCSJ=" + JCSJ + ",LZ=" + LZ + ",BZ=" + BZ
				+ ",IP=" + IP + ",IPDZ=" + IPDZ + ",WYDZ=" + WYDZ + ",BKMC=" + BKMC + ",WYBT=" + WYBT + ",WYLR=" + WYLR
				+ ",SPURL=" + SPURL + ",TPURL=" + TPURL + ",YPURL=" + YPURL + ",FBSJ=" + FBSJ + ",LLL=" + LLL + ",GTL="
				+ GTL + ",FBYH=" + FBYH + ",GJC=" + GJC + "}";
	}

	public Forum(Builder builder) {
		this.FXSJ = builder.FXSJ;
		this.GXSJ = builder.GXSJ;
		this.LY = builder.LY;
		this.JCSJ = builder.JCSJ;
		this.LZ = builder.LZ;
		this.BZ = builder.BZ;
		this.IP = builder.IP;
		this.IPDZ = builder.IPDZ;
		this.WYDZ = builder.WYDZ;
		this.BKMC = builder.BKMC;
		this.WYBT = builder.WYBT;
		this.WYLR = builder.WYLR;
		this.SPURL = builder.SPURL;
		this.TPURL = builder.TPURL;
		this.YPURL = builder.YPURL;
		this.FBSJ = builder.FBSJ;
		this.LLL = builder.LLL;
		this.GTL = builder.GTL;
		this.FBYH = builder.FBYH;
		this.GJC = builder.GJC;
	}

	public static class Builder {

		private Date FXSJ = null; // 首次发现时间(不配)
		private Date GXSJ = null; //最新更新时间(不配)
		private int LY; //来源(来源网站名)(不配)
		private Date JCSJ = null; //最新监测时间(不配)
		private int LZ; //来自(来自前置机编号)(不配)
		private int BZ; //标识(不配)
		private String IP = ""; //IP地址
		private String IPDZ = ""; //IP地址
		private String WYDZ = ""; //论坛地址URL
		private String BKMC = ""; //板块名称(都市生活)
		private String WYBT = ""; //论坛标题
		private String WYLR = ""; //论坛内容
		private String SPURL = ""; //视频URL(http://xxx.xxx.xxx)
		private String TPURL = ""; //图片URL(http://xxx.xxx.xxx)
		private String YPURL = ""; //音频URL(http://xxx.xxx.xxx)
		private Date FBSJ = null; //页面发布时间(2013-06-06 10:10:10)
		private int LLL; //浏览量(0)
		private int GTL; //跟帖量(0)
		private String FBYH = ""; //发布用户(张三)
		private String GJC = ""; //关键词(张学友)

		public Builder(String WYDZ, String FBYH) {
			this.WYDZ = WYDZ;
			this.FBYH = FBYH;
		}

		public Builder setFXSJ(Date fXSJ) {
			FXSJ = fXSJ;
			return this;
		}

		public Builder setGXSJ(Date gXSJ) {
			GXSJ = gXSJ;
			return this;
		}

		public Builder setLY(int lY) {
			LY = lY;
			return this;
		}

		public Builder setJCSJ(Date jCSJ) {
			JCSJ = jCSJ;
			return this;
		}

		public Builder setLZ(int lZ) {
			LZ = lZ;
			return this;
		}

		public Builder setBZ(int bZ) {
			BZ = bZ;
			return this;
		}

		public Builder setIP(String iP) {
			IP = iP;
			return this;
		}

		public Builder setIPDZ(String iPDZ) {
			IPDZ = iPDZ;
			return this;
		}

		public Builder setBKMC(String bKMC) {
			BKMC = bKMC;
			return this;
		}

		public Builder setWYBT(String wYBT) {
			WYBT = wYBT;
			return this;
		}

		public Builder setWYLR(String wYLR) {
			WYLR = wYLR;
			return this;
		}

		public Builder setSPURL(String sPURL) {
			SPURL = sPURL;
			return this;
		}

		public Builder setTPURL(String tPURL) {
			TPURL = tPURL;
			return this;
		}

		public Builder setYPURL(String yPURL) {
			YPURL = yPURL;
			return this;
		}

		public Builder setFBSJ(Date fBSJ) {
			FBSJ = fBSJ;
			return this;
		}

		public Builder setLLL(int lLL) {
			LLL = lLL;
			return this;
		}

		public Builder setGTL(int gTL) {
			GTL = gTL;
			return this;
		}

		public Builder setGJC(String gJC) {
			GJC = gJC;
			return this;
		}

		public Forum build() {
			return new Forum(this);
		}

	}

	public Date getFXSJ() {
		return FXSJ;
	}

	public Date getGXSJ() {
		return GXSJ;
	}

	public int getLY() {
		return LY;
	}

	public Date getJCSJ() {
		return JCSJ;
	}

	public int getLZ() {
		return LZ;
	}

	public int getBZ() {
		return BZ;
	}

	public String getIP() {
		return IP;
	}

	public String getIPDZ() {
		return IPDZ;
	}

	public String getWYDZ() {
		return WYDZ;
	}

	public String getBKMC() {
		return BKMC;
	}

	public String getWYBT() {
		return WYBT;
	}

	public String getWYLR() {
		return WYLR;
	}

	public String getSPURL() {
		return SPURL;
	}

	public String getTPURL() {
		return TPURL;
	}

	public String getYPURL() {
		return YPURL;
	}

	public Date getFBSJ() {
		return FBSJ;
	}

	public int getLLL() {
		return LLL;
	}

	public int getGTL() {
		return GTL;
	}

	public String getFBYH() {
		return FBYH;
	}

	public String getGJC() {
		return GJC;
	}

}
