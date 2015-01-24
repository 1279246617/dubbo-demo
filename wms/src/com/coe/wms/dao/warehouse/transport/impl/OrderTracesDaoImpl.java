package com.coe.wms.dao.warehouse.transport.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
		final String sql = "insert into w_t_order_traces (order_id,userIdOfCustomer,userIdOfOperator,warehouseId,user_id_of_customer,created_time) values (?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, orderTraces.getOrderId());
				ps.setLong(5, orderTraces.getUserIdOfCustomer());
				ps.setLong(6, orderTraces.getCreatedTime());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public OrderTraces getOrderTracesById(Long orderTracesId) {
		String sql = "select id,order_id,current_warehouse_id,from_warehouse_id,to_warehouse_id,user_id_of_customer,created_time from w_t_order_traces where id =" + orderTracesId;
		List<OrderTraces> orderTracesList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderTraces.class));
		if (orderTracesList != null && orderTracesList.size() > 0) {
			return orderTracesList.get(0);
		}
		return null;
	}

	/**
	 * 
	 * 参数一律使用实体类加Map .
	 */
	@Override
	public List<OrderTraces> findOrderTraces(OrderTraces orderTraces, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,order_id,current_warehouse_id,from_warehouse_id,to_warehouse_id,user_id_of_customer,created_time from w_t_order_traces from w_t_order_traces where 1=1 ");
		if (orderTraces != null) {
			if (orderTraces.getId() != null) {
				sb.append(" and id = " + orderTraces.getId());
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