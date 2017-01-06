package com.coe.message.api.job;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.coe.message.api.entity.QueueEntity;
import com.coe.message.entity.Message;
import com.coe.message.entity.MessageRequest;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.coe.message.service.IMessage;
import com.coe.message.service.IMessageRequest;
import com.google.gson.Gson;

/**定时任务类(定时读取数据库数据保存到队列LinkedList)*/
public class MsgToListJob {
	@Autowired
	private IMessage messageService;
	@Autowired
	private IMessageRequest messageReqService;
	private Logger log = Logger.getLogger(this.getClass());
	/**存放报文队列最大长度*/
	private static final int LIST_SIZE = 5000;

	/**定时任务，定时从数据库读取数据保存到List*/
	public void msgToList() {
		try {
			log.info("toListJob...........................");
			QueueEntity queueEntity = QueueEntity.getInstance();
			List<String> msgReqJsonList = queueEntity.getMsgReqJsonList();
			// 集合当前的长度
			int msgReqSize = msgReqJsonList.size();
			// 距离集合最大长度还有多少
			int gapSize = LIST_SIZE - msgReqSize;
			List<Message> msgList = new ArrayList<Message>();
			// 大于1000条,查询1000
			if (gapSize > 1000 || gapSize == 1000) {
				try {
					msgList = messageService.queryPageForApi(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 介于0-1000条，差多少条查多少条
			if (0 < gapSize && gapSize < 1000) {
				msgList = messageService.queryPageForApi(gapSize);
			}
			if (msgList.size() > 0) {
				int msgSize = msgList.size();
				// 遍历Message列表，查询出MessageRequest列表，存入队列
				for (int i = 0; i < msgSize; i++) {
					Message message = msgList.get(i);
					Long messageId = message.getId();
					MessageRequest msgReq = new MessageRequest();
					msgReq.setMessageId(messageId);
					List<MessageRequestWithBLOBs> msgReqRecords = messageReqService.selectByExample(msgReq);
					if (msgReqRecords.size() > 0) {
						MessageRequest messageReq = msgReqRecords.get(0);
						String messageReqJson = new Gson().toJson(messageReq);
						// 队列中不存在则存入
						if (!msgReqJsonList.contains(messageReqJson)) {
							msgReqJsonList.add(messageReqJson);
							log.info("报文存入队列成功，报文信息：" + messageReqJson);
							// 更新Message字段count，使count加1
							Message updateMsg = new Message();
							updateMsg.setId(messageId);
							updateMsg.setCount(message.getCount() + 1);
							messageService.updateOrSave(updateMsg);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
