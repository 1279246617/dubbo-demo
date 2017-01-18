package com.coe.message.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.coe.message.api.entity.RequestMsgEntity;
import com.coe.message.api.entity.RequestParamsEntity;
import com.coe.message.api.entity.ResultEntity;
import com.coe.message.api.service.IMsgServiceApi;
import com.coe.message.common.JedisUtil;
import com.coe.message.entity.Message;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.coe.message.service.IMessageService;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

/**报文信息接口实现类*/
public class MsgServiceImplApi implements IMsgServiceApi {

	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private IMessageService messageService;

	/**
	 * 接收报文(存入Redis)
	 * @param paramsEntity 参数实体类
	 * @return ResultEntity返回信息实体
	 */
	public ResultEntity receiveMsg(RequestParamsEntity paramsEntity) throws Exception {
		ResultEntity resultEntity = new ResultEntity();
		int code = 0;
		String msg = "";
		try {
			String body = paramsEntity.getBody();
			String bodyParams = paramsEntity.getBodyParams();
			String headerParams = paramsEntity.getHeaderParams();
			String url = paramsEntity.getUrl();
			Integer method = paramsEntity.getMethod();
			String requestId = paramsEntity.getRequestId();
			String keyword1 = paramsEntity.getKeyword1();
			String keyword2 = paramsEntity.getKeyword2();
			String keyword3 = paramsEntity.getKeyword3();
			String callbackUrl = paramsEntity.getCallbackUrl();
			Integer soTimeOut = paramsEntity.getSoTimeOut();
			Integer connectionTimeOut = paramsEntity.getConnectionTimeOut();
			Long currentTime = new Date().getTime();
			if (StringUtils.isEmpty(url) || method == null || StringUtils.isEmpty(requestId) || StringUtils.isEmpty(keyword1) || StringUtils.isEmpty(callbackUrl)) {
				code = 1;
				msg = "参数缺失，必选参数[url,method,requestId,keyword1,callbackUrl],可选参数[body,bodyParams,headerParams,keyword2,keyword3,soTimeOut,connectionTimeOut]";
				resultEntity.setCode(code);
				resultEntity.setResultMsg(msg);
				return resultEntity;
			}
			Message messageQuery = new Message();
			messageQuery.setRequestId(requestId);
			List<Message> msgList = messageService.selectByExample(messageQuery);
			String messageJson = JedisUtil.getString(requestId);
			if (StringUtils.isNotBlank(messageJson) || msgList.size() > 0) {
				code = 2;
				msg = "requestId=" + requestId + "的报文已存在！";
				resultEntity.setCode(code);
				resultEntity.setResultMsg(msg);
				return resultEntity;
			}
			// Message
			Message message = new Message();
			message.setRequestId(requestId);
			message.setKeyword1(keyword1);
			message.setCreatedTime(currentTime);
			message.setCount(0);
			message.setStatus(0);
			message.setIsValid(0);
			if (StringUtils.isNotBlank(keyword2)) {
				message.setKeyword2(keyword2);
			}
			if (StringUtils.isNotBlank(keyword3)) {
				message.setKeyword3(keyword3);
			}
			// MessageRequest
			MessageRequestWithBLOBs mrwb = new MessageRequestWithBLOBs();
			mrwb.setCreatedTime(currentTime);
			mrwb.setMethod(method);
			mrwb.setUrl(url);
			mrwb.setBody(body);
			mrwb.setBodyParams(bodyParams);
			mrwb.setHeaderParams(headerParams);
			mrwb.setSoTimeOut(soTimeOut);
			mrwb.setConnectionTimeOut(connectionTimeOut);
			mrwb.setCallbackUrl(callbackUrl);
			// RequestMsgEntity
			RequestMsgEntity requestMsgEntity = new RequestMsgEntity();
			requestMsgEntity.setMessage(message);
			requestMsgEntity.setMsgReqWithBlobs(mrwb);
			Gson gson = new Gson();
			String requestMsgJson = gson.toJson(requestMsgEntity);
			log.info("存入Redis,报文信息：" + requestMsgJson);
			// 将接口请求相关信息存入Redis
			JedisUtil.setString(requestId, requestMsgJson);
			requestMsgJson = JedisUtil.getString(requestId);
			if (StringUtils.isNotBlank(requestMsgJson)) {
				resultEntity.setCode(0);
				resultEntity.setResultMsg("成功接收requestId=" + requestId + "的报文信息！");
			} else {
				resultEntity.setCode(-1);
				resultEntity.setResultMsg("报文接收存入Redis未成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
	}

	public IMessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
}
