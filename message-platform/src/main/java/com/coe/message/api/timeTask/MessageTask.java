package com.coe.message.api.timeTask;

import java.util.Set;
import com.coe.message.api.entity.RequestMsgEntity;
import com.coe.message.common.JedisUtil;
import com.coe.message.entity.Message;
import com.coe.message.entity.MessageCallback;
import com.coe.message.entity.MessageRequest;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

/**报文信息定时任务类（定时将Redis缓存数据保存到MySQL数据库）*/
public class MessageTask {
	/**保存报文相关信息*/
	public static void saveMessageFromRedis() {
		Jedis jedis = JedisUtil.getJedis();
		Set<String> keySet = jedis.keys("*");
		for (String key : keySet) {
			//存入时，key:requestId,value:RequestMsgEntity转成的json格式字符串
			String requestMsgJson = jedis.get(key);
			Gson gson = new Gson();
			RequestMsgEntity requestMsgEntity = gson.fromJson(requestMsgJson,RequestMsgEntity.class);
			Message message = requestMsgEntity.getMessage();
			MessageCallback messageCallback = requestMsgEntity.getMessageCallback();
			MessageRequest messageRequest = requestMsgEntity.getMessageRequest();
			
		}
	}
	
}
