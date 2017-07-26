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
