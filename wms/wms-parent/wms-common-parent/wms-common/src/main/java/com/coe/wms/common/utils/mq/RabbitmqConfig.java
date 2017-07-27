package com.coe.wms.common.utils.mq;
/**
 * Rabbitmq 配置文件
* @ClassName: RabbitmqConfig  
* @author lqg  
* @date 2017年7月25日 下午7:12:00  
* @Description: TODO
 */
public class RabbitmqConfig {

	private String host;//ip
	private String userName;//用户名
	private String password;//密码
	private Integer port;//端口
	
	private Long connectionSurvivalTime=2*60*1000l;//(单位  毫秒) 连接对象存活时间 默认存活时间 3小时
	private Long channelSurvivalTime=1*60*1000l;//(单位 毫秒)  连接通道存活时间  默认存活时间  1小时  
	
	
	
	public Long getConnectionSurvivalTime() {
		return connectionSurvivalTime;
	}
	public void setConnectionSurvivalTime(Long connectionSurvivalTime) {
		this.connectionSurvivalTime = connectionSurvivalTime;
	}
	public Long getChannelSurvivalTime() {
		return channelSurvivalTime;
	}
	public void setChannelSurvivalTime(Long channelSurvivalTime) {
		this.channelSurvivalTime = channelSurvivalTime;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	
	
}
