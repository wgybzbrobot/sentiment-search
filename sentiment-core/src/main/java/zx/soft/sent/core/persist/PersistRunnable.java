package zx.soft.sent.core.persist;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.redis.client.cache.Cache;
import zx.soft.sent.dao.domain.platform.RecordInfo;
import zx.soft.sent.dao.domain.sentiment.RecordInsert;
import zx.soft.sent.dao.sentiment.SentimentRecord;
import zx.soft.sent.dao.sql.CreateTables;
import zx.soft.utils.checksum.CheckSumUtils;
import zx.soft.utils.log.LogbackUtil;

/**
 * 持久化线程类
 * 
 * @author wanggang
 *
 */
public class PersistRunnable implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(PersistRunnable.class);

	private final SentimentRecord sentRecord;

	private final RecordInfo record;

	private final Cache cache;

	public static final String SENT_KEY_INSERTED = "sent:key:inserted";

	public PersistRunnable(final Cache cache, SentimentRecord sentRecord, RecordInfo record) {
		if (record == null) {
			logger.error("Record is null.");
			//			throw new IllegalArgumentException("record is null");
		}
		this.cache = cache;
		this.sentRecord = sentRecord;
		this.record = record;
	}

	@Override
	public synchronized void run() {

		try {
			RecordInsert tRecord = transRecord(record);
			// 记录存在的情况下
			if (cache.sismember(SENT_KEY_INSERTED, record.getId())) {
				// 更新的时候存在线程安全，但是问题不太大
				// 暂时不做更新
				//				try {
				//					logger.info("Update Record:{}", record.getId());
				//					sentRecord.updateRecord(tRecord);
				//				} catch (Exception e) {
				//					logger.error("Exception:{}", LogbackUtil.expection2Str(e));
				//				}
				// 记录不存在的情况下
			} else {
				// 下面两句顺序不可改变，否则会导致线程安全
				cache.sadd(SENT_KEY_INSERTED, record.getId());
				try {
					logger.info("Insert Record:{}", record.getId());
					sentRecord.insertRecord(tRecord);
				} catch (Exception e) {
					logger.error("Exception:{}", LogbackUtil.expection2Str(e));
				}
			}
		} catch (Exception e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
		}

	}

	private RecordInsert transRecord(RecordInfo record) {
		return new RecordInsert.Builder(CreateTables.SENT_TABLE + CheckSumUtils.getCRC32(record.getId())
				% CreateTables.MAX_TABLE_NUM, record.getId(), record.getPlatform()).setMid(record.getMid())
				.setUsername(record.getUsername()).setNickname(record.getNickname())
				.setOriginal_id(record.getOriginal_id()).setOriginal_uid(record.getOriginal_uid())
				.setOriginal_name(record.getOriginal_name()).setOriginal_title(record.getOriginal_title())
				.setOriginal_url(record.getOriginal_url()).setUrl(record.getUrl()).setHome_url(record.getHome_url())
				.setTitle(record.getTitle()).setType(record.getType()).setContent(record.getContent())
				.setComment_count(record.getComment_count()).setRead_count(record.getRead_count())
				.setFavorite_count(record.getFavorite_count()).setAttitude_count(record.getAttitude_count())
				.setRepost_count(record.getRepost_count()).setVideo_url(record.getVideo_url())
				.setPic_url(record.getPic_url()).setVoice_url(record.getVoice_url())
				.setTimestamp(new Date(record.getTimestamp())).setSource_id(record.getSource_id())
				.setLasttime(new Date(record.getLasttime())).setServer_id(record.getServer_id())
				.setIdentify_id(record.getIdentify_id()).setIdentify_md5(record.getIdentify_md5())
				.setKeyword(record.getKeyword()).setFirst_time(new Date(record.getFirst_time()))
				.setUpdate_time(new Date(record.getUpdate_time())).setIp(record.getIp())
				.setLocation(record.getLocation()).setGeo(record.getGeo()).setReceive_addr(record.getReceive_addr())
				.setAppend_addr(record.getAppend_addr()).setSend_addr(record.getSend_addr())
				.setSource_name(record.getSource_name()).setSource_type(record.getSource_type())
				.setCountry_code(record.getCountry_code()).setLocation_code(record.getLocation_code())
				.setProvince_code(record.getProvince_code()).setCity_code(record.getCity_code()).build();
	}

}
