package com.coe.message.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coe.message.entity.Message;
import com.coe.message.entity.MessageCallback;
import com.coe.message.entity.MessageRequestWithBLOBs;

/**报文信息接口*/
public interface IMessage {
	/**根据模板查询*/
	public List<Message> selectByExample(Message message);
	/**保存Message*/
	public Long saveMessageBackId(Message message);
	/**保存Message,MessageRequestWithBLOBs,MessageCallback报文相关请求信息*/
	public boolean saveMessageAll(Message message,MessageRequestWithBLOBs msgRequestWithBlobs,MessageCallback msgCallback) throws Exception;
}
