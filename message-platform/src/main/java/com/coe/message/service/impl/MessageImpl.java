package com.coe.message.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coe.message.dao.mapper.MessageCallbackMapper;
import com.coe.message.dao.mapper.MessageMapper;
import com.coe.message.dao.mapper.MessageRequestMapper;
import com.coe.message.entity.Message;
import com.coe.message.entity.MessageCallback;
import com.coe.message.entity.MessageExample;
import com.coe.message.entity.MessageExample.Criteria;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.coe.message.service.IMessage;

/**报文信息接口实现类*/
@Service("messageImpl")
public class MessageImpl implements IMessage {
	@Autowired
	private MessageMapper msgMapper;
	@Autowired
	private MessageRequestMapper msgReqMapper;
	@Autowired
	private MessageCallbackMapper msgBackMapper;

	/**保存Message,返回主键id*/
	public Long saveMessageBackId(Message message) {
		msgMapper.insertBackId(message);
		return message.getId();
	}

	/**保存Message,MessageRequestWithBLOBs,MessageCallback报文相关请求信息
	 * @throws Exception */
	public boolean saveMessageAll(Message message, MessageRequestWithBLOBs msgRequestWithBlobs, MessageCallback msgCallback) throws Exception {
		boolean flag = false;
		// 保存Message,获取id
		int result1 = msgMapper.insertBackId(message);
		Long messageId = message.getId();
		msgRequestWithBlobs.setMessageId(messageId);
		msgCallback.setMessageId(messageId);
		// 保存MessageRequestWithBLOBs
		int result2 = msgReqMapper.insert(msgRequestWithBlobs);
		// 保存MessageCallback
		int result3 = msgBackMapper.insert(msgCallback);
		if (result1 == 1 && result2 == 1 && result3 == 1) {
			flag = true;
		}else{
			throw new Exception();
		}
		return flag;
	}
	/**根据模板查询*/
	public List<Message> selectByExample(Message message) {
		int count = message.getCount();
		Long id = message.getId();
		String keyword = message.getKeyword();
		String requestId = message.getRequestId();
		int status = message.getStatus();
		String keyword1 = message.getKeyword1();
		String keyword2 = message.getKeyword2();
		MessageExample msgExample = new MessageExample();
		Criteria messageCriteria = msgExample.createCriteria();
		messageCriteria.andCountEqualTo(count);
		messageCriteria.andIdEqualTo(id);
		messageCriteria.andKeywordEqualTo(keyword);
		messageCriteria.andKeyword1EqualTo(keyword1);
		messageCriteria.andKeyword2EqualTo(keyword2);
		messageCriteria.andStatusEqualTo(status);
		messageCriteria.andRequestIdEqualTo(requestId);
		return msgMapper.selectByExample(msgExample);
	}

}
