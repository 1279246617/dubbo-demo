package com.coe.wms.common.utils.mq;

import com.rabbitmq.client.Channel;

/**
 * 通道拓展类
* @ClassName: ChannelExpand  
* @author lqg  
* @date 2017年7月26日 上午11:16:33  
* @Description: TODO
 */
public class ChannelExpand {

	private Channel channel;
	
	private String connectionKey;
	
	
	//唯一channelKey
	private String channelKey;

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getConnectionKey() {
		return connectionKey;
	}

	public void setConnectionKey(String connectionKey) {
		this.connectionKey = connectionKey;
	}

	public String getChannelKey() {
		return channelKey;
	}

	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}

	 
	
	
}
