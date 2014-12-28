package zx.soft.sent.dao.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.log.LogbackUtil;

/**
 * Mybatis配置类
 * 
 * @author wanggang
 *
 */
public class MybatisConfig {

	private static Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

	public enum ServerEnum {
		sentiment
	}

	private static Map<ServerEnum, SqlSessionFactory> sessionFactorys = new HashMap<>();

	static {
		for (ServerEnum server : ServerEnum.values()) {
			try (InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");) {
				sessionFactorys.put(server, new SqlSessionFactoryBuilder().build(inputStream, server.name()));
			} catch (IOException e) {
				logger.error("Exception:{}", LogbackUtil.expection2Str(e));
				throw new RuntimeException(e);
			}
		}
	}

	public static SqlSessionFactory getSqlSessionFactory(ServerEnum server) {
		return sessionFactorys.get(server);
	}

}
