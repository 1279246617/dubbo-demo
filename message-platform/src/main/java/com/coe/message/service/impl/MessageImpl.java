package com.coe.message.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coe.message.dao.mapper.MessageMapper;
import com.coe.message.dao.mapper.MessageRequestMapper;
import com.coe.message.entity.Message;
import com.coe.message.entity.MessageExample;
import com.coe.message.entity.MessageExample.Criteria;
import com.coe.message.entity.MessageRequest;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.coe.message.service.IMessage;

/**报文信息接口实现类*/
@Service
public class MessageImpl implements IMessage {
	@Autowired
	private MessageMapper msgMapper;
	@Autowired
	private MessageRequestMapper msgReqMapper;

	public int updateOrSave(Message msg) {
		Long id = msg.getId();
		if (id != null) {
			return msgMapper.updateByPrimaryKeySelective(msg);
		} else {
			return msgMapper.insertSelective(msg);
		}
	}

	/**
	 * 批量更新Count字段
	 * @param idList Message主键id列表
	 */
	public int updateCountBatchForApi(List<Long> idList) {
		return msgMapper.updateCountBatchForApi(idList);
	}

	/**
	 * 分页查询(For API)
	 * @param records 总记录数
	 */
	public List<Message> queryPageForApi(int records) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("records", records);
		return msgMapper.queryPageForApi(paramsMap);
	}

	/**保存Message,返回主键id*/
	public Long saveMessageBackId(Message message) {
		msgMapper.insertBackId(message);
		return message.getId();
	}

	/**保存Message,MessageRequestWithBLOBs报文相关请求信息
	 * @throws Exception */
	public boolean saveMessageAll(Message message, MessageRequestWithBLOBs msgRequestWithBlobs) throws Exception {
		boolean flag = false;
		// 保存Message,获取id
		int result1 = msgMapper.insertBackId(message);
		Long messageId = message.getId();
		msgRequestWithBlobs.setMessageId(messageId);
		// 保存MessageRequestWithBLOBs
		int result2 = msgReqMapper.insert(msgRequestWithBlobs);
		// 保存MessageCallback
		if (result1 == 1 && result2 == 1) {
			flag = true;
		} else {
			throw new Exception();
		}
		return flag;
	}

	/**根据模板查询*/
	public List<Message> selectByExample(Message message) {
		Integer count = message.getCount();
		Long id = message.getId();
		String keyword = message.getKeyword();
		String keyword1 = message.getKeyword1();
		String keyword2 = message.getKeyword2();
		String requestId = message.getRequestId();
		Integer status = message.getStatus();
		MessageExample msgExample = new MessageExample();
		Criteria messageCriteria = msgExample.createCriteria();
		if (count != null) {
			messageCriteria.andCountEqualTo(count);
		}
		if (id != null) {
			messageCriteria.andIdEqualTo(id);
		}
		if (status != null) {
			messageCriteria.andStatusEqualTo(status);
		}
		if (StringUtils.isNotBlank(requestId)) {
			messageCriteria.andRequestIdEqualTo(requestId);
		}
		if (StringUtils.isNotBlank(keyword)) {
			messageCriteria.andKeywordEqualTo(keyword);
		}
		if (StringUtils.isNotBlank(keyword1)) {
			messageCriteria.andKeyword1EqualTo(keyword1);
		}
		if (StringUtils.isNotBlank(keyword2)) {
			messageCriteria.andKeyword2EqualTo(keyword2);
		}
		return msgMapper.selectByExample(msgExample);
	}
}
