package zx.soft.sent.core.demo;

public class CoreDemo {

	public static void main(String[] args) {
		try {
			String[] a = { "aa", "bb" };
			for (int i = 0; i < 3; i++) {
				System.out.println(a[i]);
			}
		} catch (Exception e) {
			throw new RuntimeException("test\ntest data");
		}

	}

}
