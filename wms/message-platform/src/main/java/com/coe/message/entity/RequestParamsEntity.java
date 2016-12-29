package com.coe.message.entity;

/**请求参数实体类*/
public class RequestParamsEntity {
	/**请求体*/
	private String body;
	/**请求参数*/
	private String params;
	/**请求头信息*/
	private String headers;
	/**请求地址(非空)*/
	private String url;
	/**请求方法，1：get,2:post(非空)*/
	private int method;
	/**请求id,长度36的UUID,不可重复(非空)*/
	private String requestId;
	/**用于搜索报文的关键字 一般如:订单号,跟踪单号,订单id,客户id之类(类似关键字最多可传3个，非空)*/
	private String keyword;
	/**用于搜索报文的关键字 一般如:订单号,跟踪单号,订单id,客户id之类(类似关键字最多可传3个)*/
	private String keyword1;
	/**用于搜索报文的关键字 一般如:订单号,跟踪单号,订单id,客户id之类(类似关键字最多可传3个)*/
	private String keyword2;
	/**回调地址(非空)*/
	private String callbackUrl;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

}
