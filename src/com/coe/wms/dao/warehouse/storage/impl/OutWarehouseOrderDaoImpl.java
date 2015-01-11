package com.coe.wms.dao.warehouse.storage.impl;

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
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.util.Constant;
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
		final String sql = "insert into w_s_out_warehouse_order (warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,out_warehouse_weight,weight_code,trade_remark,logistics_remark,tracking_no,printed_count) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
				ps.setInt(14, 0);
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public OutWarehouseOrder getOutWarehouseOrderById(Long outWarehouseOrderId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,callback_send_weight_is_success,callback_send_weigh_count,callback_send_status_is_success,callback_send_status_count,out_warehouse_weight,weight_code,trade_remark,logistics_remark,tracking_no,printed_count,shipway_extra1,shipway_extra2 from w_s_out_warehouse_order where id ="
				+ outWarehouseOrderId;
		OutWarehouseOrder order = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<OutWarehouseOrder>(OutWarehouseOrder.class));
		return order;
	}

	/**
	 * 查询入库订单
	 * 
	 * 参数一律使用实体类加Map .
	 */
	@Override
	public List<OutWarehouseOrder> findOutWarehouseOrder(OutWarehouseOrder outWarehouseOrder, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,callback_send_weight_is_success,callback_send_weigh_count,callback_send_status_is_success,callback_send_status_count,out_warehouse_weight,weight_code,trade_remark,logistics_remark,tracking_no,printed_count,shipway_extra1,shipway_extra2 from w_s_out_warehouse_order where 1=1 ");
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
		} else if (moreParam != null && StringUtil.isEqual(moreParam.get("trackingNoIsNull"), Constant.Y)) {
			sb.append(" and (tracking_no is null or tracking_no = '') ");
		} else {
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
					sb.append(" and weight_code = '" + outWarehouseOrder.getWeightCode() + "'");
				}
				if (outWarehouseOrder.getTradeRemark() != null) {
					sb.append(" and trade_remark = '" + outWarehouseOrder.getTradeRemark() + "'");
				}
				if (outWarehouseOrder.getLogisticsRemark() != null) {
					sb.append(" and logistics_remark = '" + outWarehouseOrder.getLogisticsRemark() + "'");
				}
				if (outWarehouseOrder.getTrackingNo() != null) {
					sb.append(" and tracking_no = '" + outWarehouseOrder.getTrackingNo() + "'");
				}
				if (StringUtil.isNotNull(outWarehouseOrder.getCustomerReferenceNo())) {
					sb.append(" and customer_reference_no = '" + outWarehouseOrder.getCustomerReferenceNo() + "' ");
				}
				if (outWarehouseOrder.getPrintedCount() != null) {
					sb.append(" and printed_count = " + outWarehouseOrder.getPrintedCount());
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
		if (page != null) {
			// 分页sql
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		logger.debug("查询出库订单sql:" + sql);
		List<OutWarehouseOrder> outWarehouseOrderList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OutWarehouseOrder.class));
		return outWarehouseOrderList;
	}

	@Override
	public Long countOutWarehouseOrder(OutWarehouseOrder outWarehouseOrder, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_s_out_warehouse_order where 1=1");
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
		} else if (moreParam != null && StringUtil.isEqual(moreParam.get("trackingNoIsNull"), Constant.Y)) {
			sb.append(" and (tracking_no is null or tracking_no = '') ");
		} else {
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
					sb.append(" and weight_code = '" + outWarehouseOrder.getWeightCode() + "'");
				}
				if (outWarehouseOrder.getTradeRemark() != null) {
					sb.append(" and trade_remark = '" + outWarehouseOrder.getTradeRemark() + "'");
				}
				if (outWarehouseOrder.getLogisticsRemark() != null) {
					sb.append(" and logistics_remark = '" + outWarehouseOrder.getLogisticsRemark() + "'");
				}
				if (outWarehouseOrder.getTrackingNo() != null) {
					sb.append(" and tracking_no = '" + outWarehouseOrder.getTrackingNo() + "'");
				}
				if (StringUtil.isNotNull(outWarehouseOrder.getCustomerReferenceNo())) {
					sb.append(" and customer_reference_no = '" + outWarehouseOrder.getCustomerReferenceNo() + "' ");
				}
				if (outWarehouseOrder.getPrintedCount() != null) {
					sb.append(" and printed_count = " + outWarehouseOrder.getPrintedCount());
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
		logger.debug("统计出库订单sql:" + sql);
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
		String sql = "select id from w_s_out_warehouse_order where status ='" + OutWarehouseOrderStatusCode.WSW + "' and (callback_send_weight_is_success = 'N' or  callback_send_weight_is_success is null)";
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
		String sql = "select id from w_s_out_warehouse_order where status ='" + OutWarehouseOrderStatusCode.SUCCESS + "' and (callback_send_status_is_success = 'N' or  callback_send_status_is_success is null)";
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
		String sql = "update w_s_out_warehouse_order set callback_send_weight_is_success='" + outWarehouseOrder.getCallbackSendWeightIsSuccess() + "' ,callback_send_weigh_count = " + outWarehouseOrder.getCallbackSendWeighCount() + " , status='"
				+ outWarehouseOrder.getStatus() + "' where id=" + outWarehouseOrder.getId();
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
		String sql = "update w_s_out_warehouse_order set callback_send_status_is_success='" + outWarehouseOrder.getCallbackSendStatusIsSuccess() + "' ,callback_send_status_count = " + outWarehouseOrder.getCallbackSendStatusCount() + " , status='"
				+ outWarehouseOrder.getStatus() + "' where id=" + outWarehouseOrder.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 
	 */
	@Override
	public int updateOutWarehouseOrderWeight(OutWarehouseOrder outWarehouseOrder) {
		String sql = "update w_s_out_warehouse_order set out_warehouse_weight=" + outWarehouseOrder.getOutWarehouseWeight() + ",weight_code='" + outWarehouseOrder.getWeightCode() + "' , status='" + outWarehouseOrder.getStatus() + "' where id="
				+ outWarehouseOrder.getId();
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateOutWarehouseOrderPrintedCount(Long id, Integer newPrintedCount) {
		String sql = "update w_s_out_warehouse_order set printed_count= " + newPrintedCount + " where id=" + id;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int deleteOutWarehouseOrder(Long outWarehouseOrderId) {
		String sql = "delete from w_s_out_warehouse_order where id = " + outWarehouseOrderId;
		int count = jdbcTemplate.update(sql);
		sql = "delete from w_s_out_warehouse_order_additional_sf where out_warehouse_order_id =" + outWarehouseOrderId;
		jdbcTemplate.update(sql);
		sql = "delete from w_s_out_warehouse_order_item where out_warehouse_order_id = " + outWarehouseOrderId;
		jdbcTemplate.update(sql);
		sql = "delete from w_s_out_warehouse_order_receiver where out_warehouse_order_id =" + outWarehouseOrderId;
		jdbcTemplate.update(sql);
		sql = "delete from w_s_out_warehouse_order_sender where out_warehouse_order_id =" + outWarehouseOrderId;
		jdbcTemplate.update(sql);
		return count;
	}

	@Override
	public int updateOutWarehouseOrderTrackingNo(OutWarehouseOrder outWarehouseOrder) {
		String sql = "update w_s_out_warehouse_order set tracking_no= ?,shipway_extra1=?,shipway_extra2=? where id=?";
		return jdbcTemplate.update(sql, outWarehouseOrder.getTrackingNo(), outWarehouseOrder.getShipwayExtra1(), outWarehouseOrder.getShipwayExtra2(), outWarehouseOrder.getId());
	}

	@Override
	public String getOutWarehouseOrderTrackingNo(Long orderId) {
		String sql = "select tracking_no from w_s_out_warehouse_order where id = " + orderId;
		List<String> trackingNoList = jdbcTemplate.queryForList(sql, String.class);
		if (trackingNoList.size() >= 1) {
			return trackingNoList.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findSingleBarcodeOutWarehouseOrder(Long warehouseId, String status, Long userIdOfCustomer, String createdTimeStart, String createdTimeEnd, String isQuantityOnly1) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT a.out_warehouse_order_id as outWarehouseOrderId ,b.user_id_of_customer as userIdOfCustomer");
		sb.append(" ,b.warehouse_id as warehouseId,b.customer_reference_no as customerReferenceNo");
		sb.append(" ,b.tracking_no as trackingNo,a.sku as barcode,a.sku_no as sku,a.quantity as quantity");
		sb.append(" ,a.sku_name as productName,b.created_time as createdTime,b.`status` as `status`");
		sb.append(" FROM `w_s_out_warehouse_order_item` a LEFT JOIN w_s_out_warehouse_order b  on a.out_warehouse_order_id = b.id");
		sb.append(" where 1=1 ");
		if (warehouseId != null) {
			sb.append(" and b.warehouse_id =  " + warehouseId);
		}
		if (StringUtil.isNotNull(status)) {
			sb.append(" and b.status =  '" + status + "'");
		}
		if (userIdOfCustomer != null) {
			sb.append(" and b.user_id_of_customer =  " + userIdOfCustomer);
		}
		sb.append(" GROUP BY outWarehouseOrderId HAVING count(*)=1 ");
		if (StringUtil.isEqual(Constant.Y, isQuantityOnly1)) {
			sb.append(" and quantity=1  ");
		}
		sb.append(" ORDER BY barcode,quantity asc");
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sb.toString());
		return mapList;
	}
}