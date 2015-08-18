package zx.soft.sent.spider.sina;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.sent.solr.utils.RedisMQ;
import zx.soft.utils.checksum.CheckSumUtils;
import zx.soft.utils.http.HttpClientDaoImpl;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.log.LogbackUtil;
import zx.soft.utils.threads.ApplyThreadPool;
import zx.soft.weibo.core.weibos.SinaPublicWeibosSentiment;
import zx.soft.weibo.sina.common.WidToMid;
import zx.soft.weibo.sina.domain.SinaDomain;

/**
 * 新浪公共微博抓取：hefei06
 * 单线程模式，多线程暂时不需要，因为新浪公共微博抓起过于频繁的话，重复数据太多。
 *
 * 运行目录：/home/zxdfs/run-work/spider
 * 运行命令：cd sentiment-spider
 *        ./timer-spider.sh &
 *
 * @author wanggang
 *
 */
public class SinaPublicWeibosSpider {

	private static Logger logger = LoggerFactory.getLogger(SinaPublicWeibosSpider.class);

	private static final String WEIBO_BASE_URL = "http://weibo.com/";

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

	/**
	 * 主函数
	 */
	public static void main(String[] args) throws Exception {

		RedisMQ redisMQ = new RedisMQ();
		SinaPublicWeibosSentiment sinaPublicWeibos = new SinaPublicWeibosSentiment(new HttpClientDaoImpl());
		AtomicInteger COUNT = new AtomicInteger(0);
		while (true) {
			spider(redisMQ, sinaPublicWeibos, COUNT);
		}

	}

	public void run() {
		final int cpuNum = 1;
		final ThreadPoolExecutor pool = ApplyThreadPool.getThreadPoolExector(cpuNum);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				pool.shutdown();
			}
		}));

		RedisMQ redisMQ = new RedisMQ();
		SinaPublicWeibosSentiment sinaPublicWeibos = new SinaPublicWeibosSentiment(new HttpClientDaoImpl());

		while (true) {
			try {
				pool.execute(new SpiderRunnable(redisMQ, sinaPublicWeibos));
			} catch (Exception e) {
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
				if (pool.isShutdown()) {
					logger.error("Threads poll is shutdown!");
					break;
				}
			}
		}

		// common-utils新版本中
		//		ProcessAnalysis.killPid(ProcessAnalysis.getCurrentPidByLang());
		//		System.err.println("Closed this processor......");
	}

	public static class SpiderRunnable implements Runnable {

		// 计数器
		private static final AtomicInteger COUNT = new AtomicInteger(0);

		// 消息队列类
		private final RedisMQ redisMQ;
		// 新浪微博接口实现类
		private final SinaPublicWeibosSentiment sinaPublicWeibos;

		public SpiderRunnable(RedisMQ redisMQ, SinaPublicWeibosSentiment sinaPublicWeibos) {
			this.redisMQ = redisMQ;
			this.sinaPublicWeibos = sinaPublicWeibos;
		}

		@Override
		public void run() {
			spider(redisMQ, sinaPublicWeibos, COUNT);
		}

	}

	public static void spider(RedisMQ redisMQ, SinaPublicWeibosSentiment sinaPublicWeibos, final AtomicInteger COUNT) {
		SinaDomain weibos, weibo, user;
		List<String> records = new ArrayList<>();
		RecordInfo recordInfo;
		try {
			weibos = sinaPublicWeibos.getPublicWeibos(200);
			logger.info("Spider sina_public_weibos at:{}, weibos'size={}", COUNT.addAndGet(1),
					weibos.getFieldValues("statuses").size());
			for (Object status : weibos.getFieldValues("statuses")) {
				try {
					// 循环添加RecordInfo
					weibo = (SinaDomain) status;
					user = (SinaDomain) weibo.getFieldValue("user");
					recordInfo = new RecordInfo();
					recordInfo.setPlatform(3);
					recordInfo.setSource_id(7);
					recordInfo.setSource_name("新浪微博");
					recordInfo.setLocation_code(110000);
					recordInfo.setProvince_code(11);
					recordInfo.setFirst_time(System.currentTimeMillis());
					recordInfo.setUpdate_time(System.currentTimeMillis());
					recordInfo.setLasttime(System.currentTimeMillis());
					recordInfo.setIdentify_id(100L); // 表示本地
					recordInfo.setCountry_code(1);
					recordInfo.setIp("180.149.134.141");
					recordInfo.setLocation("北京市 电信集团公司");
					recordInfo.setSource_type(Integer.parseInt(weibo.getFieldValue("source_type").toString()));
					recordInfo.setTimestamp(getTime(weibo.getFieldValue("created_at").toString()));
					recordInfo.setId((CheckSumUtils.getMD5(WEIBO_BASE_URL + user.getFieldValue("id").toString() + "/"
							+ WidToMid.wid2mid(weibo.getFieldValue("id").toString()))).toUpperCase());
					recordInfo.setUsername(user.getFieldValue("id").toString());
					recordInfo.setNickname(user.getFieldValue("screen_name").toString());
					recordInfo.setUrl(WEIBO_BASE_URL + user.getFieldValue("id").toString() + "/"
							+ WidToMid.wid2mid(weibo.getFieldValue("id").toString()));
					recordInfo.setContent(weibo.getFieldValue("text").toString());
					// 增加图片地址列表
					recordInfo.setPic_url(getPicUrls(weibo));
					records.add(JsonUtils.toJsonWithoutPretty(recordInfo));
				} catch (Exception e) {
					logger.error("Exception:{}", LogbackUtil.expection2Str(e));
				}
			}
			redisMQ.addRecord(records.toArray(new String[records.size()]));
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

	@SuppressWarnings("unchecked")
	private static String getPicUrls(SinaDomain weibo) {
		if (weibo.getFieldValue("pic_urls").toString().length() < 10) {
			return "";
		}
		List<SinaDomain> pics = (List<SinaDomain>) weibo.getFieldValue("pic_urls");
		StringBuffer sb = new StringBuffer();
		for (SinaDomain pic : pics) {
			sb.append(pic.getFieldValue("thumbnail_pic").toString().replace("thumbnail", "bmiddle")).append(",");
		}
		return sb.substring(0, sb.length() - 1);
	}

	private static long getTime(String timeStr) {
		try {
			return FORMAT.parse(timeStr).getTime();
		} catch (ParseException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			// 如果时间解析错误，则使用当前时间
			return System.currentTimeMillis();
		}
	}

}
