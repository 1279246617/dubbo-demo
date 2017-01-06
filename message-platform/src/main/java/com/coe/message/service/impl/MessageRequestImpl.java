package com.coe.message.service.impl;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coe.message.dao.mapper.MessageRequestMapper;
import com.coe.message.entity.MessageRequest;
import com.coe.message.entity.MessageRequestExample;
import com.coe.message.entity.MessageRequestExample.Criteria;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.coe.message.service.IMessageRequest;

/**报文请求信息接口实现类*/
@Service
public class MessageRequestImpl implements IMessageRequest {
	@Autowired
	private MessageRequestMapper msgReqMapper;

	/**根据模板查询*/
	public List<MessageRequestWithBLOBs> selectByExample(MessageRequest msgReq) {
		MessageRequestExample example = new MessageRequestExample();
		Criteria criteria = example.createCriteria();
		String callbackUrl = msgReq.getCallbackUrl();
		Long messageId = msgReq.getMessageId();
		Integer method = msgReq.getMethod();
		String url = msgReq.getUrl();
		Long id = msgReq.getId();
		if (messageId != null) {
			criteria.andMessageIdEqualTo(messageId);
		}
		if (method != null) {
			criteria.andMethodEqualTo(method);
		}
		if (id != null) {
			criteria.andIdEqualTo(id);
		}
		if (StringUtils.isNotBlank(url)) {
			criteria.andUrlEqualTo(url);
		}
		if (StringUtils.isNotBlank(callbackUrl)) {
			criteria.andCallbackUrlEqualTo(callbackUrl);
		}
		return msgReqMapper.selectByExampleWithBLOBs(example);
	}

}
