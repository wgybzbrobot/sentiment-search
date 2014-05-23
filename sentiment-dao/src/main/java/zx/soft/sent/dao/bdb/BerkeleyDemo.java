package zx.soft.sent.dao.bdb;

import java.io.File;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public class BerkeleyDemo {

	public static void main(String[] args) {

		// 打开database环境
		EnvironmentConfig envConfig = new EnvironmentConfig();
		//当环境不存在的时候自动创建环境
		envConfig.setAllowCreate(true);
		//设置支持事务
		envConfig.setTransactional(true);
		Environment myDbEnvironment = new Environment(new File("bdb/dbEnv"), envConfig);

		// 打开一个数据库，如果数据库不存在则创建一个
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		//打开一个数据库，数据库名为sampleDatabase,数据库的配置为dbConfig
		Database myDatabase = myDbEnvironment.openDatabase(null, "sampleDatabase", dbConfig);

		// 删除当前环境中指定的数据库 
		String dbName = myDatabase.getDatabaseName();
		myDbEnvironment.removeDatabase(null, dbName);
		// 给当前环境下的数据库改名
		String oldName = myDatabase.getDatabaseName();
		myDbEnvironment.renameDatabase(null, oldName, oldName + ".new");
		//		清空database内的所有数据，返回清空了多少条记录
		long numDiscarded = myDbEnvironment.truncateDatabase(null, myDatabase.getDatabaseName(), true);
		System.out.println("一共删除了 " + numDiscarded + " 条记录 从数据库 " + myDatabase.getDatabaseName());

		// 关闭database环境
		if (myDatabase != null) {
			myDatabase.close();
		}
		if (myDbEnvironment != null) {
			myDbEnvironment.cleanLog(); // 在关闭环境前清理下日志
			myDbEnvironment.close();
		}

	}
}
