package zx.soft.sent.dao.domain;

public class SinaWeibo {

	private final Long wid;
	private final Long username;
	private final int repostsCount;
	private final int commentsCount;
	private final int attitudesCount;
	private final String text;
	private final Long createat;
	private final Long owid;
	private final Long ousername;
	private final Boolean favorited;
	private final String geo;
	private final double latitude;
	private final double longitude;
	private final String originalpic;
	private final String source;
	private final String visible;
	private final int mlevel;

	public SinaWeibo(Builder builder) {
		this.wid = builder.wid;
		this.username = builder.username;
		this.repostsCount = builder.repostsCount;
		this.commentsCount = builder.commentsCount;
		this.attitudesCount = builder.attitudesCount;
		this.text = builder.text;
		this.createat = builder.createat;
		this.owid = builder.owid;
		this.ousername = builder.ousername;
		this.favorited = builder.favorited;
		this.geo = builder.geo;
		this.latitude = builder.latitude;
		this.longitude = builder.longitude;
		this.originalpic = builder.originalpic;
		this.source = builder.source;
		this.visible = builder.visible;
		this.mlevel = builder.mlevel;
	}

	public static class Builder {

		private final Long wid;
		private final Long username;
		private int repostsCount;
		private int commentsCount;
		private int attitudesCount;
		private String text;
		private Long createat;
		private Long owid;
		private Long ousername;
		private Boolean favorited;
		private String geo;
		private double latitude;
		private double longitude;
		private String originalpic;
		private String source;
		private String visible;
		private int mlevel;

		public Builder(Long wid, Long username) {
			this.wid = wid;
			this.username = username;
		}

		public Builder setRepostsCount(int repostsCount) {
			this.repostsCount = repostsCount;
			return this;
		}

		public Builder setCommentsCount(int commentsCount) {
			this.commentsCount = commentsCount;
			return this;
		}

		public Builder setAttitudesCount(int attitudesCount) {
			this.attitudesCount = attitudesCount;
			return this;
		}

		public Builder setText(String text) {
			this.text = text;
			return this;
		}

		public Builder setCreateat(Long createat) {
			this.createat = createat;
			return this;
		}

		public Builder setOwid(Long owid) {
			this.owid = owid;
			return this;
		}

		public Builder setOusername(Long ousername) {
			this.ousername = ousername;
			return this;
		}

		public Builder setFavorited(Boolean favorited) {
			this.favorited = favorited;
			return this;
		}

		public Builder setGeo(String geo) {
			this.geo = geo;
			return this;
		}

		public Builder setLatitude(double latitude) {
			this.latitude = latitude;
			return this;
		}

		public Builder setLongitude(double longitude) {
			this.longitude = longitude;
			return this;
		}

		public Builder setOriginalpic(String originalpic) {
			this.originalpic = originalpic;
			return this;
		}

		public Builder setSource(String source) {
			this.source = source;
			return this;
		}

		public Builder setVisible(String visible) {
			this.visible = visible;
			return this;
		}

		public Builder setMlevel(int mlevel) {
			this.mlevel = mlevel;
			return this;
		}

		public SinaWeibo build() {
			return new SinaWeibo(this);
		}

	}

	public Long getWid() {
		return wid;
	}

	public Long getUsername() {
		return username;
	}

	public int getRepostsCount() {
		return repostsCount;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public int getAttitudesCount() {
		return attitudesCount;
	}

	public String getText() {
		return text;
	}

	public Long getCreateat() {
		return createat;
	}

	public Long getOwid() {
		return owid;
	}

	public Long getOusername() {
		return ousername;
	}

	public Boolean getFavorited() {
		return favorited;
	}

	public String getGeo() {
		return geo;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getOriginalpic() {
		return originalpic;
	}

	public String getSource() {
		return source;
	}

	public String getVisible() {
		return visible;
	}

	public int getMlevel() {
		return mlevel;
	}

}
