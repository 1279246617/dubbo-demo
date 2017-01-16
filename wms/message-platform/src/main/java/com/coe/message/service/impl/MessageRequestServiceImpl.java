package com.coe.message.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coe.message.common.BeanMapUtil;
import com.coe.message.common.DateUtil;
import com.coe.message.dao.mapper.MessageRequestMapper;
import com.coe.message.entity.MessageRequest;
import com.coe.message.entity.MessageRequestExample;
import com.coe.message.entity.MessageRequestExample.Criteria;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.coe.message.service.IMessageRequestService;

/**报文请求信息接口实现类*/
@Service
public class MessageRequestServiceImpl implements IMessageRequestService {
	@Autowired
	private MessageRequestMapper msgReqMapper;

	public List<Map<String,Object>> selectByExampleBackMapList(MessageRequest msgReq) {
		List<MessageRequestWithBLOBs> msgReqList = selectByExampleBackList(msgReq);
		return objListToMapList(msgReqList);
	}

	/**根据模板查询*/
	public List<MessageRequestWithBLOBs> selectByExampleBackList(MessageRequest msgReq) {
		MessageRequestExample example = generateExample(msgReq);
		return msgReqMapper.selectByExampleWithBLOBs(example);
	}

	/**生成查询模板*/
	public MessageRequestExample generateExample(MessageRequest msgReq) {
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
		return example;
	}

	/**实体转Map*/
	public List<Map<String, Object>> objListToMapList(List<MessageRequestWithBLOBs> msgReqWithBlobsList) {
		List<Map<String, Object>> msgReqWithBlobsMapList = new ArrayList<Map<String, Object>>();
		for (MessageRequestWithBLOBs msgReqWithBlobs : msgReqWithBlobsList) {
			Long createdTime = msgReqWithBlobs.getCreatedTime();
			int method = msgReqWithBlobs.getMethod();
			String methodStr = "";
			Map<String, Object> msgReqWithBlobsMap = BeanMapUtil.convertBean(msgReqWithBlobs);
			if (createdTime != null) {
				msgReqWithBlobsMap.put("createdTime",DateUtil.getDateStrFromTimestamp(createdTime,DateUtil.simple));
			}
			if(method==1){
				methodStr = "get";
			}else if(method==2){
				methodStr = "post";
			}else{
				methodStr = "未知";
			}
			msgReqWithBlobsMap.put("method",methodStr);
			msgReqWithBlobsMapList.add(msgReqWithBlobsMap);
		}
		return msgReqWithBlobsMapList;
	}

}
