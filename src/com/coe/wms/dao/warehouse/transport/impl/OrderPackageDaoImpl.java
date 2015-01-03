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
import com.coe.wms.dao.warehouse.transport.IOrderPackageDao;
import com.coe.wms.model.warehouse.transport.OrderPackage;
import com.coe.wms.model.warehouse.transport.OrderPackageStatus.OrderPackageStatusCode;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("orderPackageDao")
public class OrderPackageDaoImpl implements IOrderPackageDao {

	Logger logger = Logger.getLogger(OrderPackageDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOrderPackage(final OrderPackage orderPackage) {
		final String sql = "insert into w_t_order_package (warehouse_id,user_id_of_customer,user_id_of_operator,created_time,status,carrier_code,tracking_no,customer_reference_no,remark) values (?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, orderPackage.getWarehouseId());
				ps.setLong(2, orderPackage.getUserIdOfCustomer());
				if (orderPackage.getUserIdOfOperator() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setDouble(3, orderPackage.getUserIdOfOperator());
				}
				ps.setLong(4, orderPackage.getCreatedTime());
				ps.setString(5, orderPackage.getStatus());
				ps.setString(6, orderPackage.getCarrierCode());
				ps.setString(7, orderPackage.getTrackingNo());
				ps.setString(8, orderPackage.getCustomerReferenceNo());
				ps.setString(9, orderPackage.getRemark());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public OrderPackage getOrderPackageById(Long orderPackageId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,created_time,status,carrier_code,tracking_no,customer_reference_no,remark,received_time,callback_send_status_is_success,callback_send_status_count from w_t_order_package where id ="
				+ orderPackageId;
		List<OrderPackage> OrderPackageList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderPackage.class));
		if (OrderPackageList != null && OrderPackageList.size() > 0) {
			return OrderPackageList.get(0);
		}
		return null;
	}

	/**
	 * 
	 * 参数一律使用实体类加Map .
	 */
	@Override
	public List<OrderPackage> findOrderPackage(OrderPackage orderPackage, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,created_time,status,carrier_code,tracking_no,customer_reference_no,remark,received_time,callback_send_status_is_success,callback_send_status_count from w_t_order_package where 1=1 ");
		if (orderPackage != null) {
			if (orderPackage.getId() != null) {
				sb.append(" and id = " + orderPackage.getId());
			}
			if (orderPackage.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + orderPackage.getUserIdOfCustomer());
			}
			if (orderPackage.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + orderPackage.getUserIdOfOperator());
			}
			if (orderPackage.getCreatedTime() != null) {
				sb.append(" and created_time = " + orderPackage.getCreatedTime());
			}
			if (StringUtil.isNotNull(orderPackage.getStatus())) {
				sb.append(" and status = '" + orderPackage.getStatus() + "' ");
			}
			if (StringUtil.isNotNull(orderPackage.getCarrierCode())) {
				sb.append(" and carrier_code = '" + orderPackage.getCarrierCode() + "' ");
			}
			if (StringUtil.isNotNull(orderPackage.getTrackingNo())) {
				sb.append(" and tracking_no = '" + orderPackage.getTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(orderPackage.getCustomerReferenceNo())) {
				sb.append(" and customer_reference_no = '" + orderPackage.getCustomerReferenceNo() + "' ");
			}
			if (StringUtil.isNotNull(orderPackage.getRemark())) {
				sb.append(" and remark = '" + orderPackage.getRemark() + "' ");
			}
			if (StringUtil.isNotNull(orderPackage.getCallbackSendStatusIsSuccess())) {
				sb.append(" and callback_send_status_is_success = '" + orderPackage.getCallbackSendStatusIsSuccess() + "' ");
			}
			if (orderPackage.getCallbackSendStatusCount() != null) {
				sb.append(" and callback_send_status_count = " + orderPackage.getCallbackSendStatusCount());
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
		List<OrderPackage> OrderPackageList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderPackage.class));
		return OrderPackageList;
	}

	@Override
	public Long countOrderPackage(OrderPackage orderPackage, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_t_order_package where 1=1");
		if (orderPackage != null) {
			if (orderPackage.getId() != null) {
				sb.append(" and id = " + orderPackage.getId());
			}
			if (orderPackage.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + orderPackage.getUserIdOfCustomer());
			}
			if (orderPackage.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + orderPackage.getUserIdOfOperator());
			}
			if (orderPackage.getCreatedTime() != null) {
				sb.append(" and created_time = " + orderPackage.getCreatedTime());
			}
			if (StringUtil.isNotNull(orderPackage.getStatus())) {
				sb.append(" and status = '" + orderPackage.getStatus() + "' ");
			}
			if (StringUtil.isNotNull(orderPackage.getCarrierCode())) {
				sb.append(" and carrier_code = '" + orderPackage.getCarrierCode() + "' ");
			}
			if (StringUtil.isNotNull(orderPackage.getTrackingNo())) {
				sb.append(" and tracking_no = '" + orderPackage.getTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(orderPackage.getCustomerReferenceNo())) {
				sb.append(" and customer_reference_no = '" + orderPackage.getCustomerReferenceNo() + "' ");
			}
			if (StringUtil.isNotNull(orderPackage.getRemark())) {
				sb.append(" and remark = '" + orderPackage.getRemark() + "' ");
			}
			if (StringUtil.isNotNull(orderPackage.getCallbackSendStatusIsSuccess())) {
				sb.append(" and callback_send_status_is_success = '" + orderPackage.getCallbackSendStatusIsSuccess() + "' ");
			}
			if (orderPackage.getCallbackSendStatusCount() != null) {
				sb.append(" and callback_send_status_count = " + orderPackage.getCallbackSendStatusCount());
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

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int updateOrderPackageStatus(Long orderPackageId, String newStatus) {
		String sql = "update w_t_order_package set status = '" + newStatus + "' where id = " + orderPackageId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public String getOrderPackageStatus(Long orderPackageId) {
		String sql = "select status from w_t_order_package where id = " + orderPackageId;
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	/**
	 * 获取回调未成功的记录id
	 * 
	 * 1,必须有客户id, 2,必须有重量, 3回调未成功 或未回调
	 */
	@Override
	public List<Long> findCallbackSendStatusUnSuccessOrderPackageId() {
		String sql = "select id from w_t_order_package where status ='" + OrderPackageStatusCode.SUCCESS + "' and (callback_send_status_is_success = 'N' or  callback_send_status_is_success is null)";
		List<Long> OrderPackageIdList = jdbcTemplate.queryForList(sql, Long.class);
		return OrderPackageIdList;
	}

	/**
	 * 更新回调顺丰状态
	 * 
	 * @param OrderPackage
	 * @return
	 */
	@Override
	public int updateOrderPackageCallbackSendStatus(OrderPackage orderPackage) {
		String sql = "update w_t_order_package set callback_send_status_is_success='" + orderPackage.getCallbackSendStatusIsSuccess() + "' ,callback_send_status_count = " + orderPackage.getCallbackSendStatusCount() + " , status='"
				+ orderPackage.getStatus() + "' where id=" + orderPackage.getId();
		return jdbcTemplate.update(sql);
	}

	@Override
	public String getCustomerReferenceNoById(Long orderPackageId) {
		String sql = "select customer_reference_no from w_t_order_package where id = " + orderPackageId;
		return jdbcTemplate.queryForObject(sql, String.class);
	}
}