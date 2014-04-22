package zx.soft.sent.dao.domain;

import java.util.Date;


/**
 * 博客类
 * @author wanggang
 *
 */
public class Blog {

	/*
	Blog与Record映射关系：

	url=BKDZ
	title=BKBT
	content=BKLR
	mid=BKID
	original_name=YHM
	nickname=FBYH
	comment_count=PLS
	read_count=YDS
	favorite_count=SCS
	attitude_count=XHS
	repost_count=ZZS
	video_url=SPURL
	pic_url=TPURL
	voice_url=YPURL
	timestamp=FBSJ
	source_id=LY
	lasttime=JCSJ
	server_id=LZ
	identify_id=BZ
	ip=IP
	keyword=GJC
	update_time=GXSJ
	location=IPDZ

	 */

	private final String BKDZ; // 博客地址
	private final String BKBT; // 博客标题
	private final String BKLR; // 博客内容
	private final String BKID; // 博客ID
	private final String YHM; // 博客用户名(微博主页名称)
	private final String FBYH; // 发布用户(头像下面的名称)
	private final int PLS; // 评论数量(0)
	private final int YDS; // 阅读数量(0)
	private final int SCS; // 收藏数量(0)
	private final int XHS; // 喜欢数量(0)
	private final int ZZS; // 转载数量(0)
	private final String SPURL; // 视频URL(http://xxx.xxx.xxx)
	private final String TPURL; // 图片URL(http://xxx.xxx.xxx)
	private final String YPURL; // 音频URL(http://xxx.xxx.xxx)
	private final Date FBSJ; // 页面发布时间(2013-06-06 10:10:10)
	private final int LY; // 来源(来源网站名)(不配)
	private final Date JCSJ; // 最新监测时间(不配)
	private final int LZ; // 来自(来自前置机编号)
	private final int BZ; // 标识(不配)
	private final String IP; // IP地址
	private final String GJC; // 关键词
	private final Date GXSJ; // 最新更新时间(不配)
	private final String IPDZ; // IP地址

	@Override
	public String toString() {
		return "Blog:{BKDZ=" + BKDZ + ",BKBT=" + BKBT + ",BKLR=" + BKLR + ",BKID=" + BKID + ",YHM=" + YHM + ",FBYH="
				+ FBYH + ",PLS=" + PLS + ",YDS=" + YDS + ",SCS=" + SCS + ",XHS=" + XHS + ",ZZS=" + ZZS + ",SPURL="
				+ SPURL + ",TPURL=" + TPURL + ",YPURL=" + YPURL + ",FBSJ=" + FBSJ + ",LY=" + LY + ",JCSJ=" + JCSJ
				+ ",LZ=" + LZ + ",BZ=" + BZ + ",IP=" + IP + ",GJC=" + GJC + ",GXSJ=" + GXSJ + ",IPDZ=" + IPDZ + "}";
	}

	public Blog(Builder builder) {
		this.BKDZ = builder.BKDZ;
		this.BKBT = builder.BKBT;
		this.BKLR = builder.BKLR;
		this.BKID = builder.BKID;
		this.YHM = builder.YHM;
		this.FBYH = builder.FBYH;
		this.PLS = builder.PLS;
		this.YDS = builder.YDS;
		this.SCS = builder.SCS;
		this.XHS = builder.XHS;
		this.ZZS = builder.ZZS;
		this.SPURL = builder.SPURL;
		this.TPURL = builder.TPURL;
		this.YPURL = builder.YPURL;
		this.FBSJ = builder.FBSJ;
		this.LY = builder.LY;
		this.JCSJ = builder.JCSJ;
		this.LZ = builder.LZ;
		this.BZ = builder.BZ;
		this.IP = builder.IP;
		this.GJC = builder.GJC;
		this.GXSJ = builder.GXSJ;
		this.IPDZ = builder.IPDZ;
	}

	public static class Builder {

		private String BKDZ = ""; // 博客地址
		private String BKBT = ""; // 博客标题
		private String BKLR = ""; // 博客内容
		private final String BKID; // 博客ID
		private String YHM = ""; // 博客用户名(微博主页名称)
		private String FBYH = ""; // 发布用户(头像下面的名称)
		private int PLS; // 评论数量(0)
		private int YDS; // 阅读数量(0)
		private int SCS; // 收藏数量(0)
		private int XHS; // 喜欢数量(0)
		private int ZZS; // 转载数量(0)
		private String SPURL = ""; // 视频URL(http://xxx.xxx.xxx)
		private String TPURL = ""; // 图片URL(http://xxx.xxx.xxx)
		private String YPURL = ""; // 音频URL(http://xxx.xxx.xxx)
		private Date FBSJ = null; // 页面发布时间(2013-06-06 10:10:10)
		private int LY; // 来源(来源网站名)(不配)
		private Date JCSJ = null; // 最新监测时间(不配)
		private int LZ; // 来自(来自前置机编号)
		private int BZ; // 标识(不配)
		private String IP = ""; // IP地址
		private String GJC = ""; // 关键词
		private Date GXSJ = null; // 最新更新时间(不配)
		private String IPDZ = ""; // IP地址

		public Builder(String BKID, String BKDZ) {
			this.BKID = BKID;
			this.BKDZ = BKDZ;
		}

		public Builder setBKBT(String bKBT) {
			BKBT = bKBT;
			return this;
		}

		public Builder setBKLR(String bKLR) {
			BKLR = bKLR;
			return this;
		}

		public Builder setYHM(String yHM) {
			YHM = yHM;
			return this;
		}

		public Builder setFBYH(String fBYH) {
			FBYH = fBYH;
			return this;
		}

		public Builder setPLS(int pLS) {
			PLS = pLS;
			return this;
		}

		public Builder setYDS(int yDS) {
			YDS = yDS;
			return this;
		}

		public Builder setSCS(int sCS) {
			SCS = sCS;
			return this;
		}

		public Builder setXHS(int xHS) {
			XHS = xHS;
			return this;
		}

		public Builder setZZS(int zZS) {
			ZZS = zZS;
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

		public Builder setGJC(String gJC) {
			GJC = gJC;
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

		public Blog build() {
			return new Blog(this);
		}

	}

	public String getBKDZ() {
		return BKDZ;
	}

	public String getBKBT() {
		return BKBT;
	}

	public String getBKLR() {
		return BKLR;
	}

	public String getBKID() {
		return BKID;
	}

	public String getYHM() {
		return YHM;
	}

	public String getFBYH() {
		return FBYH;
	}

	public int getPLS() {
		return PLS;
	}

	public int getYDS() {
		return YDS;
	}

	public int getSCS() {
		return SCS;
	}

	public int getXHS() {
		return XHS;
	}

	public int getZZS() {
		return ZZS;
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

	public String getGJC() {
		return GJC;
	}

	public Date getGXSJ() {
		return GXSJ;
	}

	public String getIPDZ() {
		return IPDZ;
	}

}
