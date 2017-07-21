package com.coe.wms.common.core.cache.redis;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @ClassName: RedisUtil
 * @author yechao
 * @date 2017年5月2日 下午2:35:24
 * @Description: TODO
 */
public class RedisClient {

	private static final Logger logger = LoggerFactory.getLogger(RedisClient.class);

	private JedisPool jedisPool = null;

	public RedisClient() {
		initialPool();
	}

	private static RedisClient Singleton = new RedisClient();

	/**
	 * 获取redis工具类实例
	 * 
	 * @return
	 */
	public static RedisClient getInstance() {
		return Singleton;
	}

	/**
	 * 初始化非集群连接池
	 */
	private void initialPool() {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("redis.properties");

			// Redis服务器IP
			String host = properties.getProperty("host");

			// Redis的端口号
			int port = Integer.valueOf(properties.getProperty("port"));

			// 访问密码
			String auth = properties.getProperty("auth");

			// 可用连接最大数
			int maxTotal = Integer.valueOf(properties.getProperty("max_total"));

			// pool最多有多少个空闲连接
			int maxIdle = Integer.valueOf(properties.getProperty("max_idle"));

			// 等待可用连接的最大时间，单位ms
			int maxWait = Integer.valueOf(properties.getProperty("max_wait"));

			// 操作超时时间 单位ms
			int timeout = Integer.valueOf(properties.getProperty("timeout"));

			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
			boolean testOnBorrow = true;

			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(maxTotal);
			config.setMaxIdle(maxIdle);
			config.setTestOnBorrow(testOnBorrow);
			config.setMaxWaitMillis(maxWait);
			config.setTestWhileIdle(true);
			// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
			config.setMinEvictableIdleTimeMillis(60000L);
			// 毫秒检查一次连接池中空闲的连接
			config.setTimeBetweenEvictionRunsMillis(30000L);

			jedisPool = new JedisPool(config, host, port, timeout, auth);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Jedis getJedis() {
		return jedisPool.getResource();
	}

	/**
	 * 设置数据
	 * 
	 * @param key
	 * @param value
	 */
	public void setString(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 设置数据
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 *            单位秒
	 */
	public void setString(String key, String value, int expire) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.set(key, value);
			jedis.expire(key, expire);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 删除keys对应的记录,可以是多个key
	 *
	 * @param keys
	 * @return 删除的记录数
	 */
	public long delete(String... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.del(keys);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
		return 0l;
	}

	/**
	 * 设置对象类型数据
	 * 
	 * @param objectKey
	 * @param objectValue
	 */
	public void setObject(Object objectKey, Object objectValue) {
		byte[] key = SerializeUtil.serialize(objectKey);
		byte[] value = SerializeUtil.serialize(objectValue);
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 设置对象类型数据,有过期时间
	 * 
	 * @param objectKey
	 * @param objectValue
	 * @param expire
	 *            单位秒
	 */
	public void setObject(Object objectKey, Object objectValue, int expire) {
		byte[] key = SerializeUtil.serialize(objectKey);
		byte[] value = SerializeUtil.serialize(objectValue);
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
			jedis.expire(key, expire);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 获取数据
	 * 
	 * @param objectKey
	 * @return
	 */
	public Object getObject(Object objectKey) {
		byte[] key = SerializeUtil.serialize(objectKey);
		byte[] value = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
		if (value == null) {
			return null;
		}
		return SerializeUtil.unSerialize(value);
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param expire
	 */
	public void setExpire(String key, int expire) {
		getJedis().expire(key, expire);
	}

	/**
	 * 设置过期时间
	 * 
	 * @param objectKey
	 * @param expire
	 */
	public void setExpire(Object objectKey, int expire) {
		byte[] key = SerializeUtil.serialize(objectKey);
		getJedis().expire(key, expire);
	}

	/**
	 * 删除keys对应的记录,可以是多个key
	 *
	 * @param keys
	 * @return 删除的记录数
	 */
	public long delete(byte[]... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.del(keys);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
		return 0l;
	}

	/**
	 * 查找所有匹配给定的模式的键
	 *
	 * @param pattern
	 *            的表达式,*表示多个，？表示一个
	 */
	public Set<String> keys(String pattern) {
		Jedis jedis = null;
		Set<String> set = null;
		try {
			jedis = getJedis();
			set = jedis.keys(pattern);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
		return set;
	}

	/**
	 * 清空全部数据
	 * 
	 * @return
	 */
	public String flushAll() {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.flushAll();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
		return "";
	}
	
	/**
	 * 保存 map
	 * 
	 * @param key
	 * @param k
	 * @param v
	 * @return
	 */
	public	void put(String key, String k, String v,Integer expire){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hset(key, k, v);
			if(expire!=null)
			  jedis.expire(key, expire);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
		
		
	}

	/**
	 * 批量保存map
	 * 
	 * @param key
	 * @param kv
	 * @return
	 */
	public	void putAll(String key, Map<String, String> kv,Integer expire){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			
			Set<Entry<String, String>> kvs = kv.entrySet();
			for (Entry<String, String> entry : kvs) {
				jedis.hset(key, entry.getKey(), entry.getValue());
			}
			if(expire!=null)
			 jedis.expire(key, expire);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
		} finally {
			returnResource(jedis);
		}
		
		
	}

	/**
	 * 从map中取
	 * 
	 * @param key
	 * @param k
	 * @return
	 */
	public String mapGet(String key, String k,Integer expire){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String value = jedis.hget(key, k);
			if(expire!=null)
			 jedis.expire(key, expire);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
			return null;
		} finally {
			returnResource(jedis);
		}
	}

	/**
	 * 取出map
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> mapGetAll(String key){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("" + e);
			return null;
		} finally {
			returnResource(jedis);
		}
	}

	

	/**
	 * 返还到连接池
	 * 
	 * @param redis
	 */
	private void returnResource(Jedis redis) {
		if (redis != null) {
			jedisPool.returnResourceObject(redis);
		}
	}
	
	

}