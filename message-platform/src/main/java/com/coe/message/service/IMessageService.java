package com.coe.message.service;

import java.util.List;
import java.util.Map;

import com.coe.message.entity.Message;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.coe.message.entity.QueryParamsEntity;

/**报文信息接口*/
public interface IMessageService {

	/**更新或保存*/
	public int updateOrSave(Message msg);

	/**
	 * 分页查询(For API)
	 * @param records 总记录数
	 */
	public List<Message> queryPageForApi(int records);

	/**根据模板查询*/
	public List<Message> selectByExample(Message message);

	/**保存Message*/
	public Long saveMessageBackId(Message message);

	/**保存Message,MessageRequestWithBLOBs,MessageCallback报文相关请求信息*/
	public boolean saveMessageAll(Message message, MessageRequestWithBLOBs msgRequestWithBlobs) throws Exception;
	
	/**分页模糊查询*/
	public List<Map<String,Object>> queryListPageForVague(Message message,QueryParamsEntity paramsEntity);
	
	/**计数(For vague)*/
	public int countForVague(Message message,QueryParamsEntity paramsEntity);
}
