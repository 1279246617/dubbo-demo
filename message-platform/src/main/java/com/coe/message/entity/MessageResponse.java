package com.coe.message.entity;

/**接口响应实体类*/
public class MessageResponse {
	private Long id;
	/**Message主键id*/
	private Long messageId;
	/**http状态 如:200 ,404,500(负数为自定义),-1:响应超时，-2：链接超时，-3：其他异常*/
	private Integer httpStatus;
	/**http状态信息*/
	private String httpStatusMsg;
	/**发送起始时间*/
	private Long sendBeginTime;
	/**发送结束时间*/
	private Long sendEndTime;
	/**历时多少毫秒*/
	private Long usedTime;
	/**枚举状态*/
	private Integer status;
	/**状态描述*/
	private String statusDesc;
	/**创建时间*/
	private Long createdTime;

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

	public String getHttpStatusMsg() {
		return httpStatusMsg;
	}

	public void setHttpStatusMsg(String httpStatusMsg) {
		this.httpStatusMsg = httpStatusMsg;
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

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

}