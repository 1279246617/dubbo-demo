package com.coe.message.api.entity;

import java.io.Serializable;

import com.coe.message.entity.Message;
import com.coe.message.entity.MessageCallback;
import com.coe.message.entity.MessageRequestWithBLOBs;

/**请求相关信息实体*/
@SuppressWarnings("serial")
public class RequestMsgEntity implements Serializable{
	/**Message，基础类*/
	private Message message;
	/**接口请求实体*/
	private MessageRequestWithBLOBs msgReqWithBlobs;
	/**回调基础类*/
	private MessageCallback msgCallback;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public MessageRequestWithBLOBs getMsgReqWithBlobs() {
		return msgReqWithBlobs;
	}

	public void setMsgReqWithBlobs(MessageRequestWithBLOBs msgReqWithBlobs) {
		this.msgReqWithBlobs = msgReqWithBlobs;
	}

	public MessageCallback getMsgCallback() {
		return msgCallback;
	}

	public void setMsgCallback(MessageCallback msgCallback) {
		this.msgCallback = msgCallback;
	}

}
