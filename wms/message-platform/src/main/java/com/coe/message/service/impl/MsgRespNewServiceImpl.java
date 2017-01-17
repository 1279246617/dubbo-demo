package com.coe.message.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coe.message.common.BeanMapUtil;
import com.coe.message.common.DateUtil;
import com.coe.message.dao.mapper.MessageResponseNewestMapper;
import com.coe.message.entity.MessageResponseNewest;
import com.coe.message.entity.MessageResponseNewestExample;
import com.coe.message.entity.MessageResponseNewestExample.Criteria;
import com.coe.message.entity.MessageResponseNewestWithBLOBs;
import com.coe.message.service.IMsgRespNewService;

/**当前最新一次响应信息接口实现类*/
@Service
public class MsgRespNewServiceImpl implements IMsgRespNewService {
	@Autowired
	private MessageResponseNewestMapper msgRespNewestMapper;

	/**根据样例查询*/
	public List<MessageResponseNewestWithBLOBs> selectByExampleBackList(MessageResponseNewest msgReqNewest) {
		MessageResponseNewestExample example = generateExample(msgReqNewest);
		return msgRespNewestMapper.selectByExampleWithBLOBs(example);
	}

	/**根据样例查询*/
	public List<Map<String, Object>> selectByExampleBackMap(MessageResponseNewest msgReqNewest) {
		List<MessageResponseNewestWithBLOBs> msgRespNewList = selectByExampleBackList(msgReqNewest);
		return objListToMapList(msgRespNewList);
	}

	/**实体转Map*/
	public List<Map<String, Object>> objListToMapList(List<MessageResponseNewestWithBLOBs> msgRespNewList) {
		List<Map<String, Object>> msgRespNewMapList = new ArrayList<Map<String, Object>>();
		for (MessageResponseNewestWithBLOBs msgRespNewest : msgRespNewList) {
			Long sendBeginTime = msgRespNewest.getSendBeginTime();
			Long sendEndTime = msgRespNewest.getSendEndTime();
			Long createdTime = msgRespNewest.getCreatedTime();
			Map<String, Object> msgRespNewMap = BeanMapUtil.convertBean(msgRespNewest);
			msgRespNewMap.put("sendBeginTime", DateUtil.getDateStrFromTimestamp(sendBeginTime, DateUtil.simple));
			msgRespNewMap.put("sendEndTime", DateUtil.getDateStrFromTimestamp(sendEndTime, DateUtil.simple));
			msgRespNewMap.put("createdTime", DateUtil.getDateStrFromTimestamp(createdTime, DateUtil.simple));
			msgRespNewMapList.add(msgRespNewMap);
		}
		return msgRespNewMapList;
	}

	/**根据样例生成查询模板*/
	public MessageResponseNewestExample generateExample(MessageResponseNewest msgReqNewest) {
		MessageResponseNewestExample example = new MessageResponseNewestExample();
		Criteria criteria = example.createCriteria();
		Long messageId = msgReqNewest.getMessageId();
		Long id = msgReqNewest.getId();
		Integer httpStatus = msgReqNewest.getHttpStatus();
		if (messageId != null) {
			criteria.andMessageIdEqualTo(messageId);
		}
		if (id != null) {
			criteria.andIdEqualTo(id);
		}
		if (httpStatus != null) {
			criteria.andHttpStatusEqualTo(httpStatus);
		}
		return example;
	}
}
