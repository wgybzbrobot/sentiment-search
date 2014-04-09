package zx.soft.spider.dao.domain;

public class WeiboOldInfo {

	private Long wid;
	private Long username;
	private int repostsCount;
	private int commentsCount;
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

	public Long getWid() {
		return wid;
	}

	public void setWid(Long wid) {
		this.wid = wid;
	}

	public Long getUsername() {
		return username;
	}

	public void setUsername(Long username) {
		this.username = username;
	}

	public int getRepostsCount() {
		return repostsCount;
	}

	public void setRepostsCount(int repostsCount) {
		this.repostsCount = repostsCount;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getCreateat() {
		return createat;
	}

	public void setCreateat(Long createat) {
		this.createat = createat;
	}

	public Long getOwid() {
		return owid;
	}

	public void setOwid(Long owid) {
		this.owid = owid;
	}

	public Long getOusername() {
		return ousername;
	}

	public void setOusername(Long ousername) {
		this.ousername = ousername;
	}

	public Boolean getFavorited() {
		return favorited;
	}

	public void setFavorited(Boolean favorited) {
		this.favorited = favorited;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getOriginalpic() {
		return originalpic;
	}

	public void setOriginalpic(String originalpic) {
		this.originalpic = originalpic;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

}
