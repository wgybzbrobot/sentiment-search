package zx.soft.sent.web.domain;

import java.util.ArrayList;
import java.util.List;

import zx.soft.sent.utils.json.JsonUtils;

public class IndexErrResponse {

	private int errorCode;
	private List<String> errorMessage;

	public IndexErrResponse(int errorCode, List<String> errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public List<String> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(List<String> errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static void main(String[] args) {
		List<String> errorMessage = new ArrayList<>();
		errorMessage.add("wwfffrrrrr");
		errorMessage.add("1233jjffioo");
		IndexErrResponse err = new IndexErrResponse(-1, errorMessage);
		System.out.println(JsonUtils.toJson(err));
	}

}
