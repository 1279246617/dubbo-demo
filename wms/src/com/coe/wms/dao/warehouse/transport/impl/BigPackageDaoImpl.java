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
import com.coe.wms.dao.warehouse.transport.IBigPackageDao;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderReceiver;
import com.coe.wms.model.warehouse.transport.OrderStatus.OrderStatusCode;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("bigPackageDao")
public class BigPackageDaoImpl implements IBigPackageDao {

	Logger logger = Logger.getLogger(BigPackageDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveBigPackage(final Order bigPackage) {
		final String sql = "insert into w_t_order (warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,out_warehouse_weight,weight_code,trade_remark,tracking_no,transport_type) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, bigPackage.getWarehouseId());
				ps.setLong(2, bigPackage.getUserIdOfCustomer());

				if (bigPackage.getUserIdOfOperator() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setDouble(3, bigPackage.getUserIdOfOperator());
				}
				ps.setString(4, bigPackage.getShipwayCode());
				ps.setLong(5, bigPackage.getCreatedTime());
				ps.setString(6, bigPackage.getStatus());
				ps.setString(7, bigPackage.getRemark());
				ps.setString(8, bigPackage.getCustomerReferenceNo());
				if (bigPackage.getOutWarehouseWeight() == null) {
					ps.setNull(9, Types.DOUBLE);
				} else {
					ps.setDouble(9, bigPackage.getOutWarehouseWeight());
				}
				ps.setString(10, bigPackage.getWeightCode());
				ps.setString(11, bigPackage.getTradeRemark());
				ps.setString(12, bigPackage.getTrackingNo());
				ps.setString(13, bigPackage.getTransportType());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public Order getBigPackageById(Long bigPackageId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,callback_send_weight_is_success,callback_send_weigh_count,callback_send_status_is_success,callback_send_status_count,out_warehouse_weight,weight_code,trade_remark,tracking_no,callback_send_check_is_success,callback_send_check_count,check_result,transport_type,shipway_extra1,shipway_extra2 from w_t_order where id ="
				+ bigPackageId;
		List<Order> bigPackageList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(Order.class));
		if (bigPackageList != null && bigPackageList.size() > 0) {
			return bigPackageList.get(0);
		}
		return null;
	}

	/**
	 * 
	 * 参数一律使用实体类加Map .
	 */
	@Override
	public List<Order> findBigPackage(Order bigPackage, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,callback_send_weight_is_success,callback_send_weigh_count,callback_send_status_is_success,callback_send_status_count,out_warehouse_weight,weight_code,trade_remark,tracking_no,callback_send_check_is_success,callback_send_check_count,check_result,transport_type,shipway_extra1,shipway_extra2 from w_t_order where 1=1 ");
		// 按单号 批量查询 开始---------------
		if (moreParam != null && StringUtil.isNotNull(moreParam.get("nos"))) {
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
		} else if (moreParam != null && StringUtil.isEqual(moreParam.get("trackingNoIsNull"), Constant.Y)) {// 显示无跟踪单号的订单
			sb.append(" and (tracking_no is null or tracking_no = '') ");
		} else {
			if (bigPackage != null) {
				if (bigPackage.getId() != null) {
					sb.append(" and id = " + bigPackage.getId());
				}
				if (bigPackage.getUserIdOfCustomer() != null) {
					sb.append(" and user_id_of_customer = " + bigPackage.getUserIdOfCustomer());
				}
				if (bigPackage.getUserIdOfOperator() != null) {
					sb.append(" and user_id_of_operator = " + bigPackage.getUserIdOfOperator());
				}
				if (StringUtil.isNotNull(bigPackage.getShipwayCode())) {
					sb.append(" and shipway_code = '" + bigPackage.getShipwayCode() + "' ");
				}
				if (bigPackage.getCreatedTime() != null) {
					sb.append(" and created_time = " + bigPackage.getCreatedTime());
				}
				if (StringUtil.isNotNull(bigPackage.getStatus())) {
					sb.append(" and status = '" + bigPackage.getStatus() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getRemark())) {
					sb.append(" and remark = '" + bigPackage.getRemark() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendCheckIsSuccess())) {
					sb.append(" and callback_send_check_is_success = '" + bigPackage.getCallbackSendCheckIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendCheckCount() != null) {
					sb.append(" and callback_send_check_count = " + bigPackage.getCallbackSendCheckCount());
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendWeightIsSuccess())) {
					sb.append(" and callback_send_weight_is_success = '" + bigPackage.getCallbackSendWeightIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendWeighCount() != null) {
					sb.append(" and callback_send_weigh_count = " + bigPackage.getCallbackSendWeighCount());
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendStatusIsSuccess())) {
					sb.append(" and callback_send_status_is_success = '" + bigPackage.getCallbackSendStatusIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendStatusCount() != null) {
					sb.append(" and callback_send_status_count = " + bigPackage.getCallbackSendStatusCount());
				}
				if (bigPackage.getOutWarehouseWeight() != null) {
					sb.append(" and out_warehouse_weight = " + bigPackage.getOutWarehouseWeight());
				}
				if (StringUtil.isNotNull(bigPackage.getWeightCode())) {
					sb.append(" and weight_code = '" + bigPackage.getWeightCode() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getTradeRemark())) {
					sb.append(" and trade_remark = '" + bigPackage.getTradeRemark() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getTrackingNo())) {
					sb.append(" and tracking_no = '" + bigPackage.getTrackingNo() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getCheckResult())) {
					sb.append(" and check_result = '" + bigPackage.getCheckResult() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getCustomerReferenceNo())) {
					sb.append(" and customer_reference_no = '" + bigPackage.getCustomerReferenceNo() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getTransportType())) {
					sb.append(" and transport_type = '" + bigPackage.getTransportType() + "' ");
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
		List<Order> bigPackageList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(Order.class));
		return bigPackageList;
	}

	@Override
	public Long countBigPackage(Order bigPackage, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_t_order where 1=1");
		// 按单号 批量查询 开始---------------
		if (moreParam != null && StringUtil.isNotNull(moreParam.get("nos"))) {
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
		} else if (moreParam != null && StringUtil.isNotNull(moreParam.get("trackingNoIsNull"))) {// 显示无跟踪单号的订单
			sb.append(" and (tracking_no is null or tracking_no = '') ");
		} else {
			if (bigPackage != null) {
				if (bigPackage.getId() != null) {
					sb.append(" and id = " + bigPackage.getId());
				}
				if (bigPackage.getUserIdOfCustomer() != null) {
					sb.append(" and user_id_of_customer = " + bigPackage.getUserIdOfCustomer());
				}
				if (bigPackage.getUserIdOfOperator() != null) {
					sb.append(" and user_id_of_operator = " + bigPackage.getUserIdOfOperator());
				}
				if (StringUtil.isNotNull(bigPackage.getShipwayCode())) {
					sb.append(" and shipway_code = '" + bigPackage.getShipwayCode() + "' ");
				}
				if (bigPackage.getCreatedTime() != null) {
					sb.append(" and created_time = " + bigPackage.getCreatedTime());
				}
				if (StringUtil.isNotNull(bigPackage.getStatus())) {
					sb.append(" and status = '" + bigPackage.getStatus() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getRemark())) {
					sb.append(" and remark = '" + bigPackage.getRemark() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendCheckIsSuccess())) {
					sb.append(" and callback_send_check_is_success = '" + bigPackage.getCallbackSendCheckIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendCheckCount() != null) {
					sb.append(" and callback_send_check_count = " + bigPackage.getCallbackSendCheckCount());
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendWeightIsSuccess())) {
					sb.append(" and callback_send_weight_is_success = '" + bigPackage.getCallbackSendWeightIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendWeighCount() != null) {
					sb.append(" and callback_send_weigh_count = " + bigPackage.getCallbackSendWeighCount());
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendStatusIsSuccess())) {
					sb.append(" and callback_send_status_is_success = '" + bigPackage.getCallbackSendStatusIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendStatusCount() != null) {
					sb.append(" and callback_send_status_count = " + bigPackage.getCallbackSendStatusCount());
				}
				if (bigPackage.getOutWarehouseWeight() != null) {
					sb.append(" and out_warehouse_weight = " + bigPackage.getOutWarehouseWeight());
				}
				if (StringUtil.isNotNull(bigPackage.getWeightCode())) {
					sb.append(" and weight_code = '" + bigPackage.getWeightCode() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getTradeRemark())) {
					sb.append(" and trade_remark = '" + bigPackage.getTradeRemark() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getTrackingNo())) {
					sb.append(" and tracking_no = '" + bigPackage.getTrackingNo() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getCheckResult())) {
					sb.append(" and check_result = '" + bigPackage.getCheckResult() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getCustomerReferenceNo())) {
					sb.append(" and customer_reference_no = '" + bigPackage.getCustomerReferenceNo() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getTransportType())) {
					sb.append(" and transport_type = '" + bigPackage.getTransportType() + "' ");
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
	public int updateBigPackageStatus(Long bigPackageId, String newStatus) {
		String sql = "update w_t_order set status = '" + newStatus + "' where id = " + bigPackageId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public String getBigPackageStatus(Long bigPackageId) {
		String sql = "select status from w_t_order where id = " + bigPackageId;
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	/**
	 * 获取回调未成功的记录id
	 * 
	 * 1,必须有客户id, 2,必须有重量, 3回调未成功 或未回调
	 */
	@Override
	public List<Long> findCallbackSendWeightUnSuccessBigPackageId() {
		String sql = "select id from w_t_order where status ='" + OrderStatusCode.WSW + "' and (callback_send_weight_is_success = 'N' or  callback_send_weight_is_success is null)";
		List<Long> bigPackageIdList = jdbcTemplate.queryForList(sql, Long.class);
		return bigPackageIdList;
	}

	/**
	 * 获取回调未成功的记录id
	 * 
	 * 1,必须有客户id, 2,必须有重量, 3回调未成功 或未回调
	 */
	@Override
	public List<Long> findCallbackSendStatusUnSuccessBigPackageId() {
		String sql = "select id from w_t_order where status ='" + OrderStatusCode.SUCCESS + "' and (callback_send_status_is_success = 'N' or  callback_send_status_is_success is null)";
		List<Long> bigPackageIdList = jdbcTemplate.queryForList(sql, Long.class);
		return bigPackageIdList;
	}

	/**
	 * 更新回调顺丰状态
	 * 
	 * @param bigPackage
	 * @return
	 */
	@Override
	public int updateBigPackageCallbackSendWeight(Order bigPackage) {
		String sql = "update w_t_order set callback_send_weight_is_success='" + bigPackage.getCallbackSendWeightIsSuccess() + "' ,callback_send_weigh_count = " + bigPackage.getCallbackSendWeighCount() + " , status='" + bigPackage.getStatus()
				+ "' where id=" + bigPackage.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 更新回调顺丰状态
	 * 
	 * @param bigPackage
	 * @return
	 */
	@Override
	public int updateBigPackageCallbackSendStatus(Order bigPackage) {
		String sql = "update w_t_order set callback_send_status_is_success='" + bigPackage.getCallbackSendStatusIsSuccess() + "' ,callback_send_status_count = " + bigPackage.getCallbackSendStatusCount() + " , status='" + bigPackage.getStatus()
				+ "' where id=" + bigPackage.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 
	 */
	@Override
	public int updateBigPackageWeight(Order bigPackage) {
		String sql = "update w_t_order set out_warehouse_weight=" + bigPackage.getOutWarehouseWeight() + ",weight_code='" + bigPackage.getWeightCode() + "' , status='" + bigPackage.getStatus() + "' where id=" + bigPackage.getId();
		return jdbcTemplate.update(sql);
	}

	@Override
	public List<Long> findCallbackSendCheckUnSuccessBigPackageId() {
		String sql = "select id from w_t_order where status ='" + OrderStatusCode.WCI + "' and (callback_send_check_is_success = 'N' or  callback_send_check_is_success is null)";
		List<Long> bigPackageIdList = jdbcTemplate.queryForList(sql, Long.class);
		return bigPackageIdList;
	}

	@Override
	public int updateBigPackageCallbackSendCheck(Order bigPackage) {
		String sql = "update w_t_order set callback_send_check_is_success='" + bigPackage.getCallbackSendCheckIsSuccess() + "' ,callback_send_check_count = " + bigPackage.getCallbackSendCheckCount() + " , status='" + bigPackage.getStatus()
				+ "' where id=" + bigPackage.getId();
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateBigPackageCheckResult(Long bigPackageId, String checkResult) {
		String sql = "update w_t_order set check_result = '" + checkResult + "' where id = " + bigPackageId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public String getCustomerReferenceNoById(Long bigPackageId) {
		String sql = "select customer_reference_no from w_t_order where id = " + bigPackageId;
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	@Override
	public List<Long> findUnCheckAndTackingNoIsNullBigPackageId() {
		String sql = "select id from w_t_order where status ='" + OrderStatusCode.WWC + "' and tracking_no  is null";
		List<Long> bigPackageIdList = jdbcTemplate.queryForList(sql, Long.class);
		return bigPackageIdList;
	}

	@Override
	public int updateBigPackageTrackingNo(Order bigPackage) {
		String sql = "update w_t_order set tracking_no= ?,shipway_extra1=?,shipway_extra2=? where id=?";
		return jdbcTemplate.update(sql, bigPackage.getTrackingNo(), bigPackage.getShipwayExtra1(), bigPackage.getShipwayExtra2(), bigPackage.getId());
	}
}