package zx.soft.sent.solr.ecxception;

/**
 * 舆情搜索异常处理
 * 
 * @author wanggang
 *
 */
public class SpiderSearchException extends RuntimeException {

	private static final long serialVersionUID = 4719608449546004181L;

	public SpiderSearchException(String message, Throwable cause) {
		super(message, cause);
	}

	public SpiderSearchException(String message) {
		super(message);
	}

	public SpiderSearchException(Throwable cause) {
		super(cause);
	}

	public Throwable getRootCause() {
		Throwable t = this;
		while (true) {
			Throwable cause = t.getCause();
			if (cause != null) {
				t = cause;
			} else {
				break;
			}
		}
		return t;
	}

}
