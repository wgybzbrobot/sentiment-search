package zx.soft.sent.dao.domain;


public class RecordSelect {

	private String id; // 记录id，一般通过记录的url进行md5加密得到
	private int platform; // 平台类型，如：博客、微博、论坛等，用数字代替
	private String mid; // 主id
	private String username; // 用户id
	private String nickname; // 用户昵称
	private String original_uid; // 原创用户或者父用户id
	private String original_name; // 原创用户或者父用户昵称
	private String original_title; // 原创记录或者记录户标题
	private String original_url; // 原创记录或者父记录url
	private String url; // 该记录url
	private String home_url; // 用户首页url
	private String title; // 该记录标题
	private String type; // 该记录所属类别
	private String content; // 该记录内容
	private int comment_count; // 评论数
	private int read_count; // 阅读数
	private int favorite_count; // 收藏数
	private int attitude_count; // 赞数
	private int repost_count; // 转发数
	private String video_url; // 视频url
	private String pic_url; // 图片url
	private String voice_url; // 音频url
	private long timestamp; // 该记录发布时间
	private int source_id; // 来源网站名
	private long lasttime; // 最新监测时间
	private int server_id; // 来自前置机编号
	private long identify_id; // 标志id
	private String identify_md5; // 标志md5
	private String keyword; // 关键词
	private long first_time; // 首次发现时间
	private long update_time; // 最新更新时间
	private String ip; // 该记录发布的ip地址
	private String location; // 该记录发布的区域地址
	private String geo; // 该记录发布的地理位置信息
	private String receive_addr; // 收件人地址
	private String append_addr; // 抄送人地址
	private String send_addr; // 发送人地址
	private String source_name; // 来源名称
	private int source_type;
	private int country_code; // 国家代码
	private int location_code; // 区域编码
	private int province_code;
	private int city_code;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getOriginal_uid() {
		return original_uid;
	}
	public void setOriginal_uid(String original_uid) {
		this.original_uid = original_uid;
	}
	public String getOriginal_name() {
		return original_name;
	}
	public void setOriginal_name(String original_name) {
		this.original_name = original_name;
	}
	public String getOriginal_title() {
		return original_title;
	}
	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}
	public String getOriginal_url() {
		return original_url;
	}
	public void setOriginal_url(String original_url) {
		this.original_url = original_url;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHome_url() {
		return home_url;
	}
	public void setHome_url(String home_url) {
		this.home_url = home_url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public int getRead_count() {
		return read_count;
	}
	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}
	public int getFavorite_count() {
		return favorite_count;
	}
	public void setFavorite_count(int favorite_count) {
		this.favorite_count = favorite_count;
	}
	public int getAttitude_count() {
		return attitude_count;
	}
	public void setAttitude_count(int attitude_count) {
		this.attitude_count = attitude_count;
	}
	public int getRepost_count() {
		return repost_count;
	}
	public void setRepost_count(int repost_count) {
		this.repost_count = repost_count;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String getVoice_url() {
		return voice_url;
	}
	public void setVoice_url(String voice_url) {
		this.voice_url = voice_url;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public int getSource_id() {
		return source_id;
	}
	public void setSource_id(int source_id) {
		this.source_id = source_id;
	}
	public long getLasttime() {
		return lasttime;
	}
	public void setLasttime(long lasttime) {
		this.lasttime = lasttime;
	}
	public int getServer_id() {
		return server_id;
	}
	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}
	public long getIdentify_id() {
		return identify_id;
	}
	public void setIdentify_id(long identify_id) {
		this.identify_id = identify_id;
	}
	public String getIdentify_md5() {
		return identify_md5;
	}
	public void setIdentify_md5(String identify_md5) {
		this.identify_md5 = identify_md5;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public long getFirst_time() {
		return first_time;
	}
	public void setFirst_time(long first_time) {
		this.first_time = first_time;
	}
	public long getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getGeo() {
		return geo;
	}
	public void setGeo(String geo) {
		this.geo = geo;
	}
	public String getReceive_addr() {
		return receive_addr;
	}
	public void setReceive_addr(String receive_addr) {
		this.receive_addr = receive_addr;
	}
	public String getAppend_addr() {
		return append_addr;
	}
	public void setAppend_addr(String append_addr) {
		this.append_addr = append_addr;
	}
	public String getSend_addr() {
		return send_addr;
	}
	public void setSend_addr(String send_addr) {
		this.send_addr = send_addr;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public int getSource_type() {
		return source_type;
	}

	public void setSource_type(int source_type) {
		this.source_type = source_type;
	}
	public int getCountry_code() {
		return country_code;
	}
	public void setCountry_code(int country_code) {
		this.country_code = country_code;
	}
	public int getLocation_code() {
		return location_code;
	}
	public void setLocation_code(int location_code) {
		this.location_code = location_code;
	}
	public int getProvince_code() {
		return province_code;
	}
	public void setProvince_code(int province_code) {
		this.province_code = province_code;
	}
	public int getCity_code() {
		return city_code;
	}
	public void setCity_code(int city_code) {
		this.city_code = city_code;
	}

}
