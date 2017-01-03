package com.coe.message.api.service;

import com.coe.message.api.entity.RequestParamsEntity;
import com.coe.message.api.entity.ResultEntity;

/**报文信息接口*/
public interface IMsgServiceApi {
	/**
	 * 接收报文(存入Redis)
	 * @param paramsEntity 参数实体类
	 * @return ResultEntity返回信息实体
	 */
	public ResultEntity receiveMsg(RequestParamsEntity paramsEntity)throws Exception ;
}
