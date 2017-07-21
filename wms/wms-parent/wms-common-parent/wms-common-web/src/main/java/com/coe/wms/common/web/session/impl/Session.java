package com.coe.wms.common.web.session.impl;


import com.coe.wms.common.core.cache.redis.RedisClient;
import com.coe.wms.common.web.session.CacheSession;
import com.coe.wms.common.web.session.CookieHolder;
/**
 * 自定义session 实现
 * @author lqg
 *
 */
public class Session implements CacheSession{

	
	private Integer  sessionTimeOut;
	
	
	
	@Override
	public void setAttr(String key, String value) {
		//cache.put(CookieHolder.getSessionId(), key, value);
		 RedisClient redisClient = RedisClient.getInstance();
		 redisClient.put(CookieHolder.getSessionId(), key, value, sessionTimeOut*60);
	}

	@Override
	public String getAttr(String key) {
		String attr = RedisClient.getInstance().mapGet(CookieHolder.getSessionId(), key,sessionTimeOut*60);
		return attr;
	}

	@Override
	public void refresh() {
		RedisClient.getInstance().setExpire(CookieHolder.getSessionId(), sessionTimeOut*60);
	}

	@Override
	public Integer getSessionTimeOut() {
		return sessionTimeOut;
	}

	public void setSessionTimeOut(Integer sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	@Override
	public void destroy() {
		 RedisClient redisClient = RedisClient.getInstance();
		 redisClient.delete(CookieHolder.getSessionId());
	}

}
