package com.coe.message.entity;

/**接口请求实体继承类*/
public class MessageRequestWithBLOBs extends MessageRequest {
	
	/**接口请求body*/
	private String body;
	/**头部请求信息*/
	private String headerParams;
	/**请求参数主体*/
	private String bodyParams;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHeaderParams() {
		return headerParams;
	}

	public void setHeaderParams(String headerParams) {
		this.headerParams = headerParams;
	}

	public String getBodyParams() {
		return bodyParams;
	}

	public void setBodyParams(String bodyParams) {
		this.bodyParams = bodyParams;
	}

}