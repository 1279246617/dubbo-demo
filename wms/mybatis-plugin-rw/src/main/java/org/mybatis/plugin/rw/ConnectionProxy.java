package org.mybatis.plugin.rw;

import java.sql.Connection;

/**
 * 连接代理
 * 
 * @ClassName: ConnectionProxy
 * @author yechao
 * @date 2017年3月2日 上午11:36:30
 * @Description: TODO
 */
public interface ConnectionProxy extends Connection {
	/**
	 * 根据传入的读写分离需要的key路由得到正确的connection
	 * 
	 * @param key
	 *            数据源标识
	 * @return
	 */
	Connection getTargetConnection(String key);
}
