package com.coe.wms.common.utils.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 消费者对象
* @ClassName: QueueingConsumerExpand  
* @author lqg  
* @date 2017年7月26日 下午5:55:29  
* @Description: TODO
 */
public class QueueingConsumerExpand {

	private Channel channel;
	
	private QueueingConsumer queueingConsumer;

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public QueueingConsumer getQueueingConsumer() {
		return queueingConsumer;
	}

	public void setQueueingConsumer(QueueingConsumer queueingConsumer) {
		this.queueingConsumer = queueingConsumer;
	}
	
	
}
