package com.coe.wms.common.utils.mq;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 
 * @ClassName: RabbitmqManager
 * @author lqg
 * @date 2017年7月25日 下午7:14:48
 * @Description: TODO
 */
public class RabbitmqManager {

	// 配置文件
	private static RabbitmqConfig rabbitmqConfig;

	// 连接工厂
	private static ConnectionFactory factory = null;

	// 连接map
	private static Map<String, Connection> connectionMap = new HashMap<String, Connection>();

	// 通道
	private static Map<String, List<ChannelExpand>> channelMap = new HashMap<String, List<ChannelExpand>>();

	// 连接通道数量
	private static Map<String, Integer> connectionChnnelCount = new HashMap<String, Integer>();

	// 连接队列
	private static DelayQueue<Delayed> connectionDelayed = new DelayQueue<Delayed>();

	// 通道队列
	private static DelayQueue<Delayed> channelDelayed = new DelayQueue<Delayed>();

	// 此链接用于接收数据
	private static Connection connection;

	private final static Integer lock = 2;

	static {
		factory = new ConnectionFactory();
		// 加载配置文件
		loadConfig();
		// 初始化工厂
		factory.setHost(rabbitmqConfig.getHost());
		factory.setUsername(rabbitmqConfig.getUserName());
		factory.setPassword(rabbitmqConfig.getPassword());
		factory.setPort(rabbitmqConfig.getPort());

		try {
			connection = factory.newConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 销毁通道队列
		/*
		 * new Thread() {
		 * 
		 * @Override public void run() { destroyChannelDelayed(); }
		 * 
		 * }.start();
		 */
		// 销毁连接队列
		/*
		 * new Thread() {
		 * 
		 * @Override public void run() { destroyConnectionDelayed(); }
		 * 
		 * }.start();
		 */
	}

	/**
	 * 销毁连接对列
	 */
	public static void destroyConnectionDelayed() {
		while (true) {
			try {
				RabbitMqConnectionPoolDelayed rabbitMqConnectionPoolDelayed = (RabbitMqConnectionPoolDelayed) connectionDelayed.take();
				Connection connection = rabbitMqConnectionPoolDelayed.getConnection();
				// 获取连接对象key
				String connectionKey = rabbitMqConnectionPoolDelayed.getConnectionKey();
				// 删除connection
				connectionMap.remove(connectionKey);
				// 删除channel
				channelMap.remove(connectionKey);
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 销毁连接通道
	 */
	public static void destroyChannelDelayed() {
		while (true) {
			try {
				RabbitMqChannelPoolDelayed rabbitMqChannelPoolDelayed = (RabbitMqChannelPoolDelayed) channelDelayed.take();
				ChannelExpand channelExpand = rabbitMqChannelPoolDelayed.getChannelExpand();
				// 连接connectionKey
				String connectionKey = channelExpand.getConnectionKey();
				// 通道chennelKey
				String channelKey = rabbitMqChannelPoolDelayed.getChannelKey();
				// 删除通道
				List<ChannelExpand> channelExpandList = channelMap.get(connectionKey);
				for (int i = 0; i < channelExpandList.size(); i++) {
					ChannelExpand childChannelExpand = channelExpandList.get(i);
					if (childChannelExpand.getChannelKey().equals(channelKey)) {
						Channel channel = childChannelExpand.getChannel();
						try {
							channel.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						channelExpandList.remove(i);
						i--;
					}
				}

				// channelMap

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 回收通道
	 * 
	 * @param channelExpand
	 */
	public static void recoveryChannel(ChannelExpand channelExpand) {
		channelMap.get(channelExpand.getConnectionKey()).add(channelExpand);
	}

	/**
	 * 获取消费者对象
	 * 
	 * @param isAc
	 *            是否自动完成
	 * @param title
	 * @param type
	 * @param isPersistence
	 * @param exchangeName
	 * @return
	 */
	public static QueueingConsumerExpand getConsumer(boolean isAc, String title, String type, boolean isPersistence, String exchangeName) {
		try {
			// 获取通道
			Channel channel = connection.createChannel();
			// 把分发
			if ("0".equals(type)) {
				// 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
				channel.queueDeclare(title, isPersistence, false, false, null);
				channel.basicQos(1);
				// 创建队列消费者
				QueueingConsumer consumer = new QueueingConsumer(channel);
				// 指定消费队列
				channel.basicConsume(title, isAc, consumer);

				QueueingConsumerExpand queueingConsumerExpand = new QueueingConsumerExpand();
				queueingConsumerExpand.setChannel(channel);
				queueingConsumerExpand.setQueueingConsumer(consumer);
				return queueingConsumerExpand;
			} else {

				channel.exchangeDeclare(exchangeName, "fanout");
				// 创建一个非持久的、唯一的且自动删除的队列

				if (title == null||title.trim().length()==0) {
					title = channel.queueDeclare().getQueue();
				}

				// 为转发器指定队列，设置binding 
				channel.queueBind(title, exchangeName, "");

				QueueingConsumer consumer = new QueueingConsumer(channel);
				// 指定接收者，第二个参数为自动应答，无需手动应答
				channel.basicConsume(title, isAc, consumer);

				QueueingConsumerExpand queueingConsumerExpand = new QueueingConsumerExpand();
				queueingConsumerExpand.setChannel(channel);
				queueingConsumerExpand.setQueueingConsumer(consumer);
				return queueingConsumerExpand;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取消费者", e);
		}

	}

	// public "erffasdf"

	/**
	 * 获取通道 主要用于发送消息时
	 * 
	 * @return
	 */
	public static ChannelExpand getChannel() {
		synchronized (lock) {

			if (connectionMap.size() == 0) {
				createNewConnection();
			}
			Set<Entry<String, Connection>> connectionSet = connectionMap.entrySet();
			for (Entry<String, Connection> connectionSingle : connectionSet) {
				// 获取key
				String connectionKey = connectionSingle.getKey();
				// 获取连接
				Connection connection = connectionSingle.getValue();
				// 获取已有 channle总数量
				Integer channelCount = connectionChnnelCount.get(connectionKey);
				// 获取可用channel数量
				List<ChannelExpand> channelList = channelMap.get(connectionKey);
				Integer availableCount = channelList.size();

				if (channelCount == 0 || (availableCount == 0 && channelCount < 600)) {
					ChannelExpand channelExpand = createChannel(connectionKey, connection, channelCount, channelList);
					if (channelExpand == null)
						continue;
					// 刷新生命connection周期
					refreshConnection(connectionKey, connection);
					// 刷新channel生命周期
					refreshChannel(channelExpand);
					return channelExpand;
				} else if (availableCount == 0) {
					continue;
				}
				ChannelExpand channelExpand = channelList.remove(0);
				// 刷新生命connection周期
				refreshConnection(connectionKey, connection);
				// 刷新channel生命周期
				refreshChannel(channelExpand);

				return channelExpand;

			}
			createNewConnection();
			return getChannel();

		}
	}

	/**
	 * 刷新channel生命周期
	 * 
	 * @param channelExpand
	 */
	private static void refreshChannel(ChannelExpand channelExpand) {
		try {
			RabbitMqChannelPoolDelayed rabbitMqChannelPoolDelayed = new RabbitMqChannelPoolDelayed();
			rabbitMqChannelPoolDelayed.setChannelExpand(channelExpand);
			rabbitMqChannelPoolDelayed.setChannelKey(channelExpand.getChannelKey());
			rabbitMqChannelPoolDelayed.setEndTime(System.currentTimeMillis() + 1 * 60 * 60 * 1000l);
			channelDelayed.remove(rabbitMqChannelPoolDelayed);
			channelDelayed.add(rabbitMqChannelPoolDelayed);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 刷新生命connection周期
	private static void refreshConnection(String connectionKey, Connection connection) {
		RabbitMqConnectionPoolDelayed rabbitMqConnectionPoolDelayed = new RabbitMqConnectionPoolDelayed();
		rabbitMqConnectionPoolDelayed.setConnection(connection);
		rabbitMqConnectionPoolDelayed.setConnectionKey(connectionKey);
		rabbitMqConnectionPoolDelayed.setEndTime(System.currentTimeMillis() + 3 * 60 * 60 * 1000l);
		connectionDelayed.remove(rabbitMqConnectionPoolDelayed);
		connectionDelayed.add(rabbitMqConnectionPoolDelayed);

	}

	/**
	 * 创建通道
	 * 
	 * @param connectionKey
	 * @param connection
	 * @param channelCount
	 * @param channelList
	 * @return
	 */
	private static ChannelExpand createChannel(String connectionKey, Connection connection, Integer channelCount, List<ChannelExpand> channelList) {
		try {
			System.out.println("创建通道");
			Channel channel = connection.createChannel();
			ChannelExpand channelExpand = new ChannelExpand();
			channelExpand.setChannel(channel);
			channelExpand.setChannelKey(UUID.randomUUID().toString() + System.nanoTime());
			channelExpand.setConnectionKey(connectionKey);
			channelList.add(channelExpand);
			connectionChnnelCount.put(connectionKey, ++channelCount);

			// 往管道队列新增数据
			RabbitMqChannelPoolDelayed rabbitMqChannelPoolDelayed = new RabbitMqChannelPoolDelayed();
			rabbitMqChannelPoolDelayed.setChannelExpand(channelExpand);
			rabbitMqChannelPoolDelayed.setChannelKey(channelExpand.getChannelKey());
			rabbitMqChannelPoolDelayed.setEndTime(System.currentTimeMillis() + 1 * 60 * 60 * 1000l);
			channelDelayed.add(rabbitMqChannelPoolDelayed);
			return channelExpand;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建新的连接
	 */
	private static void createNewConnection() {
		String newMapKey = UUID.randomUUID().toString() + System.nanoTime();
		try {
			Connection connection = factory.newConnection();
			connectionMap.put(newMapKey, connection);

			channelMap.put(newMapKey, new ArrayList<ChannelExpand>());

			connectionChnnelCount.put(newMapKey, 0);

			// 往连接队列新增数据
			RabbitMqConnectionPoolDelayed rabbitMqConnectionPoolDelayed = new RabbitMqConnectionPoolDelayed();
			rabbitMqConnectionPoolDelayed.setConnection(connection);
			rabbitMqConnectionPoolDelayed.setConnectionKey(newMapKey);
			rabbitMqConnectionPoolDelayed.setEndTime(System.currentTimeMillis() + 3 * 60 * 60 * 1000l);
			connectionDelayed.add(rabbitMqConnectionPoolDelayed);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 往消息对列发送消息
	 * 
	 * @param title
	 *            队列名字
	 * @param type
	 *            类型 0每个消费者消费不同数据 ,1所有订阅者均可获取数据
	 * @param msg
	 * @param isPersistence
	 *            是否持久化
	 * @return
	 */
	public static void sendMsg(String title, String type, String exchangeName, boolean isPersistence, String... msg) {
		ChannelExpand channel = null;
		try {
			// 定义持久化类型
			AMQP.BasicProperties persistenceType = null;
			// 获取通道
			channel = getChannel();
			if (title == null)
				title = "";
			if (exchangeName == null)
				exchangeName = "";
			if ("0".equals(type)) {
				channel.getChannel().queueDeclare(title, isPersistence, false, false, null);
			} else {
				channel.getChannel().exchangeDeclare(exchangeName, "fanout");
				channel.getChannel().queueDeclare(title, isPersistence, false, false, null);
				channel.getChannel().queueBind(title, exchangeName, "");
			}

			if (isPersistence)
				persistenceType = MessageProperties.PERSISTENT_TEXT_PLAIN;
			// 发送信息
			for (String ms : msg) {
				channel.getChannel().basicPublish(exchangeName, title, persistenceType, ms.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("发送失败,", e);
		} finally {
			if (channel != null) {
				synchronized (lock) {
					recoveryChannel(channel);
				}
			}
		}
	}

	/**
	 * 获取类加载器
	 * 
	 * @return
	 */
	public static ClassLoader classLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = ClassLoader.getSystemClassLoader();
		}
		return loader;
	}

	/**
	 * 加载配置
	 */
	public static void loadConfig() {
		InputStream resourceAsStream = classLoader().getResourceAsStream("rabbitmq.properties");
		if (resourceAsStream == null) {
			throw new RuntimeException("加载配置文件失败!");
		}

		Properties pro = new Properties();
		try {
			pro.load(resourceAsStream);
			resourceAsStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("读取配置文件失败!", e);
		}

		rabbitmqConfig = new RabbitmqConfig();
		rabbitmqConfig.setHost(pro.getProperty("host"));
		rabbitmqConfig.setUserName(pro.getProperty("user_name"));
		rabbitmqConfig.setPassword(pro.getProperty("password"));
		String portStr = pro.getProperty("port");
		Integer port = Integer.parseInt(portStr);
		rabbitmqConfig.setPort(port);
	}

}
