package com.coe.message.entity;

import java.util.Date;

/**请求回调实体类*/
public class MessageCallback {
	private Long id;
	/**Message表主键id*/
	private Long messageId;
	/**异步回调地址*/
	private String callbackUrl;
	/**创建时间*/
	private Date createdTime;

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

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}