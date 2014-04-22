package zx.soft.sent.utils.retry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Proxy;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class RetryHandlerTest {

	class Bar implements Foo {

		int errorTimes;

		@Override
		public int fun() {
			// OK
			return 1;
		}

		@Override
		public int funError() {
			throw new RuntimeException("Sorry");
		}

		@Override
		public int funError2Times() {
			if (++errorTimes > 2) {
				return 2;
			}
			throw new RuntimeException("Sorry");
		}

	}

	interface Foo {
		int fun();

		int funError();

		int funError2Times();
	}

	private Foo foo;

	@Before
	public void setUp() {
		foo = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(), new Class[] { Foo.class },
				new RetryHandler<Foo>(new Bar(), 100, 3) {
					@Override
					protected boolean isRetry(Throwable e) {
						return e instanceof RuntimeException;
					}
				});
	}

	@Test
	public void test() {

		assertEquals(1, foo.fun());
	}

	@Test(expected = RuntimeException.class)
	public void testDiffException() {
		foo = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(), new Class[] { Foo.class },
				new RetryHandler<Foo>(new Bar(), 100, 3) {
					@Override
					protected boolean isRetry(Throwable e) {
						return e instanceof SQLException;
					}
				});
		foo.funError2Times();
	}

	@Test
	public void testReplayFail() {
		long start = System.currentTimeMillis();
		try {
			foo.funError();
			fail();
		} catch (RuntimeException e) {
			assertEquals("Sorry", e.getMessage());
		}
		long duration = System.currentTimeMillis() - start;
		assertTrue(300 <= duration && duration <= 400);
	}

	@Test
	public void testReplaySucceed() {
		long start = System.currentTimeMillis();
		assertEquals(2, foo.funError2Times());
		long duration = System.currentTimeMillis() - start;
		assertTrue(200 <= duration && duration <= 300);
	}

}
