package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("outWarehouseOrderDao")
public class OutWarehouseOrderDaoImpl implements IOutWarehouseOrderDao {

	Logger logger = Logger.getLogger(OutWarehouseOrderDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOutWarehouseOrder(final OutWarehouseOrder order) {
		final String sql = "insert into w_s_out_warehouse_order (warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,out_warehouse_weight,weight_code) values (?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, order.getWarehouseId() != null ? order.getWarehouseId() : 0);
				ps.setLong(2, order.getUserIdOfCustomer() != null ? order.getUserIdOfCustomer() : 0);
				ps.setLong(3, order.getUserIdOfOperator() != null ? order.getUserIdOfOperator() : 0);
				ps.setString(4, order.getShipwayCode());
				ps.setLong(5, order.getCreatedTime());
				ps.setString(6, order.getStatus());
				ps.setString(7, order.getRemark());
				ps.setString(8, order.getCustomerReferenceNo());
				ps.setDouble(9, order.getOutWarehouseWeight());
				ps.setString(10, order.getWeightCode());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public OutWarehouseOrder getOutWarehouseOrderById(Long outWarehouseOrderId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,callback_send_weight_is_success,callback_send_weigh_count,callback_send_status_is_success,callback_send_status_count,out_warehouse_weight,weight_code from w_s_out_warehouse_order where id ="
				+ outWarehouseOrderId;
		OutWarehouseOrder order = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<OutWarehouseOrder>(OutWarehouseOrder.class));
		return order;
	}

	/**
	 * 查询入库订单
	 * 
	 * 参数一律使用实体类加Map . 节省QueryVO
	 */
	@Override
	public List<OutWarehouseOrder> findOutWarehouseOrder(OutWarehouseOrder outWarehouseOrder, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,callback_send_weight_is_success,callback_send_weigh_count,callback_send_status_is_success,callback_send_status_count,out_warehouse_weight,weight_code from w_s_out_warehouse_order where 1=1 ");
		if (outWarehouseOrder != null) {
			if (outWarehouseOrder.getId() != null) {
				sb.append(" and id = " + outWarehouseOrder.getId());
			}
			if (outWarehouseOrder.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + outWarehouseOrder.getUserIdOfCustomer());
			}
			if (outWarehouseOrder.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + outWarehouseOrder.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(outWarehouseOrder.getShipwayCode())) {
				sb.append(" and shipway_code = '" + outWarehouseOrder.getShipwayCode() + "' ");
			}
			if (outWarehouseOrder.getCreatedTime() != null) {
				sb.append(" and created_time = " + outWarehouseOrder.getCreatedTime());
			}
			if (StringUtil.isNotNull(outWarehouseOrder.getStatus())) {
				sb.append(" and status = '" + outWarehouseOrder.getStatus() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrder.getRemark())) {
				sb.append(" and remark = '" + outWarehouseOrder.getRemark() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrder.getCustomerReferenceNo())) {
				sb.append(" and customer_reference_no = '" + outWarehouseOrder.getCustomerReferenceNo() + "' ");
			}

			if (StringUtil.isNotNull(outWarehouseOrder.getCallbackSendWeightIsSuccess())) {
				sb.append(" and callback_send_weight_is_success = '" + outWarehouseOrder.getCallbackSendWeightIsSuccess() + "' ");
			}
			if (outWarehouseOrder.getCallbackSendWeighCount() != null) {
				sb.append(" and callback_send_weigh_count = " + outWarehouseOrder.getCallbackSendWeighCount());
			}

			if (StringUtil.isNotNull(outWarehouseOrder.getCallbackSendStatusIsSuccess())) {
				sb.append(" and callback_send_status_is_success = '" + outWarehouseOrder.getCallbackSendStatusIsSuccess() + "' ");
			}
			if (outWarehouseOrder.getCallbackSendStatusCount() != null) {
				sb.append(" and callback_send_status_count = " + outWarehouseOrder.getCallbackSendStatusCount());
			}

			if (outWarehouseOrder.getOutWarehouseWeight() != null) {
				sb.append(" and out_warehouse_weight = " + outWarehouseOrder.getOutWarehouseWeight());
			}
			if (outWarehouseOrder.getWeightCode() != null) {
				sb.append(" and weight_code = " + outWarehouseOrder.getWeightCode());
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
		logger.info("查询出库订单sql:" + sql);
		List<OutWarehouseOrder> outWarehouseOrderList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(OutWarehouseOrder.class));
		return outWarehouseOrderList;
	}

	@Override
	public Long countOutWarehouseOrder(OutWarehouseOrder outWarehouseOrder, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_s_out_warehouse_order where 1=1 ");
		if (outWarehouseOrder != null) {
			if (outWarehouseOrder.getId() != null) {
				sb.append(" and id = " + outWarehouseOrder.getId());
			}
			if (outWarehouseOrder.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + outWarehouseOrder.getUserIdOfCustomer());
			}
			if (outWarehouseOrder.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + outWarehouseOrder.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(outWarehouseOrder.getShipwayCode())) {
				sb.append(" and shipway_code = '" + outWarehouseOrder.getShipwayCode() + "' ");
			}
			if (outWarehouseOrder.getCreatedTime() != null) {
				sb.append(" and created_time = " + outWarehouseOrder.getCreatedTime());
			}
			if (StringUtil.isNotNull(outWarehouseOrder.getStatus())) {
				sb.append(" and status = '" + outWarehouseOrder.getStatus() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrder.getRemark())) {
				sb.append(" and remark = '" + outWarehouseOrder.getRemark() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrder.getCustomerReferenceNo())) {
				sb.append(" and customer_reference_no = '" + outWarehouseOrder.getCustomerReferenceNo() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrder.getCallbackSendWeightIsSuccess())) {
				sb.append(" and callback_send_weight_is_success = '" + outWarehouseOrder.getCallbackSendWeightIsSuccess() + "' ");
			}
			if (outWarehouseOrder.getCallbackSendWeighCount() != null) {
				sb.append(" and callback_send_weigh_count = " + outWarehouseOrder.getCallbackSendWeighCount());
			}

			if (StringUtil.isNotNull(outWarehouseOrder.getCallbackSendStatusIsSuccess())) {
				sb.append(" and callback_send_status_is_success = '" + outWarehouseOrder.getCallbackSendStatusIsSuccess() + "' ");
			}
			if (outWarehouseOrder.getCallbackSendStatusCount() != null) {
				sb.append(" and callback_send_status_count = " + outWarehouseOrder.getCallbackSendStatusCount());
			}
			if (outWarehouseOrder.getOutWarehouseWeight() != null) {
				sb.append(" and out_warehouse_weight = " + outWarehouseOrder.getOutWarehouseWeight());
			}
			if (outWarehouseOrder.getWeightCode() != null) {
				sb.append(" and weight_code = " + outWarehouseOrder.getWeightCode());
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
		logger.info("统计出库订单sql:" + sql);
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int updateOutWarehouseOrderStatus(Long orderId, String newStatus) {
		String sql = "update w_s_out_warehouse_order set status = '" + newStatus + "' where id = " + orderId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public String getOutWarehouseOrderStatus(Long orderId) {
		String sql = "select status from w_s_out_warehouse_order where id = " + orderId;
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	/**
	 * 获取回调未成功的记录id
	 * 
	 * 1,必须有客户id, 2,必须有重量, 3回调未成功 或未回调
	 */
	@Override
	public List<Long> findCallbackSendWeightUnSuccessOrderId() {
		String sql = "select id from w_s_out_warehouse_order where status ='"+OutWarehouseOrderStatusCode.WSW+"' and (callback_send_weight_is_success = 'N' or  callback_send_weight_is_success is null)";
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
		String sql = "select id from w_s_out_warehouse_order where status ='"+OutWarehouseOrderStatusCode.SUCCESS+"' and (callback_send_status_is_success = 'N' or  callback_send_status_is_success is null)";
		List<Long> orderIdList = jdbcTemplate.queryForList(sql, Long.class);
		return orderIdList;
	}

	/**
	 * 更新回调顺丰状态
	 * 
	 * @param outWarehouseOrder
	 * @return
	 */
	@Override
	public int updateOutWarehouseOrderCallbackSendWeight(OutWarehouseOrder outWarehouseOrder) {
		String sql = "update w_s_out_warehouse_order set callback_send_weight_is_success='"
				+ outWarehouseOrder.getCallbackSendWeightIsSuccess() + "' ,callback_send_weigh_count = "
				+ outWarehouseOrder.getCallbackSendWeighCount() + " where id=" + outWarehouseOrder.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 更新回调顺丰状态
	 * 
	 * @param outWarehouseOrder
	 * @return
	 */
	@Override
	public int updateOutWarehouseOrderCallbackSendStatus(OutWarehouseOrder outWarehouseOrder) {
		String sql = "update w_s_out_warehouse_order set callback_send_status_is_success='"
				+ outWarehouseOrder.getCallbackSendStatusIsSuccess() + "' ,callback_send_status_count = "
				+ outWarehouseOrder.getCallbackSendStatusCount() + " where id=" + outWarehouseOrder.getId();
		return jdbcTemplate.update(sql);
	}
}