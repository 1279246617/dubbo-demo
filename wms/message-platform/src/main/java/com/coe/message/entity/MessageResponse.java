package com.coe.message.entity;

import java.util.Date;

/**接口响应实体类*/
public class MessageResponse {
	private Long id;
	/**Message主键id*/
	private Long messageId;
	/**http状态 如:200 ,404,500*/
	private Integer httpStatus;
	/**发送起始时间*/
	private Long sendBeginTime;
	/**发送结束时间*/
	private Long sendEndTime;
	/**历时多少毫秒*/
	private Long usedTime;
	/**枚举状态:1=成功,2:连接超时 3:响应超时 4:对方报错(状态不等于200)*/
	private Integer status;
	/**状态描述*/
	private String statusDesc;
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

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Long getSendBeginTime() {
		return sendBeginTime;
	}

	public void setSendBeginTime(Long sendBeginTime) {
		this.sendBeginTime = sendBeginTime;
	}

	public Long getSendEndTime() {
		return sendEndTime;
	}

	public void setSendEndTime(Long sendEndTime) {
		this.sendEndTime = sendEndTime;
	}

	public Long getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}