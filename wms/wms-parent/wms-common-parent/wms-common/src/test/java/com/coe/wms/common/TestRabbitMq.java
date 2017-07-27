package com.coe.wms.common;

import com.coe.wms.common.utils.mq.QueueingConsumerExpand;
import com.coe.wms.common.utils.mq.RabbitmqManager;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class TestRabbitMq {
	
	
	public static void main8(String[] args) throws Exception, Exception, InterruptedException {
		QueueingConsumerExpand consumerExpand = RabbitmqManager.getConsumer(false, "lqgTestFanout", "1", true, "logs");
	
		QueueingConsumer consumer = consumerExpand.getQueueingConsumer();
		Channel channel = consumerExpand.getChannel();
		for (int i = 0; i < 10; i++) {
			Delivery nextDelivery = consumer.nextDelivery();
			System.out.println(new String(nextDelivery.getBody()));
			channel.basicAck(nextDelivery.getEnvelope().getDeliveryTag(), false);
		}
		
	}
	
  
	public static void main(String[] args) {
		/*new Thread() {
			@Override
			public void run() {
				RabbitmqManager.destroyChannelDelayed();
				System.out.println("执行销毁通道线程");
			}

		}.start();
		new Thread() {
			@Override
			public void run() {
				RabbitmqManager.destroyConnectionDelayed();
				System.out.println("执行销毁连接线程");
			}

		}.start();
		*/
		
		
		
		
		mainCopy();

		mainCopy();
		//main1();
		
		/* QueueingConsumerExpand consumerExpand = RabbitmqManager.getConsumer(false, "lqgTest1", "0", true, "");
	    
		for (int i = 0; i < 50000; i++) {
			try {
				QueueingConsumer consumer = consumerExpand.getQueueingConsumer();
				Channel channel = consumerExpand.getChannel();
				Delivery nextDelivery = consumer.nextDelivery();
				System.out.println(new String(nextDelivery.getBody()));
				 channel.basicAck(nextDelivery.getEnvelope().getDeliveryTag(), false);
			     
			} catch (ShutdownSignalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConsumerCancelledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		//RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
		//RabbitmqManager.sendMsg("", "1", "logs", true, "0", "1", "2", "3", "4", "5");
	}
	
	public static void main1() {

		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 120000000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");

				}
			}

		}.start();

		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 120000000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");
				}
			}

		}.start();

		
		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 120000000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");
				}
			}

		}.start();

		
		
		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 120000000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");
				}
			}

		}.start();

		
		
		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 120000000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");
				}
			}

		}.start();

		
		
		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 120000000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");
				}
			}

		}.start();

		
		/**/
	}
	
	
	
	public static void mainCopy() {

		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");

				}
			}

		}.start();

		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");
				}
			}

		}.start();

		
		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");
				}
			}

		}.start();

		
		
		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");
				}
			}

		}.start();

		
		
		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");
				}
			}

		}.start();

		
		
		new Thread() {

			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					RabbitmqManager.sendMsg("lqgTest", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest1", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest2", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest3", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest4", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest5", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest6", "0", null, true, "0", "1", "2", "3", "4", "5");
					RabbitmqManager.sendMsg("lqgTest7", "0", null, false, "0", "1", "2", "3", "4", "5");
				}
			}

		}.start();

		
		/**/
	}
}
