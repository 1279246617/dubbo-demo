package com.coe.wms.common;

import java.io.IOException;

import com.coe.wms.common.utils.mq.QueueingConsumerExpand;
import com.coe.wms.common.utils.mq.RabbitmqManager;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class TestRabbitMqDemo {

	public static void test() {
		RabbitmqManager.sendMsg("", "1", "logs", false, "2");

		try {
			Thread.sleep(2 * 60 * 1000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("sendMsg");
		RabbitmqManager.sendMsg("", "1", "logs", false, "2");

		RabbitmqManager.sendMsg("0727Test", "0", "", true, "0");
	}

	/**
	 * 接收信息 广播模式
	 */
	public static void receiveBroadcast() {

		/*
		 * new Thread(){
		 * 
		 * @Override public void run() { for (int i = 0; i < 1000000; i++) { sendMsgBroadcast(); }
		 * 
		 * }
		 * 
		 * }.start();
		 */

		String exchangeName = "exchangeName";// 转换器

		QueueingConsumerExpand queueingConsumerExpand = RabbitmqManager.getConsumer(true, "", "1", false, exchangeName);
		Channel channel = queueingConsumerExpand.getChannel();
		QueueingConsumer queueingConsumer = queueingConsumerExpand.getQueueingConsumer();

		try {
			// 获取消息
			Delivery delivery = queueingConsumer.nextDelivery();
			byte[] body = delivery.getBody();
			System.out.println(new String(body));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 接收信息 普通模式
	 */
	public static void receiveOrdinary() {
		boolean isAc = false;// 是否开启自动应答
		String title = "0727Test";// 主题

		QueueingConsumerExpand queueingConsumerExpand = RabbitmqManager.getConsumer(isAc, title, "0", true, "");
		Channel channel = queueingConsumerExpand.getChannel();
		QueueingConsumer queueingConsumer = queueingConsumerExpand.getQueueingConsumer();

		try {
			// 获取消息
			Delivery delivery = queueingConsumer.nextDelivery();
			byte[] body = delivery.getBody();
			System.out.println(new String(body));

			if (!isAc) {
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送信息 普通模式
	 */
	public static void sendMsgOrdinary() {
		// 测试生产者 一般模式
		String title = "0727Test";// 关键
		RabbitmqManager.sendMsg(title, "0", "", true, "0");

	}

	/**
	 * 发送信息 广播模式
	 */
	public static void sendMsgBroadcast() {
		// 测试生产者 广播模式
		String changeName = "exchangeName";// 关键
		RabbitmqManager.sendMsg("", "1", changeName, false, "8");
	}
}
