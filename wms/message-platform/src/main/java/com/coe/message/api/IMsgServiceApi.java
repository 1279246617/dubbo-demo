package com.coe.message.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coe.message.entity.RequestParamsEntity;
import com.coe.message.entity.ResultEntity;

/**报文信息接口*/
public interface IMsgServiceApi {
	/**
	 * 接收报文
	 * @param paramsEntity 报文接收实体
	 * @return Map返回信息
	 */
	public ResultEntity receiveMsg(HttpServletRequest request,HttpServletResponse response,RequestParamsEntity paramsEntity);
}
