package zx.soft.sent.dao.bdb;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.file.FileUtils;
import zx.soft.utils.log.LogbackUtil;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

/**
 * 基本的BDB数据库
 * 
 * @author wanggang
 *
 */
public class BasicDatabase {

	private static Logger logger = LoggerFactory.getLogger(BasicDatabase.class);

	private final Environment environment;
	private final DatabaseConfig dbConfig;

	public BasicDatabase(String envUrl) {
		// 目录不存在的话创建新目录
		if (FileUtils.isExisted(envUrl)) {
			FileUtils.createPath(envUrl);
		}
		// 打开环境
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(true);
		envConfig.setAllowCreate(true);
		environment = new Environment(new File(envUrl), envConfig);
		// 数据库配置
		dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		dbConfig.setTransactional(true);
		// 设置一个key是否允许存储多个值
		dbConfig.setSortedDuplicates(true);
	}

	/**
	 * 创建数据库，注意调用过后关闭数据库
	 */
	public Database createOrOpenDB(String dbName) {
		return environment.openDatabase(null, dbName, dbConfig);
	}

	/**
	 * 写数据
	 */
	public boolean insertDataUnique(Database db, String key, String value, int type) {
		try {
			DatabaseEntry keyEntry = new DatabaseEntry(key.getBytes("utf-8"));
			DatabaseEntry valEntry = new DatabaseEntry(value.getBytes("utf-8"));
			OperationStatus status = null;
			if (type == 0) {
				// Database.put()： 向数据库写入数据，如果不支持重复记录，则会覆盖更新key对应的已有记录 
				status = db.put(null, keyEntry, valEntry);
			} else if (type == 1) {
				// Database.putNoOverwrite():向数据库写入数据，但是如果key已经存在，不会覆盖已有数据（即使数据库支持重复key） 
				status = db.putNoOverwrite(null, keyEntry, valEntry);
			} else if (type == 2) {
				// Database.putNoDupData():向数据库写入数据（该方法仅用于支持重复key的数据库），
				// 如果key和value对应的记录已经存在，那么操作结果是：OperationStatus.KEYEXIST 
				status = db.putNoDupData(null, keyEntry, valEntry);
			} else {
				throw new RuntimeException("Param type=" + type + " is error.");
			}
			if (status == OperationStatus.SUCCESS) {
				return Boolean.TRUE;
			} else if (status == OperationStatus.KEYEXIST) {
				logger.info("putNoDupData KEYEXIST:" + key);
				return Boolean.TRUE;
			} else {
				logger.error("Insert '" + value + "' in '" + key + "' " + status);
				return Boolean.FALSE;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 读数据
	 */
	public String selectData(Database db, String key, int type) {
		try {
			DatabaseEntry value = new DatabaseEntry();
			OperationStatus status = null;
			if (type == 0) {
				// Database.get() ：检索key对应的记录  
				status = db.get(null, new DatabaseEntry(key.getBytes("utf-8")), value, LockMode.DEFAULT);
			} else if (type == 1) {
				// Database.getSearchBoth() ：根据key和value 检索数据库记录  
				status = db.getSearchBoth(null, new DatabaseEntry(key.getBytes("utf-8")), value, LockMode.DEFAULT);
			} else {
				throw new RuntimeException("Param type=" + type + " is error.");
			}
			if (status == OperationStatus.SUCCESS) {
				return new String(value.getData(), "utf-8");
			} else {
				throw new RuntimeException("Read data in '" + key + "' " + status);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 更新数据，保证更新
	 */
	public String updateData(Database db, String key, String value) {
		try {
			DatabaseEntry keyEntry = new DatabaseEntry(key.getBytes("utf-8"));
			DatabaseEntry valEntry = new DatabaseEntry(value.getBytes("utf-8"));
			OperationStatus status = db.put(null, keyEntry, valEntry);
			DatabaseEntry valueGet = new DatabaseEntry();
			status = db.get(null, keyEntry, valueGet, LockMode.DEFAULT);
			if (status == OperationStatus.SUCCESS) {
				return new String(valueGet.getData(), "utf-8");
			} else {
				throw new RuntimeException("Update '" + value + "' in '" + key + "' " + status);
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除数据
	 */
	public boolean deleteData(Database db, String key) {
		try {
			OperationStatus status = db.delete(null, new DatabaseEntry(key.getBytes("utf-8")));
			if (status == OperationStatus.SUCCESS) {
				return Boolean.TRUE;
			} else {
				logger.error("Read data in '" + key + "' " + status);
				return Boolean.FALSE;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception:{}", LogbackUtil.expection2Str(e));
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭资源
	 */
	public void close() {
		environment.sync();
		environment.cleanLog();
		environment.close();
	}

}
