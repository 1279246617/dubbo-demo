package com.coe.message.api.job;

import java.util.List;
import org.apache.log4j.Logger;
import com.coe.message.api.entity.QueueEntity;
import com.coe.message.common.HttpClientUtil;
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
		//遍历队列
		for (int i = 0; i < msgReqJsonSize; i++) {
			String msgReqJson = msgReqJsonList.get(i);
			log.info("报文信息："+msgReqJson);
			Gson gson = new Gson();
			MessageRequestWithBLOBs msgReq = gson.fromJson(msgReqJson, MessageRequestWithBLOBs.class);
			//请求地址
			String requestUrl = msgReq.getUrl();
//			String body = msgReq.getBody();
			//请求的参数(Json格式字符串)
			String bodyParams = msgReq.getBodyParams();
			//header参数(Json格式字符串)
			String headerParams = msgReq.getHeaderParams();
			//请求方式，1：get,2:post
			Integer method = msgReq.getMethod();
			//回调地址
//			String callbackUrl = msgReq.getCallbackUrl();
			//现将该条报文从队列中移除
			msgReqJsonList.remove(i);
			//队列长度减1
			msgReqJsonSize--;
			//开始发送报文
		}
	}
	
}
