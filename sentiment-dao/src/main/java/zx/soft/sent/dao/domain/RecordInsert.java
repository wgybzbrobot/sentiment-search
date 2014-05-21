package zx.soft.sent.dao.domain;

/**
 * Solr中的每条记录类
 * @author wanggang
 *
 */
public class RecordInsert {

	private final String tablename;
	private final String id; // 记录id，一般通过记录的url进行md5加密得到
	private final int platform; // 平台类型，如：博客、微博、论坛等，用数字代替
	private final String mid; // 主id
	private final String username; // 用户id
	private final String nickname; // 用户昵称
	private final String original_id; // 原创记录id
	private final String original_uid; // 原创用户或者父用户id
	private final String original_name; // 原创用户或者父用户昵称
	private final String original_title; // 原创记录或者记录户标题
	private final String original_url; // 原创记录或者父记录url
	private final String url; // 该记录url
	private final String home_url; // 用户首页url
	private final String title; // 该记录标题
	private final String type; // 该记录所属类别
	private final boolean isharmful; // 是否是有害信息
	private final String content; // 该记录内容
	private final int comment_count; // 评论数
	private final int read_count; // 阅读数
	private final int favorite_count; // 收藏数
	private final int attitude_count; // 赞数
	private final int repost_count; // 转发数
	private final String video_url; // 视频url
	private final String pic_url; // 图片url
	private final String voice_url; // 音频url
	private final long timestamp; // 该记录发布时间
	private final int source_id; // 来源网站名
	private final long lasttime; // 最新监测时间
	private final int server_id; // 来自前置机编号
	private final long identify_id; // 标志id
	private final String identify_md5; // 标志md5
	private final String keyword; // 关键词
	private final long first_time; // 首次发现时间
	private final long update_time; // 最新更新时间
	private final String ip; // 该记录发布的ip地址
	private final String location; // 该记录发布的区域地址
	private final String geo; // 该记录发布的地理位置信息
	private final String receive_addr; // 收件人地址
	private final String append_addr; // 抄送人地址
	private final String send_addr; // 发送人地址
	private final String source_name; // 来源名称
	private final int source_type;
	private final int country_code; // 国家代码
	private final int location_code; // 区域编码
	private final int province_code;
	private final int city_code;

	@Override
	public String toString() {
		return "Record:{id=" + id + ",platform=" + platform + ",mid=" + mid + ",username=" + username + ",nickname="
				+ nickname + ",original_id=" + original_id + ",original_uid=" + original_uid + ",original_name="
				+ original_name + ",original_title=" + original_title + ",original_url=" + original_url + ",url=" + url
				+ ",home_url=" + home_url + ",title=" + title + ",type=" + type + ",isharmful=" + isharmful
				+ ",content=" + content + ",comment_count=" + comment_count + ",read_count=" + read_count
				+ ",favorite_count=" + favorite_count + ",attitude_count=" + attitude_count + ",repost_count="
				+ repost_count + ",video_url=" + video_url + ",pic_url=" + pic_url + ",voice_url=" + voice_url
				+ ",timestamp=" + timestamp + ",source_id=" + source_id + ",lasttime=" + lasttime + ",server_id="
				+ server_id + ",identify_id=" + identify_id + ",identify_md5=" + identify_md5 + ",keyword=" + keyword
				+ ",first_time=" + first_time + ",update_time=" + update_time + ",ip=" + ip + ",location=" + location
				+ ",geo=" + geo + ",receive_addr=" + receive_addr + ",append_addr=" + append_addr + ",send_addr="
				+ send_addr + ",source_name=" + source_name + ",country_code=" + country_code + ",location_code="
				+ location_code + ",province_code=" + province_code + ",city_code=" + city_code + ",source_type="
				+ source_type + "}";
	}

