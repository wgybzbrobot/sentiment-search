package zx.soft.spider.solr.search;

/**
 * 查询结果类
 * @author wanggang
 *
 */
public class QueryResult {

	private int errorCode;
	private String errorMessage;
	private ResponseData responseData;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

}
