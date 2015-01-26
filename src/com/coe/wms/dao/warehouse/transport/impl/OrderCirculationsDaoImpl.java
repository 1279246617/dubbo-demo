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
import com.coe.wms.dao.warehouse.transport.IOrderCirculationsDao;
import com.coe.wms.model.warehouse.transport.OrderCirculations;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("orderCirculationsDao")
public class OrderCirculationsDaoImpl implements IOrderCirculationsDao {

	Logger logger = Logger.getLogger(OrderCirculationsDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOrderCirculations(final OrderCirculations orderCirculations) {
		final String sql = "insert into w_t_order_circulations (order_id,current_warehouse_id,from_warehouse_id,to_warehouse_id,user_id_of_customer,created_time) values (?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, orderCirculations.getOrderId());
				ps.setLong(2, orderCirculations.getCurrentWarehouseId());
				if (orderCirculations.getFromWarehouseId() != null) {
					ps.setLong(3, orderCirculations.getFromWarehouseId());
				} else {
					ps.setNull(3, Types.BIGINT);
				}
				if (orderCirculations.getToWarehouseId() != null) {
					ps.setLong(4, orderCirculations.getToWarehouseId());
				} else {
					ps.setNull(4, Types.BIGINT);
				}
				ps.setLong(5, orderCirculations.getUserIdOfCustomer());
				ps.setLong(6, orderCirculations.getCreatedTime());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public OrderCirculations getOrderCirculationsById(Long orderCirculationsId) {
		String sql = "select id,order_id,current_warehouse_id,from_warehouse_id,to_warehouse_id,user_id_of_customer,created_time from w_t_order_circulations where id =" + orderCirculationsId;
		List<OrderCirculations> orderCirculationsList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderCirculations.class));
		if (orderCirculationsList != null && orderCirculationsList.size() > 0) {
			return orderCirculationsList.get(0);
		}
		return null;
	}

	/**
	 * 
	 * 
	 */
	@Override
	public List<OrderCirculations> findOrderCirculations(OrderCirculations orderCirculations, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,order_id,current_warehouse_id,from_warehouse_id,to_warehouse_id,user_id_of_customer,created_time   from w_t_order_circulations where 1=1 ");
		if (orderCirculations != null) {
			if (orderCirculations.getId() != null) {
				sb.append(" and id = " + orderCirculations.getId());
			}
			if (orderCirculations.getOrderId() != null) {
				sb.append(" and order_id = " + orderCirculations.getOrderId());
			}
			if (orderCirculations.getCurrentWarehouseId() != null) {
				sb.append(" and current_warehouse_id = " + orderCirculations.getCurrentWarehouseId());
			}
			if (orderCirculations.getFromWarehouseId() != null) {
				sb.append(" and from_warehouse_id = " + orderCirculations.getFromWarehouseId());
			}
			if (orderCirculations.getToWarehouseId() != null) {
				sb.append(" and to_warehouse_id = " + orderCirculations.getToWarehouseId());
			}
			if (orderCirculations.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + orderCirculations.getUserIdOfCustomer());
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
		List<OrderCirculations> OrderCirculationsList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderCirculations.class));
		return OrderCirculationsList;
	}

	@Override
	public Long countOrderCirculations(OrderCirculations orderCirculations, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from w_t_order_circulations where 1=1");
		if (orderCirculations != null) {
			if (orderCirculations.getId() != null) {
				sb.append(" and id = " + orderCirculations.getId());
			}
			if (orderCirculations.getOrderId() != null) {
				sb.append(" and order_id = " + orderCirculations.getOrderId());
			}
			if (orderCirculations.getCurrentWarehouseId() != null) {
				sb.append(" and current_warehouse_id = " + orderCirculations.getCurrentWarehouseId());
			}
			if (orderCirculations.getFromWarehouseId() != null) {
				sb.append(" and from_warehouse_id = " + orderCirculations.getFromWarehouseId());
			}
			if (orderCirculations.getToWarehouseId() != null) {
				sb.append(" and to_warehouse_id = " + orderCirculations.getToWarehouseId());
			}
			if (orderCirculations.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + orderCirculations.getUserIdOfCustomer());
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