	public RecordInsert(Builder builder) {
		this.tablename = builder.tablename;
		this.id = builder.id;
		this.platform = builder.platform;
		this.mid = builder.mid;
		this.username = builder.username;
		this.nickname = builder.nickname;
		this.original_id = builder.original_id;
		this.original_uid = builder.original_uid;
		this.original_name = builder.original_name;
		this.original_title = builder.original_title;
		this.original_url = builder.original_url;
		this.url = builder.url;
		this.home_url = builder.home_url;
		this.title = builder.title;
		this.type = builder.type;
		this.isharmful = builder.isharmful;
		this.content = builder.content;
		this.comment_count = builder.comment_count;
		this.read_count = builder.read_count;
		this.favorite_count = builder.favorite_count;
		this.attitude_count = builder.attitude_count;
		this.repost_count = builder.repost_count;
		this.video_url = builder.video_url;
		this.pic_url = builder.pic_url;
		this.voice_url = builder.voice_url;
		this.timestamp = builder.timestamp;
		this.source_id = builder.source_id;
		this.lasttime = builder.lasttime;
		this.server_id = builder.server_id;
		this.identify_id = builder.identify_id;
		this.identify_md5 = builder.identify_md5;
		this.keyword = builder.keyword;
		this.first_time = builder.first_time;
		this.update_time = builder.update_time;
		this.ip = builder.ip;
		this.location = builder.location;
		this.geo = builder.geo;
		this.receive_addr = builder.receive_addr;
		this.append_addr = builder.append_addr;
		this.send_addr = builder.send_addr;
		this.source_name = builder.source_name;
		this.country_code = builder.country_code;
		this.location_code = builder.location_code;
		this.province_code = builder.province_code;
		this.city_code = builder.city_code;
		this.source_type = builder.source_type;
	}

	public static class Builder {

		private final String tablename;
		private final String id;
		private final int platform;
		private String mid = "";
		private String username = "";
		private String nickname = "";
		private String original_id = "";
		private String original_uid = "";
		private String original_name = "";
		private String original_title = "";
		private String original_url = "";
		private String url = "";
		private String home_url = "";
		private String title = "";
		private String type = "";
		private boolean isharmful = Boolean.TRUE;
		private String content = "";
		private int comment_count;
		private int read_count;
		private int favorite_count;
		private int attitude_count;
		private int repost_count;
		private String video_url = "";
		private String pic_url = "";
		private String voice_url = "";
		private long timestamp;
		private int source_id;
		private long lasttime;
		private int server_id;
		private long identify_id;
		private String identify_md5 = "";
		private String keyword = "";
		private long first_time;
		private long update_time;
		private String ip = "";
		private String location = "";
		private String geo = "";
		private String receive_addr = "";
		private String append_addr = "";
		private String send_addr = "";
		private String source_name = "";
		private int source_type;
		private int country_code;
		private int location_code;
		private int province_code;
		private int city_code;

		public Builder(String tablename, String id, int platform) {
			this.tablename = tablename;
			this.id = id;
			this.platform = platform;
		}

		public Builder setMid(String mid) {
			this.mid = mid;
			return this;
		}

		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder setNickname(String nickname) {
			this.nickname = nickname;
			return this;
		}

		public Builder setOriginal_id(String original_id) {
			this.original_id = original_id;
			return this;
		}

		public Builder setOriginal_uid(String original_uid) {
			this.original_uid = original_uid;
			return this;
		}

		public Builder setOriginal_name(String original_name) {
			this.original_name = original_name;
			return this;
		}

		public Builder setOriginal_title(String original_title) {
			this.original_title = original_title;
			return this;
		}

		public Builder setOriginal_url(String original_url) {
			this.original_url = original_url;
			return this;
		}

		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}

