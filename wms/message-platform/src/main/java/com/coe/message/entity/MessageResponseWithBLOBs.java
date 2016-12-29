package com.coe.message.entity;

/**接口响应实体继承类*/
public class MessageResponseWithBLOBs extends MessageResponse {
	/**响应头信息*/
	private String responseHeader;
	/**响应主体内容*/
	private String responseBody;

	public String getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(String responseHeader) {
		this.responseHeader = responseHeader;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

}