package com.coe.wms.dao.warehouse.transport.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.transport.IOrderTracesDao;
import com.coe.wms.model.warehouse.transport.OrderTraces;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("orderTracesDao")
public class OrderTracesDaoImpl implements IOrderTracesDao {

	Logger logger = Logger.getLogger(OrderTracesDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOrderTraces(final OrderTraces orderTraces) {
		final String sql = "insert into w_t_order_traces (order_id,user_id_of_customer,user_id_of_operator,warehouse_id,location,event,event_type,created_time,remark) values (?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, orderTraces.getOrderId());
				ps.setLong(2, orderTraces.getUserIdOfCustomer());
				if (orderTraces.getUserIdOfOperator() != null) {
					ps.setLong(3, orderTraces.getUserIdOfOperator());
				} else {
					ps.setNull(3, Types.BIGINT);
				}
				if (orderTraces.getWarehouseId() != null) {
					ps.setLong(4, orderTraces.getWarehouseId());
				} else {
					ps.setNull(4, Types.BIGINT);
				}
				ps.setString(5, orderTraces.getLocation());
				ps.setString(6, orderTraces.getEvent());
				ps.setString(7, orderTraces.getEventType());
				ps.setLong(8, orderTraces.getCreatedTime());
				ps.setString(9, orderTraces.getRemark());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public OrderTraces getOrderTracesById(Long orderTracesId) {
		String sql = "select id,order_id,user_id_of_customer,user_id_of_operator,warehouse_id,location,event,event_type,created_time,remark from w_t_order_traces where id =" + orderTracesId;
		List<OrderTraces> orderTracesList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderTraces.class));
		if (orderTracesList != null && orderTracesList.size() > 0) {
			return orderTracesList.get(0);
		}
		return null;
	}

	/**
	 * 
	 * 
	 */
	@Override
	public List<OrderTraces> findOrderTraces(OrderTraces orderTraces, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,order_id,user_id_of_customer,user_id_of_operator,warehouse_id,location,event,event_type,created_time,remark from w_t_order_traces where 1=1 ");
		if (orderTraces != null) {
			if (orderTraces.getId() != null) {
				sb.append(" and id = " + orderTraces.getId());
			}
			if (orderTraces.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + orderTraces.getUserIdOfCustomer());
			}
			if (orderTraces.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + orderTraces.getUserIdOfOperator());
			}
			if (orderTraces.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + orderTraces.getWarehouseId());
			}
			if (StringUtil.isNotNull(orderTraces.getLocation())) {
				sb.append(" and location = '" + orderTraces.getLocation() + "'");
			}
			if (StringUtil.isNotNull(orderTraces.getEvent())) {
				sb.append(" and event = '" + orderTraces.getEvent() + "'");
			}
			if (StringUtil.isNotNull(orderTraces.getEventType())) {
				sb.append(" and event_type = '" + orderTraces.getEventType() + "'");
			}
			if (orderTraces.getCreatedTime() != null) {
				sb.append(" and created_time = " + orderTraces.getCreatedTime());
			}
			if (StringUtil.isNotNull(orderTraces.getRemark())) {
				sb.append(" and remark = '" + orderTraces.getRemark() + "'");
			}
		}
		if (moreParam != null) {
			if (moreParam.get("createdTimeStart") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time >= " + date.getTime());
				}
			}
			if (moreParam.get("createdTimeEnd") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeEnd"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time <= " + date.getTime());
				}
			}
		}
		if (page != null) {
			// 分页sql
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		List<OrderTraces> OrderTracesList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderTraces.class));
		return OrderTracesList;
	}

	@Override
	public Long countOrderTraces(OrderTraces orderTraces, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from w_t_order_traces where 1=1");
		if (orderTraces != null) {
			if (orderTraces.getId() != null) {
				sb.append(" and id = " + orderTraces.getId());
			}
			if (orderTraces.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + orderTraces.getUserIdOfCustomer());
			}
			if (orderTraces.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + orderTraces.getUserIdOfOperator());
			}
			if (orderTraces.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + orderTraces.getWarehouseId());
			}
			if (StringUtil.isNotNull(orderTraces.getLocation())) {
				sb.append(" and location = '" + orderTraces.getLocation() + "'");
			}
			if (StringUtil.isNotNull(orderTraces.getEvent())) {
				sb.append(" and event = '" + orderTraces.getEvent() + "'");
			}
			if (StringUtil.isNotNull(orderTraces.getEventType())) {
				sb.append(" and event_type = '" + orderTraces.getEventType() + "'");
			}
			if (orderTraces.getCreatedTime() != null) {
				sb.append(" and created_time = " + orderTraces.getCreatedTime());
			}
			if (StringUtil.isNotNull(orderTraces.getRemark())) {
				sb.append(" and remark = '" + orderTraces.getRemark() + "'");
			}
		}
		if (moreParam != null) {
			if (moreParam.get("createdTimeStart") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time >= " + date.getTime());
				}
			}
			if (moreParam.get("createdTimeEnd") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeEnd"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time <= " + date.getTime());
				}
			}
		}
		String sql = sb.toString();
		return jdbcTemplate.queryForObject(sql, Long.class);
	}
}