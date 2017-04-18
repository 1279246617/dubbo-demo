package org.mybatis.plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.plugin.exception.PluginPropertyUndefinedException;
import org.mybatis.plugin.model.DBTypeEnum;
import org.mybatis.plugin.model.QueryParam;
import org.mybatis.plugin.util.GenMysqlCountUtil;
import org.mybatis.plugin.util.ReflectUtil;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PagePlugin implements Interceptor {

	/** 数据库方言 */
	private static String dialect = "";

	/** mapper.xml中需要拦截的ID(正则匹配) */
	private static String pageSqlId = "";

	public Object intercept(Invocation ivk) throws Throwable {

		if (!(ivk.getTarget() instanceof RoutingStatementHandler)) {
			return ivk.proceed();
		}

		RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
		BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtil.getValueByFieldName(statementHandler, "delegate");
		MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getValueByFieldName(delegate, "mappedStatement");

		// 只拦截需要分页的SQL
		if (!mappedStatement.getId().matches(pageSqlId)) {
			return ivk.proceed();
		}

		BoundSql boundSql = delegate.getBoundSql();
		// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
		Object parameterObject = boundSql.getParameterObject();
		if (parameterObject == null) {
			throw new NullPointerException("parameterObject尚未实例化！");
		}
		if (!(parameterObject instanceof QueryParam)) {
			// 如查询条件封装类非继承QueryParam 则不分页 modify by yechao 20170319

			// throw new NotQueryParamSubclassException(parameterObject.getClass().getName());
			return ivk.proceed();
		}
		QueryParam queryParam = (QueryParam) parameterObject;
		if (queryParam.getLimit() <= 0) {
			queryParam.setLimit(Integer.MAX_VALUE);
		}
		Connection connection = (Connection) ivk.getArgs()[0];
		String sql = boundSql.getSql();
		// 统计总行数sql
		String countSql = generateCountSql(sql);
		if (countSql != null) {
			PreparedStatement countStmt = connection.prepareStatement(countSql);
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
			// count. 支持<foreach>循环时定义的入参(__frch_item_0, __frch_item_1 ...)
			// modify by yepeng 20160610
			MetaObject countBsObject = SystemMetaObject.forObject(countBS);
			MetaObject boundSqlObject = SystemMetaObject.forObject(boundSql);
			countBsObject.setValue("metaParameters", boundSqlObject.getValue("metaParameters"));
			setParameters(countStmt, mappedStatement, countBS, parameterObject);
			ResultSet rs = countStmt.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			countStmt.close();
			queryParam.setCount(count);
		}
		// 分页查询sql
		String pageSql = generatePageSql(sql, queryParam);
		ReflectUtil.setValueByFieldName(boundSql, "sql", pageSql); // 将分页sql语句反射回BoundSql.
		return ivk.proceed();
	}

	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter. DefaultParameterHandler
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException(
								"There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}

	private String generateCountSql(String sql) {

		// MYSQL处理
		if (DBTypeEnum.MYSQL.name().equals(dialect)) {
			return GenMysqlCountUtil.genCountSql(sql);
		}

		// ORACLE处理
		if (DBTypeEnum.ORACLE.name().equals(dialect)) {
			return "select count(0) from (" + sql + ") tmp_count";
		}

		return null;
	}

	/**
	 * 根据数据库方言，生成特定的分页sql
	 * 
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePageSql(String sql, QueryParam queryParam) {
		if (queryParam == null || StringUtils.isBlank(dialect)) {
			return sql;
		}
		StringBuffer pageSql = new StringBuffer();

		if (DBTypeEnum.MYSQL.name().equals(dialect)) {
			pageSql.append(sql);
			// 此处应判断sql是否本身已自带limit语句 modify by yechao 20170319
			if (!GenMysqlCountUtil.pLimit.matcher(sql).find()) {
				pageSql.append(" limit " + queryParam.getStart() + "," + queryParam.getLimit());
			}
			return pageSql.toString();
		}

		if (DBTypeEnum.ORACLE.name().equals(dialect)) {
			pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
			pageSql.append(sql);
			pageSql.append(") tmp_tb where 1=1");
			if (queryParam.getLimit() > 0) {
				pageSql.append(" and ROWNUM<=");
				pageSql.append(queryParam.getStart() + queryParam.getLimit());
			}
			pageSql.append(") where row_id>");
			pageSql.append(queryParam.getStart());
			return pageSql.toString();
		}
		return pageSql.toString();
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (StringUtils.isBlank(dialect)) {
			try {
				throw new PluginPropertyUndefinedException("dialect");
			} catch (PluginPropertyUndefinedException e) {
				throw new RuntimeException(e);
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (StringUtils.isBlank(pageSqlId)) {
			try {
				throw new PluginPropertyUndefinedException("pageSqlId");
			} catch (PluginPropertyUndefinedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
