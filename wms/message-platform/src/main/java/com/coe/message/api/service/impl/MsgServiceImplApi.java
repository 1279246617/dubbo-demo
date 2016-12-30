package com.coe.message.api.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import com.coe.message.api.entity.RequestMsgEntity;
import com.coe.message.api.entity.RequestParamsEntity;
import com.coe.message.api.entity.ResultEntity;
import com.coe.message.api.service.IMsgServiceApi;
import com.coe.message.common.JedisUtil;
import com.coe.message.entity.Message;
import com.coe.message.entity.MessageCallback;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.google.gson.Gson;

/**报文信息接口实现类*/
public class MsgServiceImplApi implements IMsgServiceApi {

	/**
	 * 接收报文
	 * @param paramsEntity 参数实体类
	 * @return ResultEntity返回信息实体
	 */
	public ResultEntity receiveMsg(RequestParamsEntity paramsEntity) throws Exception {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String body = paramsEntity.getBody();
			String bodyParams = paramsEntity.getBodyParams();
			String headerParams = paramsEntity.getHeaderParams();
			String url = paramsEntity.getUrl();
			Integer method = paramsEntity.getMethod();
			String requestId = paramsEntity.getRequestId();
			String keyword = paramsEntity.getKeyword();
			String keyword1 = paramsEntity.getKeyword1();
			String keyword2 = paramsEntity.getKeyword2();
			String callbackUrl = paramsEntity.getCallbackUrl();
			Date currentTime = new Date();
			if (StringUtils.isEmpty(url) || method == null || StringUtils.isEmpty(requestId) || StringUtils.isEmpty(keyword) || StringUtils.isEmpty(callbackUrl)) {
				throw new NullPointerException("参数缺失，必选参数[url,method,requestId,keyword,callback],可选参数[body,bodyParams,headerParams,keyword1,keyword2]");
			}
			// Message
			Message message = new Message();
			message.setRequestId(requestId);
			message.setKeyword(keyword);
			message.setCreatedTime(currentTime);
			message.setCount(0);
			message.setStatus(0);
			if (StringUtils.isNotBlank(keyword1)) {
				message.setKeyword1(keyword1);
			}
			if (StringUtils.isNotBlank(keyword2)) {
				message.setKeyword2(keyword2);
			}
			// MessageCallback
			MessageCallback messageCallback = new MessageCallback();
			messageCallback.setCallbackUrl(callbackUrl);
			messageCallback.setCreatedTime(currentTime);
			// MessageRequest
			MessageRequestWithBLOBs mrwb = new MessageRequestWithBLOBs();
			mrwb.setCreatedTime(currentTime);
			mrwb.setMethod(method);
			mrwb.setUrl(url);
			mrwb.setBody(body);
			mrwb.setBodyParams(bodyParams);
			mrwb.setHeaderParams(headerParams);
			// RequestMsgEntity
			RequestMsgEntity requestMsgEntity = new RequestMsgEntity();
			requestMsgEntity.setMessage(message);
			requestMsgEntity.setMessageCallback(messageCallback);
			requestMsgEntity.setMessageRequest(mrwb);
			Gson gson = new Gson();
			String requestMsgJson = gson.toJson(requestMsgEntity);
			// 将接口请求相关信息存入Redis
			JedisUtil.setString(requestId, requestMsgJson);
			requestMsgJson = JedisUtil.getString(requestId);
			if(StringUtils.isNotBlank(requestMsgJson)){
				resultEntity.setCode(0);
				resultEntity.setResultMsg("成功接收requestId=" + requestId + "的报文信息！");
			}else{
				resultEntity.setCode(-1);
				resultEntity.setResultMsg("报文接收未成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("接口出错！");
		}
		return resultEntity;
	}

	public static void main(String[] args) throws Exception {
		IMsgServiceApi ma = new MsgServiceImplApi();
		RequestParamsEntity paramsEntity = new RequestParamsEntity();
		paramsEntity.setBody("body");
		paramsEntity.setBodyParams("bodyParams");
		paramsEntity.setHeaderParams("headerParams");
		paramsEntity.setCallbackUrl("http://callbackUrl");
		paramsEntity.setUrl("http:url");
		paramsEntity.setMethod(1);
		paramsEntity.setRequestId("123465");
		paramsEntity.setKeyword("keyword");
		paramsEntity.setKeyword1("keyword1");
		paramsEntity.setKeyword2("keyword2");
		ResultEntity  resultEntity = ma.receiveMsg(paramsEntity);
		System.out.println("code:"+resultEntity.getCode());
		System.out.println("resultMsg:"+resultEntity.getResultMsg());
		System.out.println(JedisUtil.getString("123465"));
	}

}
