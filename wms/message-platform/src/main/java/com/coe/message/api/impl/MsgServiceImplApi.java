package com.coe.message.api.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coe.message.api.IMsgServiceApi;
import com.coe.message.entity.RequestParamsEntity;
import com.coe.message.entity.ResultEntity;

/**报文信息接口实现类*/
public class MsgServiceImplApi implements IMsgServiceApi{

	/**接收报文*/
	public ResultEntity receiveMsg(HttpServletRequest request,HttpServletResponse response,RequestParamsEntity paramsEntity) {
		
		return null;
	}

}
