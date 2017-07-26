package com.coe.wms.common.utils.mq;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.rabbitmq.client.Connection;

/**
 * 用于清空不常用连接
* @ClassName: RabbitMqConnectionPoolDelayed  
* @author lqg  
* @date 2017年7月26日 上午10:18:10  
* @Description: TODO
 */
public class RabbitMqConnectionPoolDelayed implements Delayed {

	private Connection connection;

	// 截止时间
	private long endTime;

	//唯一id
	private String connectionKey;
	/**
	 * 用来判断是否到了截止时间
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		return endTime - System.currentTimeMillis();
	}

	
	
	/**
	 * 相互批较排序用
	 */
	@Override
	public int compareTo(Delayed o) {
		RabbitMqConnectionPoolDelayed jia = (RabbitMqConnectionPoolDelayed) o;
		return endTime - jia.endTime > 0 ? 1 : (endTime - jia.endTime == 0 ? 0
				: -1);
	}

	@Override
	public int hashCode() {
		return connectionKey.hashCode();
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RabbitMqConnectionPoolDelayed other = (RabbitMqConnectionPoolDelayed) obj;
		if (connectionKey == null) {
				return false;
		} else if (!connectionKey.equals(other.getConnectionKey()))
			return false;
		return true;
	}



	 

	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
	}



	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}



	public String getConnectionKey() {
		return connectionKey;
	}



	public void setConnectionKey(String connectionKey) {
		this.connectionKey = connectionKey;
	}

}
