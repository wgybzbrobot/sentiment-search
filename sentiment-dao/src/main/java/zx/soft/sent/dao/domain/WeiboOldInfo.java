package zx.soft.sent.dao.domain;

/**
 * 旧的微博信息类
 * 
 * @author wanggang
 *
 */
public class WeiboOldInfo {

	private String wid;
	private String username;
	private int repostsCount;
	private int commentsCount;
	private String text;
	private long createat;
	private String owid;
	private String ousername;
	private Boolean favorited;
	private String geo;
	private double latitude;
	private double longitude;
	private String originalpic;
	private String source;
	private String visible;

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
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

	public String getOwid() {
		return owid;
	}

	public void setOwid(String owid) {
		this.owid = owid;
	}

	public String getOusername() {
		return ousername;
	}

	public void setOusername(String ousername) {
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
