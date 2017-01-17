package com.coe.message.service;

import java.util.List;
import java.util.Map;

import com.coe.message.entity.MessageResponseNewest;
import com.coe.message.entity.MessageResponseNewestWithBLOBs;

/**当前最新一次响应信息接口*/
public interface IMsgRespNewService {
	/**根据样例查询*/
	public List<MessageResponseNewestWithBLOBs> selectByExampleBackList(MessageResponseNewest msgReqNewest);
	public List<Map<String, Object>> selectByExampleBackMap(MessageResponseNewest msgReqNewest);
}
