package zx.soft.spider.web.sentiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.spider.solr.domain.AutmSearch;
import zx.soft.spider.solr.domain.Blog;
import zx.soft.spider.solr.domain.Email;
import zx.soft.spider.solr.domain.Forum;
import zx.soft.spider.solr.domain.Information;
import zx.soft.spider.solr.domain.QQGroup;
import zx.soft.spider.solr.domain.Reply;
import zx.soft.spider.solr.domain.Weibo;
import zx.soft.spider.solr.index.IndexCloudSolr;
import zx.soft.spider.solr.utils.ConvertToRecord;

/**
 * 索引舆情数据到Solr集群中
 * @author wanggang
 *
 */
public class IndexingData {

	private static Logger logger = LoggerFactory.getLogger(IndexingData.class);

	private static IndexCloudSolr indexCloudSolr;

	public IndexingData() {
		indexCloudSolr = new IndexCloudSolr();
	}

	/**
	 * 根据数据类别进行索引
	 */
	public void addData(int platform, Object data) {
		switch (platform) {
		case 1: // 1：Information——>Record
			indexCloudSolr.addSentimentDocToSolr(ConvertToRecord.informationToRecord((Information) data));
		case 2: // 2：Forum——>Record
			indexCloudSolr.addSentimentDocToSolr(ConvertToRecord.forumToRecord((Forum) data));
		case 3: // 3：Weibo——>Record
			indexCloudSolr.addSentimentDocToSolr(ConvertToRecord.weiboToRecord((Weibo) data));
		case 4: // 4：Blog——>Record
			indexCloudSolr.addSentimentDocToSolr(ConvertToRecord.blogToRecord((Blog) data));
		case 5: // 5：QQGroup——>Record
			indexCloudSolr.addSentimentDocToSolr(ConvertToRecord.qQGroupToRecord((QQGroup) data));
		case 6: // 6：AutmSearch——>Record
			indexCloudSolr.addSentimentDocToSolr(ConvertToRecord.autmSearchToRecord((AutmSearch) data));
		case 7: // 7：Reply——>Record
			indexCloudSolr.addSentimentDocToSolr(ConvertToRecord.replyToRecord((Reply) data));
		case 8: // 8：Email——>Record
			indexCloudSolr.addSentimentDocToSolr(ConvertToRecord.emailToRecord((Email) data));
		default:
			logger.error("Indexed data's platform=" + platform + " is error!");
			return;
		}
	}

	public void commit() {
		indexCloudSolr.commitToSolr();
	}

	public void close() {
		indexCloudSolr.close();
	}

}
