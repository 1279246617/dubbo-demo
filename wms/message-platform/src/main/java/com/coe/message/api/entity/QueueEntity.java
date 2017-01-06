package com.coe.message.api.entity;

import java.util.LinkedList;
import java.util.List;

/**队列实体类*/
public class QueueEntity {
	/**报文请求信息实体列表*/
	private volatile List<String> msgReqJsonList = new LinkedList<String>();
	/**静态实体对象*/
	private volatile static QueueEntity queueEntity;

	/**私有的构造方法*/
	private QueueEntity() {

	}

	/**获取单例对象*/
	public static QueueEntity getInstance() {
		if (queueEntity == null) {
			synchronized (QueueEntity.class) {
				if (queueEntity == null) {
					queueEntity = new QueueEntity();
				}
			}
		}
		return queueEntity;
	}

	public List<String> getMsgReqJsonList() {
		return msgReqJsonList;
	}

	public void setMsgReqJsonList(List<String> msgReqJsonList) {
		this.msgReqJsonList = msgReqJsonList;
	}

	public static QueueEntity getQueueEntity() {
		return queueEntity;
	}

	public static void setQueueEntity(QueueEntity queueEntity) {
		QueueEntity.queueEntity = queueEntity;
	}

}
