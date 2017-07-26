package com.coe.wms.common.utils.mq;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


public class RabbitMqChannelPoolDelayed implements Delayed {

	private ChannelExpand channelExpand;

	// 截止时间
	private long endTime;

	//唯一id
	private String channelKey;
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
		RabbitMqChannelPoolDelayed jia = (RabbitMqChannelPoolDelayed) o;
		return endTime - jia.endTime > 0 ? 1 : (endTime - jia.endTime == 0 ? 0
				: -1);
	}

	@Override
	public int hashCode() {
		return channelKey.hashCode();
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RabbitMqChannelPoolDelayed other = (RabbitMqChannelPoolDelayed) obj;
		if (channelKey == null) {
				return false;
		} else if (!channelKey.equals(other.getChannelKey()))
			return false;
		return true;
	}



	 

	



	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}



	public String getChannelKey() {
		return channelKey;
	}



	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}



	public ChannelExpand getChannelExpand() {
		return channelExpand;
	}



	public void setChannelExpand(ChannelExpand channelExpand) {
		this.channelExpand = channelExpand;
	}



	 

}
