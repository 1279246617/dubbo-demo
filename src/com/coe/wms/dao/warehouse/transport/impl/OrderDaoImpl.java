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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.transport.IOrderDao;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderStatus.OrderStatusCode;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("orderDao")
public class OrderDaoImpl implements IOrderDao {

	Logger logger = Logger.getLogger(OrderDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOrder(final Order order) {
		final String sql = "insert into w_t_order (warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,out_warehouse_weight,weight_code,trade_remark,logistics_remark,tracking_no) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, order.getWarehouseId());
				ps.setLong(2, order.getUserIdOfCustomer());

				if (order.getUserIdOfOperator() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setDouble(3, order.getUserIdOfOperator());
				}
				ps.setString(4, order.getShipwayCode());
				ps.setLong(5, order.getCreatedTime());
				ps.setString(6, order.getStatus());
				ps.setString(7, order.getRemark());
				ps.setString(8, order.getCustomerReferenceNo());
				if (order.getOutWarehouseWeight() == null) {
					ps.setNull(9, Types.DOUBLE);
				} else {
					ps.setDouble(9, order.getOutWarehouseWeight());
				}
				ps.setString(10, order.getWeightCode());
				ps.setString(11, order.getTradeRemark());
				ps.setString(12, order.getLogisticsRemark());
				ps.setString(13, order.getTrackingNo());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public Order getOrderById(Long orderId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,callback_send_weight_is_success,callback_send_weigh_count,callback_send_status_is_success,callback_send_status_count,out_warehouse_weight,weight_code,trade_remark,logistics_remark,tracking_no from w_t_order where id ="
				+ orderId;
		Order order = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Order>(Order.class));
		return order;
	}

	/**
	 * 
	 * 参数一律使用实体类加Map .
	 */
	@Override
	public List<Order> findOrder(Order order, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,callback_send_weight_is_success,callback_send_weigh_count,callback_send_status_is_success,callback_send_status_count,out_warehouse_weight,weight_code,trade_remark,logistics_remark,tracking_no from w_t_order where 1=1 ");
		if (moreParam != null && StringUtil.isNotNull(moreParam.get("nos"))) {
			// 按单号 批量查询 开始---------------
			String noType = moreParam.get("noType");
			String nos = moreParam.get("nos");
			String noArray[] = StringUtil.splitW(nos);
			String in = "";
			for (String no : noArray) {
				in += ("'" + no + "',");
			}
			if (in.endsWith(",")) {
				in = in.substring(0, in.length() - 1);
			}
			if (StringUtil.isEqual(noType, "1")) {
				sb.append(" and customer_reference_no in(" + in + ")");
			}
			if (StringUtil.isEqual(noType, "2")) {
				sb.append(" and tracking_no in(" + in + ")");
			}
			// 按单号 批量查询 结束------------
		} else {
			if (order != null) {
				if (order.getId() != null) {
					sb.append(" and id = " + order.getId());
				}
				if (order.getUserIdOfCustomer() != null) {
					sb.append(" and user_id_of_customer = " + order.getUserIdOfCustomer());
				}
				if (order.getUserIdOfOperator() != null) {
					sb.append(" and user_id_of_operator = " + order.getUserIdOfOperator());
				}
				if (StringUtil.isNotNull(order.getShipwayCode())) {
					sb.append(" and shipway_code = '" + order.getShipwayCode() + "' ");
				}
				if (order.getCreatedTime() != null) {
					sb.append(" and created_time = " + order.getCreatedTime());
				}
				if (StringUtil.isNotNull(order.getStatus())) {
					sb.append(" and status = '" + order.getStatus() + "' ");
				}
				if (StringUtil.isNotNull(order.getRemark())) {
					sb.append(" and remark = '" + order.getRemark() + "' ");
				}
				if (StringUtil.isNotNull(order.getCallbackSendWeightIsSuccess())) {
					sb.append(" and callback_send_weight_is_success = '" + order.getCallbackSendWeightIsSuccess() + "' ");
				}
				if (order.getCallbackSendWeighCount() != null) {
					sb.append(" and callback_send_weigh_count = " + order.getCallbackSendWeighCount());
				}

				if (StringUtil.isNotNull(order.getCallbackSendStatusIsSuccess())) {
					sb.append(" and callback_send_status_is_success = '" + order.getCallbackSendStatusIsSuccess() + "' ");
				}
				if (order.getCallbackSendStatusCount() != null) {
					sb.append(" and callback_send_status_count = " + order.getCallbackSendStatusCount());
				}
				if (order.getOutWarehouseWeight() != null) {
					sb.append(" and out_warehouse_weight = " + order.getOutWarehouseWeight());
				}
				if (order.getWeightCode() != null) {
					sb.append(" and weight_code = '" + order.getWeightCode() + "'");
				}
				if (order.getTradeRemark() != null) {
					sb.append(" and trade_remark = '" + order.getTradeRemark() + "'");
				}
				if (order.getLogisticsRemark() != null) {
					sb.append(" and logistics_remark = '" + order.getLogisticsRemark() + "'");
				}
				if (order.getTrackingNo() != null) {
					sb.append(" and tracking_no = '" + order.getTrackingNo() + "'");
				}
				if (StringUtil.isNotNull(order.getCustomerReferenceNo())) {
					sb.append(" and customer_reference_no = '" + order.getCustomerReferenceNo() + "' ");
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
		}
		String sql = sb.toString();
		List<Order> orderList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(Order.class));
		return orderList;
	}

	@Override
	public Long countOrder(Order order, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_t_order where 1=1");
		if (moreParam != null && StringUtil.isNotNull(moreParam.get("nos"))) {
			// 按单号 批量查询 开始---------------
			String noType = moreParam.get("noType");
			String nos = moreParam.get("nos");
			String noArray[] = StringUtil.splitW(nos);
			String in = "";
			for (String no : noArray) {
				in += ("'" + no + "',");
			}
			if (in.endsWith(",")) {
				in = in.substring(0, in.length() - 1);
			}
			if (StringUtil.isEqual(noType, "1")) {
				sb.append(" and customer_reference_no in(" + in + ")");
			}
			if (StringUtil.isEqual(noType, "2")) {
				sb.append(" and tracking_no in(" + in + ")");
			}
			// 按单号 批量查询 结束------------
		} else {
			if (order != null) {
				if (order.getId() != null) {
					sb.append(" and id = " + order.getId());
				}
				if (order.getUserIdOfCustomer() != null) {
					sb.append(" and user_id_of_customer = " + order.getUserIdOfCustomer());
				}
				if (order.getUserIdOfOperator() != null) {
					sb.append(" and user_id_of_operator = " + order.getUserIdOfOperator());
				}
				if (StringUtil.isNotNull(order.getShipwayCode())) {
					sb.append(" and shipway_code = '" + order.getShipwayCode() + "' ");
				}
				if (order.getCreatedTime() != null) {
					sb.append(" and created_time = " + order.getCreatedTime());
				}
				if (StringUtil.isNotNull(order.getStatus())) {
					sb.append(" and status = '" + order.getStatus() + "' ");
				}
				if (StringUtil.isNotNull(order.getRemark())) {
					sb.append(" and remark = '" + order.getRemark() + "' ");
				}
				if (StringUtil.isNotNull(order.getCallbackSendWeightIsSuccess())) {
					sb.append(" and callback_send_weight_is_success = '" + order.getCallbackSendWeightIsSuccess() + "' ");
				}
				if (order.getCallbackSendWeighCount() != null) {
					sb.append(" and callback_send_weigh_count = " + order.getCallbackSendWeighCount());
				}

				if (StringUtil.isNotNull(order.getCallbackSendStatusIsSuccess())) {
					sb.append(" and callback_send_status_is_success = '" + order.getCallbackSendStatusIsSuccess() + "' ");
				}
				if (order.getCallbackSendStatusCount() != null) {
					sb.append(" and callback_send_status_count = " + order.getCallbackSendStatusCount());
				}
				if (order.getOutWarehouseWeight() != null) {
					sb.append(" and out_warehouse_weight = " + order.getOutWarehouseWeight());
				}
				if (order.getWeightCode() != null) {
					sb.append(" and weight_code = '" + order.getWeightCode() + "'");
				}
				if (order.getTradeRemark() != null) {
					sb.append(" and trade_remark = '" + order.getTradeRemark() + "'");
				}
				if (order.getLogisticsRemark() != null) {
					sb.append(" and logistics_remark = '" + order.getLogisticsRemark() + "'");
				}
				if (order.getTrackingNo() != null) {
					sb.append(" and tracking_no = '" + order.getTrackingNo() + "'");
				}
				if (StringUtil.isNotNull(order.getCustomerReferenceNo())) {
					sb.append(" and customer_reference_no = '" + order.getCustomerReferenceNo() + "' ");
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
		}
		String sql = sb.toString();
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int updateOrderStatus(Long orderId, String newStatus) {
		String sql = "update w_t_order set status = '" + newStatus + "' where id = " + orderId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public String getOrderStatus(Long orderId) {
		String sql = "select status from w_t_order where id = " + orderId;
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	/**
	 * 获取回调未成功的记录id
	 * 
	 * 1,必须有客户id, 2,必须有重量, 3回调未成功 或未回调
	 */
	@Override
	public List<Long> findCallbackSendWeightUnSuccessOrderId() {
		String sql = "select id from w_t_order where status ='" + OrderStatusCode.WSW + "' and (callback_send_weight_is_success = 'N' or  callback_send_weight_is_success is null)";
		List<Long> orderIdList = jdbcTemplate.queryForList(sql, Long.class);
		return orderIdList;
	}

	/**
	 * 获取回调未成功的记录id
	 * 
	 * 1,必须有客户id, 2,必须有重量, 3回调未成功 或未回调
	 */
	@Override
	public List<Long> findCallbackSendStatusUnSuccessOrderId() {
		String sql = "select id from w_t_order where status ='" + OrderStatusCode.SUCCESS + "' and (callback_send_status_is_success = 'N' or  callback_send_status_is_success is null)";
		List<Long> orderIdList = jdbcTemplate.queryForList(sql, Long.class);
		return orderIdList;
	}

	/**
	 * 更新回调顺丰状态
	 * 
	 * @param order
	 * @return
	 */
	@Override
	public int updateOrderCallbackSendWeight(Order order) {
		String sql = "update w_t_order set callback_send_weight_is_success='" + order.getCallbackSendWeightIsSuccess() + "' ,callback_send_weigh_count = " + order.getCallbackSendWeighCount() + " , status='" + order.getStatus() + "' where id="
				+ order.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 更新回调顺丰状态
	 * 
	 * @param order
	 * @return
	 */
	@Override
	public int updateOrderCallbackSendStatus(Order order) {
		String sql = "update w_t_order set callback_send_status_is_success='" + order.getCallbackSendStatusIsSuccess() + "' ,callback_send_status_count = " + order.getCallbackSendStatusCount() + " , status='" + order.getStatus() + "' where id="
				+ order.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 
	 */
	@Override
	public int updateOrderWeight(Order order) {
		String sql = "update w_t_order set out_warehouse_weight=" + order.getOutWarehouseWeight() + ",weight_code='" + order.getWeightCode() + "' , status='" + order.getStatus() + "' where id=" + order.getId();
		return jdbcTemplate.update(sql);
	}
}