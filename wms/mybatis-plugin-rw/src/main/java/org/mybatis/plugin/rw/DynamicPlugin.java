package org.mybatis.plugin.rw;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * 拦截器
 * 
 * @ClassName: DynamicPlugin
 * @author yechao
 * @date 2017年3月2日 下午3:33:10
 * @Description: TODO
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class DynamicPlugin implements Interceptor {
	public Object intercept(Invocation invocation) throws Throwable {
		Connection conn = (Connection) invocation.getArgs()[0];
		// 如果是采用了代理连接,则路由数据源
		if (conn instanceof ConnectionProxy) {
			StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
			// 按SQL类型 查询或更新 选择数据源
			MappedStatement mappedStatement = null;
			if (statementHandler instanceof RoutingStatementHandler) {
				StatementHandler delegate = (StatementHandler) ReflectionUtils.getFieldValue(statementHandler, "delegate");
				mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(delegate, "mappedStatement");
			} else {
				mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(statementHandler, "mappedStatement");
			}
			String key = AbstractDynamicDataSourceProxy.WRITE;

			if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT) {
				key = AbstractDynamicDataSourceProxy.READ;
			} else {
				key = AbstractDynamicDataSourceProxy.WRITE;
			}

			ConnectionProxy connectionProxy = (ConnectionProxy) conn;
			connectionProxy.getTargetConnection(key);
		}
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		// NOOP
	}
}
