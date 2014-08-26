package zx.soft.sent.dao.domain.platform;

import java.util.Date;

/**
 * 回复类
 * 
 * @author wanggang
 *
 */
public class Reply {

	/*
	Reply与Record映射关系：

	username=HFID
	original_uid=HFFID
	original_url=URL
	nickname=YHZH
	home_url=HFRZY
	content=HFLR
	video_url=SPURL
	pic_url=TPURL
	voice_url=YPURL
	title=HFZT
	timestamp=SCSJ
	geo=GSD
	source_id=LY
	lasttime=JCSJ
	server_id=LZ
	identify_id=BZ
	update_time=GXSJ
	identify_md5=MD5  主键
	url=DQURL
	ip=IP
	location=IPDZ

	 */

	private final String HFID; //回复ID
	private final String HFFID; //回复父ID
	private final String URL; //回复URL
	private final String YHZH; //用户账号
	private final String HFRZY; //回复人主页
	private final String HFLR; //回复内容
	private final String SPURL; //视频URL
	private final String TPURL; //图片URL
	private final String YPURL; //音频URL
	private final String HFZT; //回复主题标题
	private final Date SCSJ; //用户录入时间
	private final String GSD; //用户归属地
	private final int LY; //来源(来源网站名)(不配)
	private final Date JCSJ; //最新监测时间(不配)
	private final int LZ; //来自(来自前置机编号)(不配)
	private final int BZ; //标识
	private final Date GXSJ; //最新更新时间(不配)
	private final String MD5; //MD5唯一标识
	private final String DQURL; //当前页URL
	private final String IP; //IP
	private final String IPDZ; //IP地址

	@Override
	public String toString() {
		return "Reply:{HFID=" + HFID + ",HFFID=" + HFFID + ",URL=" + URL + ",YHZH=" + YHZH + ",HFRZY=" + HFRZY
				+ ",HFLR=" + HFLR + ",SPURL=" + SPURL + ",TPURL=" + TPURL + ",YPURL=" + YPURL + ",HFZT=" + HFZT
				+ ",SCSJ=" + SCSJ + ",GSD=" + GSD + ",LY=" + LY + ",JCSJ=" + JCSJ + ",LZ=" + LZ + ",BZ=" + BZ
				+ ",GXSJ=" + GXSJ + ",MD5=" + MD5 + ",DQURL=" + DQURL + ",IP=" + IP + ",IPDZ=" + IPDZ + "}";
	}

	public Reply(Builder builder) {
		this.HFID = builder.HFID;
		this.HFFID = builder.HFFID;
		this.URL = builder.URL;
		this.YHZH = builder.YHZH;
		this.HFRZY = builder.HFRZY;
		this.HFLR = builder.HFLR;
		this.SPURL = builder.SPURL;
		this.TPURL = builder.TPURL;
		this.YPURL = builder.YPURL;
		this.HFZT = builder.HFZT;
		this.SCSJ = builder.SCSJ;
		this.GSD = builder.GSD;
		this.LY = builder.LY;
		this.JCSJ = builder.JCSJ;
		this.LZ = builder.LZ;
		this.BZ = builder.BZ;
		this.GXSJ = builder.GXSJ;
		this.MD5 = builder.MD5;
		this.DQURL = builder.DQURL;
		this.IP = builder.IP;
		this.IPDZ = builder.IPDZ;
	}

	public static class Builder {

		private String HFID = ""; //回复ID
		private String HFFID = ""; //回复父ID
		private String URL = ""; //回复URL
		private String YHZH = ""; //用户账号
		private String HFRZY = ""; //回复人主页
		private String HFLR = ""; //回复内容
		private String SPURL = ""; //视频URL
		private String TPURL = ""; //图片URL
		private String YPURL = ""; //音频URL
		private String HFZT = ""; //回复主题标题
		private Date SCSJ = null; //用户录入时间
		private String GSD = ""; //用户归属地
		private int LY; //来源(来源网站名)(不配)
		private Date JCSJ = null; //最新监测时间(不配)
		private int LZ; //来自(来自前置机编号)(不配)
		private int BZ; //标识
		private Date GXSJ = null; //最新更新时间(不配)
		private String MD5 = ""; //MD5唯一标识
		private String DQURL = ""; //当前页URL
		private String IP = ""; //IP
		private String IPDZ = ""; //IP地址

		public Builder(String hFID, String uRL) {
			HFID = hFID;
			URL = uRL;
		}

		public Builder setHFID(String hFID) {
			HFID = hFID;
			return this;
		}

		public Builder setHFFID(String hFFID) {
			HFFID = hFFID;
			return this;
		}

		public Builder setYHZH(String yHZH) {
			YHZH = yHZH;
			return this;
		}

		public Builder setHFRZY(String hFRZY) {
			HFRZY = hFRZY;
			return this;
		}

		public Builder setHFLR(String hFLR) {
			HFLR = hFLR;
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

		public Builder setHFZT(String hFZT) {
			HFZT = hFZT;
			return this;
		}

		public Builder setSCSJ(Date sCSJ) {
			SCSJ = sCSJ;
			return this;
		}

		public Builder setGSD(String gSD) {
			GSD = gSD;
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

		public Builder setGXSJ(Date gXSJ) {
			GXSJ = gXSJ;
			return this;
		}

		public Builder setMD5(String mD5) {
			MD5 = mD5;
			return this;
		}

		public Builder setDQURL(String dQURL) {
			DQURL = dQURL;
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

		public Reply build() {
			return new Reply(this);
		}

	}

	public String getHFID() {
		return HFID;
	}

	public String getHFFID() {
		return HFFID;
	}

	public String getURL() {
		return URL;
	}

	public String getYHZH() {
		return YHZH;
	}

	public String getHFRZY() {
		return HFRZY;
	}

	public String getHFLR() {
		return HFLR;
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

	public String getHFZT() {
		return HFZT;
	}

	public Date getSCSJ() {
		return SCSJ;
	}

	public String getGSD() {
		return GSD;
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

	public Date getGXSJ() {
		return GXSJ;
	}

	public String getMD5() {
		return MD5;
	}

	public String getDQURL() {
		return DQURL;
	}

	public String getIP() {
		return IP;
	}

	public String getIPDZ() {
		return IPDZ;
	}

}
