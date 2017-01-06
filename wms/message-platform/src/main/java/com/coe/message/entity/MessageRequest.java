package com.coe.message.entity;

import java.util.Date;

/**接口请求实体类*/
public class MessageRequest {
	private Long id;
	/**Message主键id*/
	private Long messageId;
	/**请求地址*/
	private String url;
	/**请求方式，1:get,2:post*/
	private Integer method;
	/**创建时间*/
	private Date createdTime;
	/**数据读取超时时间(毫秒)*/
	private Integer soTimeOut;
	/**连接超时时间(毫秒)*/
	private Integer connectionTimeOut;
	/**回调地址*/
	private String callbackUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
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

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getSoTimeOut() {
		return soTimeOut;
	}

	public void setSoTimeOut(int soTimeOut) {
		this.soTimeOut = soTimeOut;
	}

	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
}