package com.coe.message.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coe.message.common.BeanMapUtil;
import com.coe.message.common.DateUtil;
import com.coe.message.common.Page;
import com.coe.message.dao.mapper.MessageMapper;
import com.coe.message.dao.mapper.MessageRequestMapper;
import com.coe.message.entity.Message;
import com.coe.message.entity.MessageExample;
import com.coe.message.entity.MessageExample.Criteria;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.coe.message.entity.QueryParamsEntity;
import com.coe.message.service.IMessageService;

/**报文信息接口实现类*/
@Service
public class MessageServiceImpl implements IMessageService {
	@Autowired
	private MessageMapper msgMapper;
	@Autowired
	private MessageRequestMapper msgReqMapper;

	/**分页模糊查询*/
	public List<Map<String, Object>> queryListPageForVague(Message message, QueryParamsEntity queryParams) {
		MessageExample msgExample = generateExampleForVague(message, queryParams);
		int pageNo = queryParams.getPage();
		int pageSize = queryParams.getRows();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		int startIndex = page.getStartIndex();
		Map<String, Object> paramsMap = BeanMapUtil.convertBean(msgExample);
		paramsMap.put("startIndex", startIndex);
		paramsMap.put("pageSize", pageSize);
		List<Message> msgList = msgMapper.queryListPageForVague(paramsMap);
		return objToMapList(msgList);
	}

	/**保存或更新*/
	public int updateOrSave(Message msg) {
		Long id = msg.getId();
		if (id != null) {
			return msgMapper.updateByPrimaryKeySelective(msg);
		} else {
			return msgMapper.insertSelective(msg);
		}
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
		String keyword3 = message.getKeyword3();
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
		if (StringUtils.isNotBlank(keyword3)) {
			messageCriteria.andKeyword3EqualTo(keyword3);
		}
		if (StringUtils.isNotBlank(keyword1)) {
			messageCriteria.andKeyword1EqualTo(keyword1);
		}
		if (StringUtils.isNotBlank(keyword2)) {
			messageCriteria.andKeyword2EqualTo(keyword2);
		}
		return msgMapper.selectByExample(msgExample);
	}

	/**生成查询样例*/
	public MessageExample generateExampleForVague(Message message, QueryParamsEntity queryParams) {
		MessageExample msgExample = new MessageExample();
		Criteria criteria = msgExample.createCriteria();
		Integer isValid = message.getIsValid();
		Integer status = message.getStatus();
		String requestId = message.getRequestId();
		String keyword1 = message.getKeyword1();
		String keyword2 = message.getKeyword2();
		String keyword3 = message.getKeyword3();
		String endTime = queryParams.getEndTime();
		String startTime = queryParams.getStartTime();
		if (isValid != null) {
			criteria.andIsValidEqualTo(isValid);
		}
		if (status != null) {
			criteria.andStatusEqualTo(status);
		}
		if (StringUtils.isNotBlank(requestId)) {
			criteria.andRequestIdLike("%" + requestId + "%");
		}
		if (StringUtils.isNotBlank(keyword1)) {
			criteria.andKeyword1Like("%" + keyword1 + "%");
		}
		if (StringUtils.isNotBlank(keyword2)) {
			criteria.andKeyword2Like("%" + keyword2 + "%");
		}
		if (StringUtils.isNotBlank(keyword3)) {
			criteria.andKeyword3Like("%" + keyword3 + "%");
		}
		if (StringUtils.isNotBlank(endTime)) {
			Long endTimeLong = DateUtil.getTimestampFromDateStr(endTime, DateUtil.simple);
			criteria.andCreatedTimeLessThan(endTimeLong);
		}
		if (StringUtils.isNotBlank(startTime)) {
			Long startTimeLong = DateUtil.getTimestampFromDateStr(startTime, DateUtil.simple);
			criteria.andCreatedTimeGreaterThanOrEqualTo(startTimeLong);
		}
		return msgExample;
	}

	/**计数(For vague)*/
	public int countForVague(Message message, QueryParamsEntity paramsEntity) {
		MessageExample msgExample = generateExampleForVague(message, paramsEntity);
		return msgMapper.countByExample(msgExample);
	}

	/**实体转Map*/
	public List<Map<String, Object>> objToMapList(List<Message> msgList) {
		List<Map<String, Object>> msgMapList = new ArrayList<Map<String, Object>>();
		for (Message message : msgList) {
			Map<String, Object> msgMap = BeanMapUtil.convertBean(message);
			Long createTime = message.getCreatedTime();
			Integer isValid = message.getIsValid();
			Integer status = message.getStatus();
			if (createTime != null) {
				String timeStr = DateUtil.getDateStrFromTimestamp(createTime, DateUtil.simple);
				msgMap.put("createTime", timeStr);
			}
			if (isValid == 0) {
				msgMap.put("isValid","有效");
			} else {
				msgMap.put("isValid","无效");
			}
			if (status == 0) {
				msgMap.put("status","未推送");
			} else if (status == 1) {
				msgMap.put("status","已推送且响应成功");
			} else if (status == 2) {
				msgMap.put("status","已推送响应不成功");
			}else{
				msgMap.put("status","未知");
			}
			msgMapList.add(msgMap);
		}
		return msgMapList;
	}

}
