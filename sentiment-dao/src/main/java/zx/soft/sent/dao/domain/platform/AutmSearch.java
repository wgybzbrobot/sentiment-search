package zx.soft.sent.dao.domain.platform;

import java.util.Date;

/**
 * 元搜索类
 * 
 * @author wanggang
 *
 */
public class AutmSearch {

	/*
	AutmSearch与Record映射关系：

	url=SSDZ
	title=SSBT
	content=SSNR
	video_url=SPURL
	pic_url=TPURL
	voice_url=YPURL
	timestamp=FBSJ
	nickname=FBYH
	keyword=GJC
	source_id=LY
	ip=IP
	lasttime=JCSJ
	server_id=LZ
	identify_id=BZ
	update_time=GXSJ
	location=IPDZ

	 */

	private final String SSDZ; //搜索地址URL
	private final String SSBT; //搜索标题
	private final String SSNR; //搜索内容
	private final String SPURL; //视频URL(http://xxx.xxx.xxx)
	private final String TPURL; //图片URL(http://xxx.xxx.xxx)
	private final String YPURL; //音频URL(http://xxx.xxx.xxx)
	private final Date FBSJ; //页面发布时间(2013-06-06 10:10:10)
	private final String FBYH; //发布用户(张三)
	private final String GJC; //关键词(张学友)
	private final int LY; //来源(来源网站名)(不配)
	private final String IP; //IP地址
	private final Date JCSJ; //最新监测时间(不配)
	private final int LZ; //来自(来自前置机编号)(不配)
	private final int BZ; //标识(不配)
	private final Date GXSJ; //最新更新时间(不配)
	private final String IPDZ; //IP地址

	@Override
	public String toString() {
		return "AutmSearch:{SSDZ=" + SSDZ + ",SSBT=" + SSBT + ",SSNR=" + SSNR + ",SPURL=" + SPURL + ",TPURL=" + TPURL
				+ ",YPURL=" + YPURL + ",FBSJ=" + FBSJ + ",FBYH=" + FBYH + ",GJC=" + GJC + ",LY=" + LY + ",IP=" + IP
				+ ",JCSJ=" + JCSJ + ",LZ=" + LZ + ",BZ=" + BZ + ",GXSJ=" + GXSJ + ",IPDZ=" + IPDZ + "}";
	}

	public AutmSearch(Builder builder) {
		this.SSDZ = builder.SSDZ;
		this.SSBT = builder.SSBT;
		this.SSNR = builder.SSNR;
		this.SPURL = builder.SPURL;
		this.TPURL = builder.TPURL;
		this.YPURL = builder.YPURL;
		this.FBSJ = builder.FBSJ;
		this.FBYH = builder.FBYH;
		this.GJC = builder.GJC;
		this.LY = builder.LY;
		this.IP = builder.IP;
		this.JCSJ = builder.JCSJ;
		this.LZ = builder.LZ;
		this.BZ = builder.BZ;
		this.GXSJ = builder.GXSJ;
		this.IPDZ = builder.IPDZ;
	}

	public static class Builder {

		private String SSDZ = ""; //搜索地址URL
		private String SSBT = ""; //搜索标题
		private String SSNR = ""; //搜索内容
		private String SPURL = ""; //视频URL(http://xxx.xxx.xxx)
		private String TPURL = ""; //图片URL(http://xxx.xxx.xxx)
		private String YPURL = ""; //音频URL(http://xxx.xxx.xxx)
		private Date FBSJ = null; //页面发布时间(2013-06-06 10:10:10)
		private String FBYH = ""; //发布用户(张三)
		private String GJC = ""; //关键词(张学友)
		private int LY; //来源(来源网站名)(不配)
		private String IP = ""; //IP地址
		private Date JCSJ = null; //最新监测时间(不配)
		private int LZ; //来自(来自前置机编号)(不配)
		private int BZ; //标识(不配)
		private Date GXSJ = null; //最新更新时间(不配)
		private String IPDZ = ""; //IP地址

		public Builder(String sSDZ, String fBYH) {
			SSDZ = sSDZ;
			FBYH = fBYH;
		}

		public Builder setSSDZ(String sSDZ) {
			SSDZ = sSDZ;
			return this;
		}

		public Builder setSSBT(String sSBT) {
			SSBT = sSBT;
			return this;
		}

		public Builder setSSNR(String sSNR) {
			SSNR = sSNR;
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

		public Builder setGJC(String gJC) {
			GJC = gJC;
			return this;
		}

		public Builder setLY(int lY) {
			LY = lY;
			return this;
		}

		public Builder setIP(String iP) {
			IP = iP;
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

		public Builder setGXSJ(Date gXSJ) {
			GXSJ = gXSJ;
			return this;
		}

		public Builder setIPDZ(String iPDZ) {
			IPDZ = iPDZ;
			return this;
		}

		public AutmSearch build() {
			return new AutmSearch(this);
		}

	}

	public String getSSDZ() {
		return SSDZ;
	}

	public String getSSBT() {
		return SSBT;
	}

	public String getSSNR() {
		return SSNR;
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

	public String getGJC() {
		return GJC;
	}

	public int getLY() {
		return LY;
	}

	public String getIP() {
		return IP;
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

	public Date getGXSJ() {
		return GXSJ;
	}

	public String getIPDZ() {
		return IPDZ;
	}

}
