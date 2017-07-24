package com.coe.wms.common.core.db;

import com.coe.wms.common.core.cache.redis.RedisClient;

/**
 * redis自增长 
* @ClassName: IdWorkerForRedis  
* @author lqg  
* @date 2017年7月21日 下午5:25:10  
* @Description: TODO
 */
public class IdWorkerForRedis {

	//自增长key前缀
	private  static final String prefixGrowth="prefixGrowth_";
	public static Long netId(String key){
		RedisClient redisClient=RedisClient.getInstance();
		return redisClient.nextId(prefixGrowth+key);
	}
}
