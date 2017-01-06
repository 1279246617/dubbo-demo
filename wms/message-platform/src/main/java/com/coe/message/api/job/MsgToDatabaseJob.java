package com.coe.message.api.job;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.coe.message.api.entity.RequestMsgEntity;
import com.coe.message.common.JedisUtil;
import com.coe.message.entity.Message;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.coe.message.service.IMessageService;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

/**定时任务类(定时将Redis里的数据保存到Mysql数据库)*/
public class MsgToDatabaseJob {
	@Autowired
	private IMessageService messageService;
	private Logger log = Logger.getLogger(this.getClass());

	/**定时任务，定时将Redis里的数据保存到MySql数据库*/
	public void msgToDB() {
		log.info("toDatabeseJob.................................");
		// 遍历Redis里的数据
		Jedis jedis = JedisUtil.getJedis();
		Set<String> keySet = jedis.keys("*");
		for (String key : keySet) {
			// 存入时，key:requestId,value:RequestMsgEntity转成的json格式字符串
			String requestMsgJson = jedis.get(key);
			log.info("保存到mysql数据库，报文信息：" + requestMsgJson);
			Gson gson = new Gson();
			RequestMsgEntity requestMsgEntity = gson.fromJson(requestMsgJson, RequestMsgEntity.class);
			Message message = requestMsgEntity.getMessage();
			String requestId = message.getRequestId();
			Message messageQuery = new Message();
			messageQuery.setRequestId(requestId);
			List<Message> msgList = messageService.selectByExample(messageQuery);
			if (msgList.size() > 0) {
				log.info("requestId=" + requestId + "的报文已存在，无需保存！");
				// 数据库存在，则删除Redis里相应的数据
				jedis.del(key);
				// 跳过本次循环
				continue;
			}
			MessageRequestWithBLOBs msgRequestWithBlobs = requestMsgEntity.getMsgReqWithBlobs();
			msgRequestWithBlobs.setCreatedTime(new Date().getTime());
			message.setCreatedTime(new Date().getTime());
			boolean flag = false;
			try {
				flag = messageService.saveMessageAll(message, msgRequestWithBlobs);
				log.info("报文保存到数据库成功！[requestId=" + requestId + "]");
			} catch (Exception e) {
				log.info("报文保存到数据库出错！[requestId=" + requestId + "]");
				e.printStackTrace();
			}
			if (flag) {
				// 成功保存到数据库后将缓存数据删除
				jedis.del(key);
			}
		}
	}

}
