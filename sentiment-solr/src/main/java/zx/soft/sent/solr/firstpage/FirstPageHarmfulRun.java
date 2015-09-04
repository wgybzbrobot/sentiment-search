package zx.soft.sent.solr.firstpage;

import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.negative.sentiment.core.NegativeClassify;
import zx.soft.sent.dao.common.MybatisConfig;
import zx.soft.sent.dao.firstpage.FirstPageHarmful;
import zx.soft.utils.json.JsonUtils;
import zx.soft.utils.log.LogbackUtil;

/**
 * OA首页有害负面信息定时分析：hefei07
 *
 * 运行目录：/home/zxdfs/run-work/timer/oa-firstpage-harmful
 * 运行命令：./firstpage_harmful_timer.sh &
 *
 * @author wanggang
 *
 */
public class FirstPageHarmfulRun {

	private static Logger logger = LoggerFactory.getLogger(FirstPageHarmfulRun.class);

	public FirstPageHarmfulRun() {
		super();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		FirstPageHarmfulRun fphr = new FirstPageHarmfulRun();
		try {
			fphr.run();
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	public void run() {
		logger.info("Starting query OA-FirstPage-Harmful data...");
		FirstPageHarmful firstPage = new FirstPageHarmful(MybatisConfig.ServerEnum.sentiment);
		OAFirstPage oafirstPage = new OAFirstPage();
		NegativeClassify negativeClassify = new NegativeClassify();
		/**
		 * 对当天的各平台进入数据进行负面评分，并按照分值推送最大的签20条内容，每小时推送一次。
		 */
		/*for (int i = 1; i < SentimentConstant.PLATFORM_ARRAY.length; i++) {
			logger.info("Retriving platform:{}", i);
			List<SolrDocument> negativeRecordsForum = oafirstPage.getNegativeRecords(i, 0, 30);
			negativeRecordsForum = FirstPageRun.getTopNNegativeRecords(negativeClassify, negativeRecordsForum, 50);
			firstPage.insertFirstPage(i, FirstPageRun.timeStrByHour(),
					JsonUtils.toJsonWithoutPretty(negativeRecordsForum));
		}*/

		List<SolrDocument> negativeRecordsForum = oafirstPage.getHarmfulRecords("1,2,3,4,7,10", 0, 30);
		negativeRecordsForum = FirstPageRun.getTopNNegativeRecords(negativeClassify, negativeRecordsForum, 50);
		firstPage
				.insertFirstPage(0, FirstPageRun.timeStrByHour(0), JsonUtils.toJsonWithoutPretty(negativeRecordsForum));
		//		System.out.println(JsonUtils.toJson(negativeRecordsForum));

		// 关闭资源
		negativeClassify.cleanup();
		oafirstPage.close();
		logger.info("Finishing query OA-FirstPage-Harmful data...");
	}

}
