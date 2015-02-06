package zx.soft.sent.spider.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.http.HttpClientDaoImpl;
import zx.soft.utils.log.LogbackUtil;
import zx.soft.weibo.core.weibos.SinaPublicWeibosSentiment;
import zx.soft.weibo.sina.domain.SinaDomain;

/**
 * 定时写XML数据
 *
 * @author wanggang
 *
 */
public class SinaPublicWeibosXML {

	private static Logger logger = LoggerFactory.getLogger(SinaPublicWeibosXML.class);

	// 新浪微博接口实现类
	private final SinaPublicWeibosSentiment sinaPublicWeibos;

	// 文件名类型
	private final String FILE_HEADER = "138-340000-";
	private final String FILE_TAIL = "-WACODE_0080-0.bcp";

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

	// 计数器
	private static final AtomicInteger COUNT = new AtomicInteger(0);

	public SinaPublicWeibosXML() {
		this.sinaPublicWeibos = new SinaPublicWeibosSentiment(new HttpClientDaoImpl());
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		SinaPublicWeibosXML weibosXML = new SinaPublicWeibosXML();
		weibosXML.writeWeibos(10, 10001);
	}

	/**
	 * 写微博数据
	 * @param N 请求次数
	 * @param fileCount 文件标号，5位数
	 */
	public void writeWeibos(int N, int fileCount) {
		List<SinaXMLInfo> weibos = getWeibos(N);
		String fileName = FILE_HEADER + System.currentTimeMillis() / 1000 + "-" + fileCount + FILE_TAIL;
		try (BufferedWriter br = new BufferedWriter(new FileWriter(new File(fileName)));) {
			for (SinaXMLInfo tmp : weibos) {
				br.write(tmp.getMBLOG_ID() + "\t\t" + tmp.getRELEVANT_USERID() + "\t\t"
						+ tmp.getRELEVANT_USER_NICKNAME() + "\t\t" + tmp.getWEIBO_MESSAGE() + "\t\t"
						+ tmp.getCOLLECT_PLACE() + "\t\t" + tmp.getFIRST_TIME() + "\t\t" + tmp.getLAST_TIME() + "\t\t"
						+ tmp.getDATA_SOURCE() + "\t\t" + tmp.getDOMAIN() + "\t\t" + tmp.getCAPTURE_TIME());
				br.newLine();
			}
		} catch (IOException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		}
	}

	public List<SinaXMLInfo> getWeibos(int N) {
		SinaDomain weibos, weibo, user;
		List<SinaXMLInfo> result = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			try {
				weibos = sinaPublicWeibos.getPublicWeibos(200);
				logger.info("Spider sina_public_weibos at:{}, weibos'size={}", COUNT.addAndGet(1), weibos
						.getFieldValues("statuses").size());
				for (Object status : weibos.getFieldValues("statuses")) {
					// 循环添加RecordInfo
					weibo = (SinaDomain) status;
					user = (SinaDomain) weibo.getFieldValue("user");
					SinaXMLInfo sinaXMLInfo = new SinaXMLInfo();
					// 微博ID
					sinaXMLInfo.setMBLOG_ID(weibo.getFieldValue("id").toString());
					// 相关人ID
					sinaXMLInfo.setRELEVANT_USERID(user.getFieldValue("id").toString());
					// 相关人昵称
					sinaXMLInfo.setRELEVANT_USER_NICKNAME(user.getFieldValue("screen_name").toString());
					// 微博消息内容
					sinaXMLInfo.setWEIBO_MESSAGE(weibo.getFieldValue("text").toString());
					// 采集地
					sinaXMLInfo.setCOLLECT_PLACE("北京市 电信集团公司");
					// 首次采集时间
					sinaXMLInfo.setFIRST_TIME(System.currentTimeMillis() / 1000);
					// 最近采集时间
					sinaXMLInfo.setLAST_TIME(System.currentTimeMillis() / 1000);
					// 数据来源名称
					sinaXMLInfo.setDATA_SOURCE("新浪微博");
					// 域名
					sinaXMLInfo.setDOMAIN("weibo.com");
					// 数据截获时间
					sinaXMLInfo.setCAPTURE_TIME(getTime(weibo.getFieldValue("created_at").toString()));
					result.add(sinaXMLInfo);
				}
			} catch (Exception e) {
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			}
		}
		return result;
	}

	private long getTime(String timeStr) {
		try {
			return FORMAT.parse(timeStr).getTime() / 1000;
		} catch (ParseException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			// 如果时间解析错误，则使用当前时间
			return System.currentTimeMillis();
		}
	}

}
