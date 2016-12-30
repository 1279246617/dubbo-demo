package com.coe.message.api.entity;

import com.coe.message.entity.Message;
import com.coe.message.entity.MessageCallback;
import com.coe.message.entity.MessageRequestWithBLOBs;

/**请求相关信息实体*/
public class RequestMsgEntity {
	/**Message，基础类*/
	private Message message;
	/**接口请求实体*/
	private MessageRequestWithBLOBs messageRequest;
	/**回调基础类*/
	private MessageCallback messageCallback;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public MessageRequestWithBLOBs getMessageRequest() {
		return messageRequest;
	}

	public void setMessageRequest(MessageRequestWithBLOBs messageRequest) {
		this.messageRequest = messageRequest;
	}

	public MessageCallback getMessageCallback() {
		return messageCallback;
	}

	public void setMessageCallback(MessageCallback messageCallback) {
		this.messageCallback = messageCallback;
	}

}
