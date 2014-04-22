package zx.soft.sent.dao.domain;

import java.util.Date;

/**
 * 资讯类
 * @author wanggang
 *
 */
public class Information {

	/*
	Information与Record映射关系：

	url=ZXDZ
	title=ZXBT
	content=ZXLR
	original_name=WZLY
	video_url=SPURL
	pic_url=TPURL
	voice_url=YPURL
	timestamp=FBSJ
	nickname=FBYH
	source_id=LY
	lasttime=JCSJ
	server_id=LZ
	identify_id=BZ
	ip=IP
	update_time=GXSJ
	location=IPDZ
	comment_count=PLS
	repost_count=ZBS
	read_count=YDS

	 */

	private final String ZXDZ; //资讯地址URL
	private final String ZXBT; //资讯标题
	private final String ZXLR; //资讯内容
	private final String WZLY; //网站稿来源(凤凰网)
	private final String SPURL; //视频URL(http://xxx.xxx.xxx)
	private final String TPURL; //图片URL(http://xxx.xxx.xxx)
	private final String YPURL; //音频URL(http://xxx.xxx.xxx)
	private final Date FBSJ; //页面发布时间(2013-06-06 10:10:10)
	private final String FBYH; //发布用户(张三)
	private final int LY; //来源(来源网站名)(不配)
	private final Date JCSJ; //最新监测时间(不配)
	private final int LZ; //来自(来自前置机编号)
	private final int BZ; //标识(不配)
	private final String IP; //IP地址
	private final Date GXSJ; //最新更新时间(不配)
	private final String IPDZ; //IP地址
	private final int PLS; //评论数(0)
	private final int ZBS; //转播数(0)
	private final int YDS; //阅读数(0)

	@Override
	public String toString() {
		return "Information:{ZXDZ=" + ZXDZ + ",ZXBT=" + ZXBT + ",ZXLR=" + ZXLR + ",WZLY=" + WZLY + ",SPURL=" + SPURL
				+ ",TPURL=" + TPURL + ",YPURL=" + YPURL + ",FBSJ=" + FBSJ + ",FBYH=" + FBYH + ",LY=" + LY + ",JCSJ="
				+ JCSJ + ",LZ=" + LZ + ",BZ=" + BZ + ",IP=" + IP + ",GXSJ=" + GXSJ + ",IPDZ=" + IPDZ + ",PLS=" + PLS
				+ ",ZBS=" + ZBS + ",YDS=" + YDS + "}";
	}

	public Information(Builder builder) {
		this.ZXDZ = builder.ZXDZ;
		this.ZXBT = builder.ZXBT;
		this.ZXLR = builder.ZXLR;
		this.WZLY = builder.WZLY;
		this.SPURL = builder.SPURL;
		this.TPURL = builder.TPURL;
		this.YPURL = builder.YPURL;
		this.FBSJ = builder.FBSJ;
		this.FBYH = builder.FBYH;
		this.LY = builder.LY;
		this.JCSJ = builder.JCSJ;
		this.LZ = builder.LZ;
		this.BZ = builder.BZ;
		this.IP = builder.IP;
		this.GXSJ = builder.GXSJ;
		this.IPDZ = builder.IPDZ;
		this.PLS = builder.PLS;
		this.ZBS = builder.ZBS;
		this.YDS = builder.YDS;
	}

	public static class Builder {

		private String ZXDZ = ""; //资讯地址URL
		private String ZXBT = ""; //资讯标题
		private String ZXLR = ""; //资讯内容
		private String WZLY = ""; //网站稿来源(凤凰网)
		private String SPURL = ""; //视频URL(http://xxx.xxx.xxx)
		private String TPURL = ""; //图片URL(http://xxx.xxx.xxx)
		private String YPURL = ""; //音频URL(http://xxx.xxx.xxx)
		private Date FBSJ = null; //页面发布时间(2013-06-06 10:10:10)
		private String FBYH = ""; //发布用户(张三)
		private int LY; //来源(来源网站名)(不配)
		private Date JCSJ = null; //最新监测时间(不配)
		private int LZ; //来自(来自前置机编号)
		private int BZ; //标识(不配)
		private String IP = ""; //IP地址
		private Date GXSJ = null; //最新更新时间(不配)
		private String IPDZ = ""; //IP地址
		private int PLS; //评论数(0)
		private int ZBS; //转播数(0)
		private int YDS; //阅读数(0)

		public Builder(String zXDZ, String fBYH) {
			ZXDZ = zXDZ;
			FBYH = fBYH;
		}

		public Builder setZXDZ(String zXDZ) {
			ZXDZ = zXDZ;
			return this;
		}

		public Builder setZXBT(String zXBT) {
			ZXBT = zXBT;
			return this;
		}

		public Builder setZXLR(String zXLR) {
			ZXLR = zXLR;
			return this;
		}

		public Builder setWZLY(String wZLY) {
			WZLY = wZLY;
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

		public Builder setFBYH(String fBYH) {
			FBYH = fBYH;
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

		public Information build() {
			return new Information(this);
		}
	}

	public String getZXDZ() {
		return ZXDZ;
	}

	public String getZXBT() {
		return ZXBT;
	}

	public String getZXLR() {
		return ZXLR;
	}

	public String getWZLY() {
		return WZLY;
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

	public int getPLS() {
		return PLS;
	}

	public int getZBS() {
		return ZBS;
	}

	public int getYDS() {
		return YDS;
	}

}
