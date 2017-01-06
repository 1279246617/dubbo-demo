package com.coe.message.api.entity;

import java.io.Serializable;

/**请求参数实体类*/
@SuppressWarnings("serial")
public class RequestParamsEntity implements Serializable {
	/**接口请求body*/
	private String body;
	/**头部请求信息*/
	private String headerParams;
	/**请求参数主体*/
	private String bodyParams;
	/**数据传输超时时间(毫秒)*/
	private Integer soTimeOut;
	/**连接超时时间(毫秒)*/
	private Integer connectionTimeOut;
	/**请求地址(非空)*/
	private String url;
	/**请求方法，1：get,2:post(非空)*/
	private Integer method;
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

	public Integer getSoTimeOut() {
		return soTimeOut;
	}

	public void setSoTimeOut(Integer soTimeOut) {
		this.soTimeOut = soTimeOut;
	}

	public Integer getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(Integer connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
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
