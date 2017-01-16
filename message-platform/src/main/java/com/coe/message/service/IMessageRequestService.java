package com.coe.message.service;

import java.util.List;
import java.util.Map;

import com.coe.message.entity.MessageRequest;
import com.coe.message.entity.MessageRequestWithBLOBs;

/**报文请求信息接口*/
public interface IMessageRequestService {
	/**根据模板查询*/
	public List<MessageRequestWithBLOBs> selectByExampleBackList(MessageRequest msgReq);
	/**根据模板查询*/
	public List<Map<String,Object>> selectByExampleBackMapList(MessageRequest msgReq);
}
