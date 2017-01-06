package com.coe.message.service;

import com.coe.message.entity.MessageResponseWithBLOBs;

/**接口请求响应接口*/
public interface IMessageResponseService {
	/**更新或保存MessageResponse*/
	public int saveOrUpdate(MessageResponseWithBLOBs msgResponse);
}
