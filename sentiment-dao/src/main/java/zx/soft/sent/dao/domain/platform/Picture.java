package zx.soft.sent.dao.domain.platform;

/**
 * 图片类
 * 
 * @author wanggang
 *
 */
public class Picture {

	/*
	 * Picture与Record映射
	id=md5(URL)
	platform=9
	indetify_md5=TPMD5
	url=URL
	content=TPLR
	pic_url=TPLJ
	isharmful=SFYH
	identify_id=BZ
	*/

	private final String TPMD5; // 图片MD5
	private final String URL; // 图片地址URL
	private final String TPLR; // 图片内容
	private final String TPLJ; // 图片路径
	private final boolean SFYH; // 是否有害信息，false–否，true–是
	private final long BZ; // 图片标识

	@Override
	public String toString() {
		return "Picture:{TPMD5=" + TPMD5 + ",URL=" + URL + ",TPLR=" + TPLR + ",TPLJ=" + TPLJ + ",SFYH=" + SFYH + ",BZ="
				+ BZ + "}";
	}

	public Picture(Builder builder) {
		this.TPMD5 = builder.TPMD5;
		this.URL = builder.URL;
		this.TPLR = builder.TPLR;
		this.TPLJ = builder.TPLJ;
		this.SFYH = builder.SFYH;
		this.BZ = builder.BZ;
	}

	public static class Builder {

		private String TPMD5 = ""; // 图片MD5
		private String URL = ""; // 图片地址URL
		private String TPLR = ""; // 图片内容
		private String TPLJ = ""; // 图片路径
		private boolean SFYH = Boolean.TRUE; // 是否有害信息，false–否，true–是
		private long BZ; // 图片路径

		public Builder(String TPMD5, String URL) {
			this.TPMD5 = TPMD5;
			this.URL = URL;
		}

		public Builder setTPLR(String tPLR) {
			TPLR = tPLR;
			return this;
		}

		public Builder setTPLJ(String tPLJ) {
			TPLJ = tPLJ;
			return this;
		}

		public Builder setSFYH(boolean sFYH) {
			SFYH = sFYH;
			return this;
		}

		public Builder setBZ(long bZ) {
			BZ = bZ;
			return this;
		}

		public Picture build() {
			return new Picture(this);
		}

	}

	public String getTPMD5() {
		return TPMD5;
	}

	public String getURL() {
		return URL;
	}

	public String getTPLR() {
		return TPLR;
	}

	public String getTPLJ() {
		return TPLJ;
	}

	public boolean isSFYH() {
		return SFYH;
	}

	public long getBZ() {
		return BZ;
	}

}
