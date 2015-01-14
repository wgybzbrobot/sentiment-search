package zx.soft.sent.spider.sina;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.sent.solr.utils.RedisMQ;
import zx.soft.utils.http.HttpClientDaoImpl;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.log.LogbackUtil;
import zx.soft.weibo.core.weibos.SinaPublicWeibos;
import zx.soft.weibo.sina.common.WidToMid;
import zx.soft.weibo.sina.domain.SinaDomain;

/**
 * 新浪公共微博抓取：hefei06机器
 * 单线程模式，多线程暂时不需要，因为新浪公共微博抓起过于频繁的话，重复数据太多。
 * 
 * @author wanggang
 *
 */
public class SinaPublicWeibosSpider {

	private static Logger logger = LoggerFactory.getLogger(SinaPublicWeibosSpider.class);

	// 消息队列类
	private final RedisMQ redisMQ;
	// 新浪微博接口实现类
	private final SinaPublicWeibos sinaPublicWeibos;

	private static final String WEIBO_BASE_URL = "http://weibo.com/";

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

	// 计数器
	private static final AtomicInteger COUNT = new AtomicInteger(0);

	public SinaPublicWeibosSpider() {
		this.redisMQ = new RedisMQ();
		this.sinaPublicWeibos = new SinaPublicWeibos(new HttpClientDaoImpl());
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		SinaPublicWeibosSpider spider = new SinaPublicWeibosSpider();
		spider.run();
	}

	public void run() {
		SinaDomain weibos, weibo, user;
		List<String> records;
		RecordInfo recordInfo;
		while (true) {
			records = new ArrayList<>();
			try {
				weibos = sinaPublicWeibos.getPublicWeibos(200);
				logger.info("Spider sina_public_weibos at:{}, weibos'size={}", COUNT.addAndGet(1), weibos
						.getFieldValues("statuses").size());
				for (Object status : weibos.getFieldValues("statuses")) {
					// 循环添加RecordInfo
					weibo = (SinaDomain) status;
					user = (SinaDomain) weibo.getFieldValue("user");
					recordInfo = new RecordInfo();
					recordInfo.setPlatform(3);
					recordInfo.setSource_id(7);
					recordInfo.setSource_name("新浪微博");
					recordInfo.setIdentify_md5("sentiment-spider");
					recordInfo.setLasttime(System.currentTimeMillis());
					recordInfo.setIdentify_id(100L); // 表示本地
					recordInfo.setCountry_code(1);
					recordInfo.setSource_type(Integer.parseInt(weibo.getFieldValue("source_type").toString()));
					recordInfo.setTimestamp(getTime(weibo.getFieldValue("created_at").toString()));
					recordInfo.setId(weibo.getFieldValue("id").toString());
					recordInfo.setNickname(user.getFieldValue("screen_name").toString());
					recordInfo.setUrl(WEIBO_BASE_URL + user.getFieldValue("id").toString() + "/"
							+ WidToMid.wid2mid(weibo.getFieldValue("id").toString()));
					recordInfo.setContent(weibo.getFieldValue("text").toString());
					recordInfo.setLocation(user.getFieldValue("location").toString());
					recordInfo.setCity_code(Integer.parseInt(user.getFieldValue("city").toString()));
					recordInfo.setProvince_code(Integer.parseInt(user.getFieldValue("province").toString()));
					records.add(JsonUtils.toJsonWithoutPretty(recordInfo));
				}
				redisMQ.addRecord(records.toArray(new String[records.size()]));
			} catch (Exception e) {
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			}
		}
	}

	private long getTime(String timeStr) {
		try {
			return FORMAT.parse(timeStr).getTime();
		} catch (ParseException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			// 如果时间解析错误，则使用当前时间
			return System.currentTimeMillis();
		}
	}

}
