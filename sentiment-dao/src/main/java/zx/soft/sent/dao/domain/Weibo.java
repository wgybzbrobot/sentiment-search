package zx.soft.sent.dao.domain;

import java.util.Date;

/**
 * 微薄类
 * @author wanggang
 *
 */
public class Weibo {

	/*
	 Weibo与Record映射关系：

	url=WBDZ
	content=WBLR
	video_url=SPURL
	pic_url=TPURL
	voice_url=YPURL
	timestamp=FBSJ
	nickname=FBYH
	comment_count=PLS
	repost_count=ZBS
	read_count=YDS
	keyword=GJC
	source_id=LY
	lasttime=JCSJ
	server_id=LZ
	identify_id=BZ
	ip=IP
	update_time=GXSJ
	location=IPDZ

	 */

	private final String WBDZ; //微博地址URL
	private final String WBLR; //微博内容
	private final String SPURL; //视频URL(http://xxx.xxx.xxx)
	private final String TPURL; //图片URL(http://xxx.xxx.xxx)
	private final String YPURL; //音频URL(http://xxx.xxx.xxx)
	private final Date FBSJ; //页面发布时间(2013-06-06 10:10:10)
	private final String FBYH; //发布用户(张三)
	private final int PLS; //评论数(0)
	private final int ZBS; //转播数(0)
	private final int YDS; //阅读数(0)
	private final String GJC; //关键词(张学友)
	private final int LY; //来源(来源网站名)(不配)
	private final Date JCSJ; //最新监测时间(不配)
	private final int LZ; //来自(来自前置机编号)(不配)
	private final int BZ; //标识(不配)
	private final String IP; //IP地址
	private final Date GXSJ; //最新更新时间(不配)
	private final String IPDZ; //IP地址

	@Override
	public String toString() {
		return "Weibo:{WBDZ=" + WBDZ + ",WBLR=" + WBLR + ",SPURL=" + SPURL + ",TPURL=" + TPURL + ",YPURL=" + YPURL
				+ ",FBSJ=" + FBSJ + ",FBYH=" + FBYH + ",PLS=" + PLS + ",ZBS=" + ZBS + ",YDS=" + YDS + ",GJC=" + GJC
				+ ",LY=" + LY + ",JCSJ=" + JCSJ + ",LZ=" + LZ + ",BZ=" + BZ + ",IP=" + IP + ",GXSJ=" + GXSJ + ",IPDZ="
				+ IPDZ + "}";
	}

	public Weibo(Builder builder) {
		this.WBDZ = builder.WBDZ;
		this.WBLR = builder.WBLR;
		this.SPURL = builder.SPURL;
		this.TPURL = builder.TPURL;
		this.YPURL = builder.YPURL;
		this.FBSJ = builder.FBSJ;
		this.FBYH = builder.FBYH;
		this.PLS = builder.PLS;
		this.ZBS = builder.ZBS;
		this.YDS = builder.YDS;
		this.GJC = builder.GJC;
		this.LY = builder.LY;
		this.JCSJ = builder.JCSJ;
		this.LZ = builder.LZ;
		this.BZ = builder.BZ;
		this.IP = builder.IP;
		this.GXSJ = builder.GXSJ;
		this.IPDZ = builder.IPDZ;
	}

	public static class Builder {

		private String WBDZ; //微博地址URL
		private String WBLR; //微博内容
		private String SPURL; //视频URL(http://xxx.xxx.xxx)
		private String TPURL; //图片URL(http://xxx.xxx.xxx)
		private String YPURL; //音频URL(http://xxx.xxx.xxx)
		private Date FBSJ = null; //页面发布时间(2013-06-06 10:10:10)
		private final String FBYH; //发布用户(张三)
		private int PLS; //评论数(0)
		private int ZBS; //转播数(0)
		private int YDS; //阅读数(0)
		private String GJC; //关键词(张学友)
		private int LY; //来源(来源网站名)(不配)
		private Date JCSJ = null; //最新监测时间(不配)
		private int LZ; //来自(来自前置机编号)(不配)
		private int BZ; //标识(不配)
		private String IP; //IP地址
		private Date GXSJ = null; //最新更新时间(不配)
		private String IPDZ; //IP地址

		public Builder(String wBDZ, String fBYH) {
			WBDZ = wBDZ;
			FBYH = fBYH;
		}

		public Builder setWBDZ(String wBDZ) {
			WBDZ = wBDZ;
			return this;
		}

		public Builder setWBLR(String wBLR) {
			WBLR = wBLR;
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

		public Builder setPLS(int pLS) {
			PLS = pLS;
			return this;
		}

		public Builder setZBS(int zBS) {
			ZBS = zBS;
			return this;
		}

		public Builder setYDS(int yDS) {
			YDS = yDS;
			return this;
		}

		public Builder setGJC(String gJC) {
			GJC = gJC;
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

		public Builder setGXSJ(Date gXSJ) {
			GXSJ = gXSJ;
			return this;
		}

		public Builder setIPDZ(String iPDZ) {
			IPDZ = iPDZ;
			return this;
		}

		public Weibo build() {
			return new Weibo(this);
		}

	}

	public String getWBDZ() {
		return WBDZ;
	}

	public String getWBLR() {
		return WBLR;
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

	public String getFBYH() {
		return FBYH;
	}

	public int getPLS() {
		return PLS;
	}

	public int getZBS() {
		return ZBS;
	}

	public int getYDS() {
		return YDS;
	}

	public String getGJC() {
		return GJC;
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

	public Date getGXSJ() {
		return GXSJ;
	}

	public String getIPDZ() {
		return IPDZ;
	}

}
