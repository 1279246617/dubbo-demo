package com.coe.message.api.job;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.log4j.Logger;
import com.coe.message.api.entity.QueueEntity;
import com.coe.message.common.HttpClientUtil;
import com.coe.message.common.JsonMapUtil;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.google.gson.Gson;

/**发送报文任务类*/
public class MsgSendJob {
	private Logger log = Logger.getLogger(this.getClass());

	/**发送报文*/
	public void sendMsg() {
		log.info("msgSendJob......................");
		QueueEntity queueEntity = QueueEntity.getInstance();
		List<String> msgReqJsonList = queueEntity.getMsgReqJsonList();
		int msgReqJsonSize = msgReqJsonList.size();
		// 遍历队列
		for (int i = 0; i < msgReqJsonSize; i++) {
			String msgReqJson = msgReqJsonList.get(i);
			log.info("报文信息：" + msgReqJson);
			Gson gson = new Gson();
			MessageRequestWithBLOBs msgReq = gson.fromJson(msgReqJson, MessageRequestWithBLOBs.class);
			// 请求地址
			String requestUrl = msgReq.getUrl();
			// String body = msgReq.getBody();
			// 请求的参数(Json格式字符串)
			String bodyParams = msgReq.getBodyParams();
			// header参数(Json格式字符串)
			String headerParams = msgReq.getHeaderParams();
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
			try {
				HttpResponse response = null;
				// get方式请求
				if (method == 1) {
					HttpGet httpGet = null;
					if (requestParams != null) { // 有参数
						httpGet = HttpClientUtil.initHttpGet(requestUrl, requestParams, headers);
					} else {// 无参数
						httpGet = HttpClientUtil.initHttpGet(requestUrl, headers);
					}
					response = HttpClientUtil.getResponse(httpGet);
				} else {// post请求
					HttpPost httpPost = null;
					if (requestParams != null) {
						httpPost = HttpClientUtil.initHttpPost(requestUrl, requestParams, headers);
					} else {
						httpPost = HttpClientUtil.initHttpPost(requestUrl, headers);
					}
					response = HttpClientUtil.getResponse(httpPost);
				}
				int statusCode = response.getStatusLine().getStatusCode();
				System.out.println(statusCode);
			} catch (Exception e) {
				log.info("报文发送出错，报文信息：" + msgReqJson);
				e.printStackTrace();
			}
		}
	}

}
