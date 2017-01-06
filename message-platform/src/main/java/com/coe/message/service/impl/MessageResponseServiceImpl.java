package com.coe.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coe.message.dao.mapper.MessageResponseMapper;
import com.coe.message.entity.MessageResponseWithBLOBs;
import com.coe.message.service.IMessageResponseService;

/**接口请求响应接口实现类*/
@Service
public class MessageResponseServiceImpl implements IMessageResponseService {
	@Autowired
	private MessageResponseMapper msgResponseMapper;

	/**更新或保存MessageResponse*/
	public int saveOrUpdate(MessageResponseWithBLOBs msgResponse) {
		Long id = msgResponse.getId();
		if (id != null) {
			return msgResponseMapper.updateByPrimaryKeySelective(msgResponse);
		} else {
			return msgResponseMapper.insertSelective(msgResponse);
		}
	}

}