		public Builder setHome_url(String home_url) {
			this.home_url = home_url;
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setType(String type) {
			this.type = type;
			return this;
		}

		public Builder setIsharmful(boolean isharmful) {
			this.isharmful = isharmful;
			return this;
		}

		public Builder setContent(String content) {
			this.content = content;
			return this;
		}

		public Builder setComment_count(int comment_count) {
			this.comment_count = comment_count;
			return this;
		}

		public Builder setRead_count(int read_count) {
			this.read_count = read_count;
			return this;
		}

		public Builder setFavorite_count(int favorite_count) {
			this.favorite_count = favorite_count;
			return this;
		}

		public Builder setAttitude_count(int attitude_count) {
			this.attitude_count = attitude_count;
			return this;
		}

		public Builder setRepost_count(int repost_count) {
			this.repost_count = repost_count;
			return this;
		}

		public Builder setVideo_url(String video_url) {
			this.video_url = video_url;
			return this;
		}

		public Builder setPic_url(String pic_url) {
			this.pic_url = pic_url;
			return this;
		}

		public Builder setVoice_url(String voice_url) {
			this.voice_url = voice_url;
			return this;
		}

		public Builder setTimestamp(long timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder setSource_id(int source_id) {
			this.source_id = source_id;
			return this;
		}

		public Builder setLasttime(long lasttime) {
			this.lasttime = lasttime;
			return this;
		}

		public Builder setServer_id(int server_id) {
			this.server_id = server_id;
			return this;
		}

		public Builder setIdentify_id(long identify_id) {
			this.identify_id = identify_id;
			return this;
		}

		public Builder setIdentify_md5(String identify_md5) {
			this.identify_md5 = identify_md5;
			return this;
		}

		public Builder setKeyword(String keyword) {
			this.keyword = keyword;
			return this;
		}

		public Builder setFirst_time(long first_time) {
			this.first_time = first_time;
			return this;
		}

		public Builder setUpdate_time(long update_time) {
			this.update_time = update_time;
			return this;
		}

		public Builder setIp(String ip) {
			this.ip = ip;
			return this;
		}

		public Builder setLocation(String location) {
			this.location = location;
			return this;
		}

		public Builder setGeo(String geo) {
			this.geo = geo;
			return this;
		}

		public Builder setReceive_addr(String receive_addr) {
			this.receive_addr = receive_addr;
			return this;
		}

		public Builder setAppend_addr(String append_addr) {
			this.append_addr = append_addr;
			return this;
		}

		public Builder setSend_addr(String send_addr) {
			this.send_addr = send_addr;
			return this;
		}

		public Builder setSource_name(String source_name) {
			this.source_name = source_name;
			return this;
		}

		public Builder setCountry_code(int country_code) {
			this.country_code = country_code;
			return this;
		}

		public Builder setLocation_code(int location_code) {
			this.location_code = location_code;
			return this;
		}

		public Builder setProvince_code(int province_code) {
			this.province_code = province_code;
			return this;
		}

		public Builder setCity_code(int city_code) {
			this.city_code = city_code;
			return this;
		}

		public Builder setSource_type(int source_type) {
			this.source_type = source_type;
			return this;
		}

		public RecordInsert build() {
			return new RecordInsert(this);
		}

	}

	public String getId() {
		return id;
	}

	public int getPlatform() {
		return platform;
	}

	public String getMid() {
		return mid;
	}

	public String getUsername() {
		return username;
	}

	public String getNickname() {
		return nickname;
	}

	public String getOriginal_id() {
		return original_id;
	}

	public String getOriginal_uid() {
		return original_uid;
	}

	public String getOriginal_name() {
		return original_name;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public String getOriginal_url() {
		return original_url;
	}

	public String getUrl() {
		return url;
	}

	public String getHome_url() {
		return home_url;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public boolean isIsharmful() {
		return isharmful;
	}

	public String getContent() {
		return content;
	}

	public int getComment_count() {
		return comment_count;
	}

	public int getRead_count() {
		return read_count;
	}

	public int getFavorite_count() {
		return favorite_count;
	}

	public int getAttitude_count() {
		return attitude_count;
	}

	public int getRepost_count() {
		return repost_count;
	}

	public String getVideo_url() {
		return video_url;
	}

	public String getPic_url() {
		return pic_url;
	}

	public String getVoice_url() {
		return voice_url;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getSource_id() {
		return source_id;
	}

	public long getLasttime() {
		return lasttime;
	}

	public int getServer_id() {
		return server_id;
	}

	public long getIdentify_id() {
		return identify_id;
	}

	public String getIdentify_md5() {
		return identify_md5;
	}

	public String getKeyword() {
		return keyword;
	}

	public long getFirst_time() {
		return first_time;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public String getIp() {
		return ip;
	}

	public String getLocation() {
		return location;
	}

	public String getGeo() {
		return geo;
	}

	public String getReceive_addr() {
		return receive_addr;
	}

	public String getAppend_addr() {
		return append_addr;
	}

	public String getSend_addr() {
		return send_addr;
	}

	public String getSource_name() {
		return source_name;
	}

	public int getCountry_code() {
		return country_code;
	}

	public int getLocation_code() {
		return location_code;
	}

	public String getTablename() {
		return tablename;
	}

	public int getProvince_code() {
		return province_code;
	}

	public int getCity_code() {
		return city_code;
	}

	public int getSource_type() {
		return source_type;
	}

}
