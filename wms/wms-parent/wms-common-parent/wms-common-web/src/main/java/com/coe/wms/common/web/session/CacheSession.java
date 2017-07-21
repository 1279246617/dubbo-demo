package com.coe.wms.common.web.session;

/**
 * 自定义session
 * 
 * @author lqg
 *
 */
public interface CacheSession {
	/**
	 * 设置参数
	 * 
	 * @param key
	 * @param value
	 */
	public void setAttr(String key, String value);

	/**
	 * 获取参数
	 * 
	 * @param key
	 */
	public String getAttr(String key);

	/**
	 * 刷新生命周期
	 * 
	 */
	public void refresh();
	
	/**
	 * 获取session超时时间
	 * @return
	 */
	public Integer getSessionTimeOut();
	
	/**
	 * 销毁session
	 */
	public void destroy();
	
}
