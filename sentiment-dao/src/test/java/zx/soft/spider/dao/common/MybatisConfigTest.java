package zx.soft.spider.dao.common;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Ignore;

public class MybatisConfigTest {

	@Ignore
	public void testGetConfig() {
		SqlSessionFactory sqlSessionFactory = MybatisConfig.getSqlSessionFactory(MybatisConfig.ServerEnum.local);
		assertNotNull(sqlSessionFactory);
		assertTrue(sqlSessionFactory.getConfiguration().getEnvironment().getId().length() > 0);
	}

}
