package com.coe.message.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**Jedis操作Redis工具类*/
public class JedisUtil {
	private JedisPool jedisPool = null;

	/**
	 * 传入ip和端口号构建连接池
	 * @param ip Redis服务器ip
	 * @param port Redis端口号
	 */
	public JedisUtil(String ip, int port) {
		if (jedisPool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(RedisServerConstant.MAX_IDLE);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(RedisServerConstant.MAX_WAIT_MILLIS);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(true);
			jedisPool = new JedisPool(config, ip, port, RedisServerConstant.MAX_WAIT_MILLIS);
		}
	}

	/**
	 * 传入配置对象，ip，端口号构建连接池
	 * @param config JedisPoolConfig实例
	 * @param ip Redis服务器ip
	 * @param prot 端口号
	 */
	public JedisUtil(JedisPoolConfig config, String ip, int prot) {
		if (jedisPool == null) {
			jedisPool = new JedisPool(config, ip, prot, RedisServerConstant.MAX_WAIT_MILLIS);
		}
	}

	/**
	 * 传入配置对象，ip，端口号,超时时间构建连接池
	 * @param config JedisPoolConfig实例
	 * @param ip Redis服务器ip
	 * @param prot 端口号
	 * @param timeout 超时时间
	 */
	public JedisUtil(JedisPoolConfig config, String ip, int prot, int timeout) {
		if (jedisPool == null) {
			jedisPool = new JedisPool(config, ip, prot, timeout);
		}
	}

	/**获取Jedis实例*/
	public static Jedis getJedis() {
		JedisUtil jedisUtil = new JedisUtil(RedisServerConstant.IP, RedisServerConstant.PORT);
		JedisPool jedisPool = jedisUtil.getJedisPool();
		Jedis jedis = jedisPool.getResource();
		return jedis;

	}

	/**
	 * 存入键值对数据
	 * @param key 键
	 * @param value 值（任何格式的字符串）
	 */
	public static void setString(String key, String value) {
		Jedis jedis = getJedis();
		jedis.set(key, value);
	}

	/**
	 * 根据键获取值
	 * @param key 键值
	 * @return 键对应的值
	 */
	public static String getString(String key) {
		Jedis jedis = getJedis();
		return jedis.get(key);
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
}
