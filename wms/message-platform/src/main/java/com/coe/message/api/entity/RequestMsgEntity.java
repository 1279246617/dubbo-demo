package com.coe.message.api.entity;

import java.io.Serializable;
import com.coe.message.entity.Message;
import com.coe.message.entity.MessageRequestWithBLOBs;

/**请求相关信息实体*/
@SuppressWarnings("serial")
public class RequestMsgEntity implements Serializable{
	/**Message，基础类*/
	private Message message;
	/**接口请求实体*/
	private MessageRequestWithBLOBs msgReqWithBlobs;
	

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
}
