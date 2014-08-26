package zx.soft.sent.dao.domain.platform;

import java.util.Date;

/**
 * 邮件类
 * 
 * @author wanggang
 *
 */
public class Email {

	/*
	Email与Record映射关系：

	mid=QJID
	username=ID
	receive_addr=SJRDZ
	append_addr=CSRDZ
	send_addr=FJRDZ
	title=YJBT
	content=YJLR
	timestamp=FSSJ
	type=LX
	first_time=FXSJ
	update_time=GXSJ
	source_id=LY
	lasttime=JCSJ
	server_id=LZ
	identify_id=BZ

	 */

	private final String QJID; //全局ID
	private final String ID; //UID
	private final String SJRDZ; //收件人地址
	private final String CSRDZ; //抄送人地址
	private final String FJRDZ; //发件人地址
	private final String YJBT; //邮件标题
	private final String YJLR; //邮件内容
	private final Date FSSJ; //发送时间
	private final String LX; //类型
	private final Date FXSJ; //首次发现时间(不配)
	private final Date GXSJ; //最新更新时间(不配)
	private final int LY; //来源(来源网站名)(不配)
	private final Date JCSJ; //最新监测时间(不配)
	private final int LZ; //来自(来自前置机编号)(不配)
	private final int BZ; //标识(不配)

	@Override
	public String toString() {
		return "Email:{QJID=" + QJID + ",ID=" + ID + ",SJRDZ=" + SJRDZ + ",CSRDZ=" + CSRDZ + ",FJRDZ=" + FJRDZ
				+ ",YJBT=" + YJBT + ",YJLR=" + YJLR + ",FSSJ=" + FSSJ + ",LX=" + LX + ",FXSJ=" + FXSJ + ",GXSJ=" + GXSJ
				+ ",LY=" + LY + ",JCSJ=" + JCSJ + ",LZ=" + LZ + ",BZ=" + BZ + "}";
	}

	public Email(Builder builder) {
		this.QJID = builder.QJID;
		this.ID = builder.ID;
		this.SJRDZ = builder.SJRDZ;
		this.CSRDZ = builder.CSRDZ;
		this.FJRDZ = builder.FJRDZ;
		this.YJBT = builder.YJBT;
		this.YJLR = builder.YJLR;
		this.FSSJ = builder.FSSJ;
		this.LX = builder.LX;
		this.FXSJ = builder.FXSJ;
		this.GXSJ = builder.GXSJ;
		this.LY = builder.LY;
		this.JCSJ = builder.JCSJ;
		this.LZ = builder.LZ;
		this.BZ = builder.BZ;
	}

	public static class Builder {

		private String QJID = ""; //全局ID
		private String ID = ""; //UID
		private String SJRDZ = ""; //收件人地址
		private String CSRDZ = ""; //抄送人地址
		private String FJRDZ = ""; //发件人地址
		private String YJBT = ""; //邮件标题
		private String YJLR = ""; //邮件内容
		private Date FSSJ = null; //发送时间
		private String LX = ""; //类型
		private Date FXSJ = null; //首次发现时间(不配)
		private Date GXSJ = null; //最新更新时间(不配)
		private int LY; //来源(来源网站名)(不配)
		private Date JCSJ = null; //最新监测时间(不配)
		private int LZ; //来自(来自前置机编号)(不配)
		private int BZ; //标识(不配)

		public Builder(String qJID, String fJRDZ) {
			QJID = qJID;
			FJRDZ = fJRDZ;
		}

		public Builder setQJID(String qJID) {
			QJID = qJID;
			return this;
		}

		public Builder setID(String iD) {
			ID = iD;
			return this;
		}

		public Builder setSJRDZ(String sJRDZ) {
			SJRDZ = sJRDZ;
			return this;
		}

		public Builder setCSRDZ(String cSRDZ) {
			CSRDZ = cSRDZ;
			return this;
		}

		public Builder setFJRDZ(String fJRDZ) {
			FJRDZ = fJRDZ;
			return this;
		}

		public Builder setYJBT(String yJBT) {
			YJBT = yJBT;
			return this;
		}

		public Builder setYJLR(String yJLR) {
			YJLR = yJLR;
			return this;
		}

		public Builder setFSSJ(Date fSSJ) {
			FSSJ = fSSJ;
			return this;
		}

		public Builder setLX(String lX) {
			LX = lX;
			return this;
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

		public Email build() {
			return new Email(this);
		}

	}

	public String getQJID() {
		return QJID;
	}

	public String getID() {
		return ID;
	}

	public String getSJRDZ() {
		return SJRDZ;
	}

	public String getCSRDZ() {
		return CSRDZ;
	}

	public String getFJRDZ() {
		return FJRDZ;
	}

	public String getYJBT() {
		return YJBT;
	}

	public String getYJLR() {
		return YJLR;
	}

	public Date getFSSJ() {
		return FSSJ;
	}

	public String getLX() {
		return LX;
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

}
