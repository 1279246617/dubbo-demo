package com.coe.message.api.job;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.coe.message.api.entity.QueueEntity;
import com.coe.message.common.HttpClientUtil;
import com.coe.message.common.JsonMapUtil;
import com.coe.message.entity.Message;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.coe.message.entity.MessageResponseWithBLOBs;
import com.coe.message.service.IMessageResponseService;
import com.coe.message.service.IMessageService;
import com.google.gson.Gson;

/**发送报文任务类*/
public class MsgSendJob {
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private IMessageService messageService;
	@Autowired
	private IMessageResponseService msgResponseService;

	/**发送报文*/
	public void sendMsg() {
		log.info("发送报文任务(msgSendJob)......................");
		QueueEntity queueEntity = QueueEntity.getInstance();
		List<String> msgReqJsonList = queueEntity.getMsgReqJsonList();
		int msgReqJsonSize = msgReqJsonList.size();
		// 遍历队列
		for (int i = 0; i < msgReqJsonSize; i++) {
			String msgReqJson = msgReqJsonList.get(i);
			log.info("发送报文信息：" + msgReqJson);
			Gson gson = new Gson();
			MessageRequestWithBLOBs msgReq = gson.fromJson(msgReqJson, MessageRequestWithBLOBs.class);
			Long messageId = msgReq.getMessageId();
			// 请求地址
			String requestUrl = msgReq.getUrl();
			// String body = msgReq.getBody();
			// 请求的参数(Json格式字符串)
			String bodyParams = msgReq.getBodyParams();
			// header参数(Json格式字符串)
			String headerParams = msgReq.getHeaderParams();
			// 链接超时时间
			Integer connectTimeout = msgReq.getConnectionTimeOut();
			// 响应超时时间
			Integer socketTimeout = msgReq.getSoTimeOut();
			Map<String, Object> headers = null;
			Map<String, Object> requestParams = null;
			if (StringUtils.isNotBlank(headerParams)) {
				headers = JsonMapUtil.jsonToMap(headerParams);
			}
			if (StringUtils.isNotBlank(bodyParams)) {
				requestParams = JsonMapUtil.jsonToMap(bodyParams);
			}
			// 请求方式，1：get,2:post
			Integer method = msgReq.getMethod();
			// 回调地址
			// String callbackUrl = msgReq.getCallbackUrl();
			// 将该条报文从队列中移除
			msgReqJsonList.remove(i);
			// 队列长度减1
			msgReqJsonSize--;
			// 开始发送报文
			Long sendBeginTime = new Date().getTime();
			try {
				HttpResponse response = null;
				// 发送请求，获得响应信息
				response = sendHttpRequest(requestUrl, headers, requestParams, method, connectTimeout, socketTimeout);
				int httpCode = response.getStatusLine().getStatusCode();
				Long sendEndTime = new Date().getTime();
				Message msg = new Message();
				msg.setId(messageId);
				if (httpCode == 200) {
					msg.setStatus(1);
				} else {
					msg.setStatus(2);
				}
				// 将Message表字段status更新
				messageService.updateOrSave(msg);
				saveMsgResponse(sendBeginTime, response, httpCode, sendEndTime, messageId);
			} catch (Exception e) {
				log.info("报文发送出错，报文信息：" + msgReqJson);
				String errorMsg = e.getMessage();
				log.info("错误信息:"+errorMsg);
				MessageResponseWithBLOBs msgResponse = new MessageResponseWithBLOBs();
				msgResponse.setMessageId(messageId);
				int httpStatus = 0;
				String httpStatusMsg = "";
				if(errorMsg.contains("Read timed out")){
					httpStatus = -1;
					httpStatusMsg = "Read timed out";
				}else if(errorMsg.contains("connect timed out")){
					httpStatus = -2;
					httpStatusMsg = "connect timed out";
				}else{
					httpStatus = -3;
					httpStatusMsg = e.getMessage();
				}
				msgResponse.setHttpStatus(httpStatus);
				msgResponse.setHttpStatusMsg(httpStatusMsg);
				msgResponse.setCreatedTime(new Date().getTime());
				msgResponse.setSendBeginTime(sendBeginTime);
				Long sendEndTime = new Date().getTime();
				msgResponse.setSendEndTime(sendEndTime);
				msgResponse.setUsedTime(sendEndTime-sendBeginTime);
				msgResponseService.saveOrUpdate(msgResponse);
			}
		}
	}

	/**保存响应信息到数据库*/
	private void saveMsgResponse(Long sendBeginTime, HttpResponse response, int httpCode, Long sendEndTime, Long messageId) throws IOException {
		MessageResponseWithBLOBs msgResponse = new MessageResponseWithBLOBs();
		msgResponse.setCreatedTime(new Date().getTime());
		msgResponse.setHttpStatus(httpCode);
		msgResponse.setSendBeginTime(sendBeginTime);
		msgResponse.setSendEndTime(sendEndTime);
		msgResponse.setUsedTime(sendEndTime - sendBeginTime);
		msgResponse.setMessageId(messageId);
		HttpEntity entity = response.getEntity();
		Header[] responseHeaders = response.getAllHeaders();
		String headerJson = "";
		for (Header respHeader : responseHeaders) {
			Map<String, Object> headerMap = new HashMap<String, Object>();
			headerMap.put(respHeader.getName(), respHeader.getValue());
			headerJson = JsonMapUtil.mapToJson(headerMap);
		}
		msgResponse.setResponseHeader(headerJson);
		if (entity != null) {
			msgResponse.setResponseBody(EntityUtils.toString(entity));
		}
		msgResponse.setHttpStatus(httpCode);
		msgResponse.setHttpStatusMsg(response.getStatusLine().getReasonPhrase());
		msgResponseService.saveOrUpdate(msgResponse);
	}

	/**
	 * 发送请求返回HttpResponse实例，响应信息
	 * @param requestUrl
	 * @param headers
	 * @param requestParams
	 * @param method
	 */
	private HttpResponse sendHttpRequest(String requestUrl, Map<String, Object> headers, Map<String, Object> requestParams, Integer method, Integer connectTimeout, Integer socketTimeout) throws Exception {
		HttpResponse response;
		// get方式请求
		if (method == 1) {
			HttpGet httpGet = null;
			if (requestParams != null) { // 有参数
				httpGet = HttpClientUtil.initHttpGet(requestUrl, requestParams, headers, connectTimeout, socketTimeout);
			} else {// 无参数
				httpGet = HttpClientUtil.initHttpGet(requestUrl, headers, connectTimeout, socketTimeout);
			}
			response = HttpClientUtil.getResponse(httpGet);
		} else {// post请求
			HttpPost httpPost = null;
			if (requestParams != null) {
				httpPost = HttpClientUtil.initHttpPost(requestUrl, requestParams, headers, connectTimeout, socketTimeout);
			} else {
				httpPost = HttpClientUtil.initHttpPost(requestUrl, headers, connectTimeout, socketTimeout);
			}
			response = HttpClientUtil.getResponse(httpPost);
		}
		return response;
	}

}
