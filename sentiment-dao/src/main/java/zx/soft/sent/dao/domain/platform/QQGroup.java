package zx.soft.sent.dao.domain.platform;

import java.util.Date;

/**
 * QQ群类
 * 
 * @author wanggang
 *
 */
public class QQGroup {

	/*
	QQGroup与Record的映射关系：

	mid=ID
	original_uid=QH
	original_name=MC
	original_title=GG
	nickname=YHNC
	username=FBYH
	content=FBLR
	timestamp=FBSJ
	source_id=LY
	server_id=LZ
	lasttime=JCSJ
	identify_id=BZ

	 */

	private final String ID; //时间戳生成ID
	private final String QH; //QQ群号
	private final String MC; //QQ群名称
	private final String GG; //QQ群公告
	private final String YHNC; //用户昵称（发布者的QQ昵称）
	private final String FBYH; //发布用户（发布者的QQ号）
	private final String FBLR; //发布内容
	private final Date FBSJ; //发布时间
	private final int LY; //来源(来源网站名)(不配)
	private final int LZ; //来自(来自前置机编号)
	private final Date JCSJ; //最新监测时间(不配)
	private final int BZ; //标识

	@Override
	public String toString() {
		return "QQGroup:{ID=" + ID + ",QH=" + QH + ",MC=" + MC + ",GG=" + GG + ",YHNC=" + YHNC + ",FBYH=" + FBYH
				+ ",FBLR=" + FBLR + ",FBSJ=" + FBSJ + ",LY=" + LY + ",LZ=" + LZ + ",JCSJ=" + JCSJ + ",BZ=" + BZ + "}";
	}

	public QQGroup(Builder builder) {
		this.ID = builder.ID;
		this.QH = builder.QH;
		this.MC = builder.MC;
		this.GG = builder.GG;
		this.YHNC = builder.YHNC;
		this.FBYH = builder.FBYH;
		this.FBLR = builder.FBLR;
		this.FBSJ = builder.FBSJ;
		this.LY = builder.LY;
		this.LZ = builder.LZ;
		this.JCSJ = builder.JCSJ;
		this.BZ = builder.BZ;
	}

	public static class Builder {

		private String ID = ""; //时间戳生成ID
		private String QH = ""; //QQ群号
		private String MC = ""; //QQ群名称
		private String GG = ""; //QQ群公告
		private String YHNC = ""; //用户昵称（发布者的QQ昵称）
		private String FBYH = ""; //发布用户（发布者的QQ号）
		private String FBLR = ""; //发布内容
		private Date FBSJ = null; //发布时间
		private int LY; //来源(来源网站名)(不配)
		private int LZ; //来自(来自前置机编号)
		private Date JCSJ = null; //最新监测时间(不配)
		private int BZ; //标识

		public Builder(String QH, String fBYH) {
			this.QH = QH;
			this.FBYH = fBYH;
		}

		public Builder setID(String iD) {
			ID = iD;
			return this;
		}

		public Builder setMC(String mC) {
			MC = mC;
			return this;
		}

		public Builder setGG(String gG) {
			GG = gG;
			return this;
		}

		public Builder setYHNC(String yHNC) {
			YHNC = yHNC;
			return this;
		}

		public Builder setFBLR(String fBLR) {
			FBLR = fBLR;
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

		public Builder setLZ(int lZ) {
			LZ = lZ;
			return this;
		}

		public Builder setJCSJ(Date jCSJ) {
			JCSJ = jCSJ;
			return this;
		}

		public Builder setBZ(int bZ) {
			BZ = bZ;
			return this;
		}

		public QQGroup build() {
			return new QQGroup(this);
		}

	}

	public String getID() {
		return ID;
	}

	public String getQH() {
		return QH;
	}

	public String getMC() {
		return MC;
	}

	public String getGG() {
		return GG;
	}

	public String getYHNC() {
		return YHNC;
	}

	public String getFBYH() {
		return FBYH;
	}

	public String getFBLR() {
		return FBLR;
	}

	public Date getFBSJ() {
		return FBSJ;
	}

	public int getLY() {
		return LY;
	}

	public int getLZ() {
		return LZ;
	}

	public Date getJCSJ() {
		return JCSJ;
	}

	public int getBZ() {
		return BZ;
	}

}
