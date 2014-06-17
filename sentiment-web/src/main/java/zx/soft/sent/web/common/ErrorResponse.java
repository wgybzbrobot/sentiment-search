package zx.soft.sent.web.common;

/**
 * 错误相应类
 * 
 * @author wanggang
 *
 */
public class ErrorResponse {

	private final int errorCode;
	private final String ErrorMessage;

	public ErrorResponse(Builder builder) {
		this.errorCode = builder.errorCode;
		this.ErrorMessage = builder.ErrorMessage;
	}

	public static class Builder {

		private final int errorCode;
		private final String ErrorMessage;

		public Builder(int errorCode, String errorMessage) {
			super();
			this.errorCode = errorCode;
			ErrorMessage = errorMessage;
		}

		public ErrorResponse build() {
			return new ErrorResponse(this);
		}

	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

}
