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
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("inWarehouseOrderDao")
public class InWarehouseOrderDaoImpl implements IInWarehouseOrderDao {

	Logger logger = Logger.getLogger(InWarehouseOrderDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveInWarehouseOrder(final InWarehouseOrder order) {
		final String sql = "insert into w_s_in_warehouse_order (user_id_of_customer,tracking_no,weight,created_time,remark,status,user_id_of_operator,carrier_code,logistics_type,warehouse_id,customer_reference_no) values (?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, order.getUserIdOfCustomer());
				ps.setString(2, order.getTrackingNo());

				if (order.getWeight() == null) {
					ps.setNull(3, Types.DOUBLE);
				} else {
					ps.setDouble(3, order.getWeight());
				}

				ps.setLong(4, order.getCreatedTime());
				ps.setString(5, order.getRemark());
				ps.setString(6, order.getStatus());

				if (order.getUserIdOfOperator() == null) {
					ps.setNull(7, Types.DOUBLE);
				} else {
					ps.setLong(7, order.getUserIdOfOperator());
				}

				ps.setString(8, order.getCarrierCode());
				ps.setString(9, order.getLogisticsType());
				ps.setLong(10, order.getWarehouseId());
				ps.setString(11, order.getCustomerReferenceNo());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public InWarehouseOrder getInWarehouseOrderById(Long inWarehouseOrderId) {
		String sql = "select id,user_id_of_customer,user_id_of_operator,tracking_no,weight,created_time,remark,status,carrier_code,logistics_type,warehouse_id,customer_reference_no from w_s_in_warehouse_order where id= "
				+ inWarehouseOrderId;
		InWarehouseOrder order = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<InWarehouseOrder>(InWarehouseOrder.class));
		return order;
	}

	/**
	 * 查询入库订单
	 * 
	 * 参数一律使用实体类加Map . 节省QueryVO
	 */
	@Override
	public List<InWarehouseOrder> findInWarehouseOrder(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,user_id_of_customer,user_id_of_operator,tracking_no,weight,created_time,remark,status,carrier_code,logistics_type,warehouse_id,customer_reference_no from w_s_in_warehouse_order where 1=1 ");
		if (inWarehouseOrder != null) {
			if (StringUtil.isNotNull(inWarehouseOrder.getTrackingNo())) {
				sb.append(" and tracking_no = '" + inWarehouseOrder.getTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseOrder.getRemark())) {
				sb.append(" and remark = '" + inWarehouseOrder.getRemark() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseOrder.getStatus())) {
				sb.append(" and status = '" + inWarehouseOrder.getStatus() + "' ");
			}
			if (inWarehouseOrder.getCreatedTime() != null) {
				sb.append(" and created_time = " + inWarehouseOrder.getCreatedTime());
			}
			if (inWarehouseOrder.getId() != null) {
				sb.append(" and id = " + inWarehouseOrder.getId());
			}
			if (inWarehouseOrder.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + inWarehouseOrder.getWarehouseId());
			}
			if (inWarehouseOrder.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + inWarehouseOrder.getUserIdOfCustomer());
			}
			if (inWarehouseOrder.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + inWarehouseOrder.getUserIdOfOperator());
			}
			if (inWarehouseOrder.getWeight() != null) {
				sb.append(" and weight = " + inWarehouseOrder.getWeight());
			}
			if (inWarehouseOrder.getCarrierCode() != null) {
				sb.append(" and carrier_code = " + inWarehouseOrder.getCarrierCode());
			}
			if (inWarehouseOrder.getLogisticsType() != null) {
				sb.append(" and logistics_type = " + inWarehouseOrder.getLogisticsType());
			}
			if (inWarehouseOrder.getCustomerReferenceNo() != null) {
				sb.append(" and customer_reference_no = " + inWarehouseOrder.getCustomerReferenceNo());
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
		logger.info("查询入库订单sql:" + sql);
		List<InWarehouseOrder> inWarehouseOrderList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(InWarehouseOrder.class));
		return inWarehouseOrderList;
	}

	@Override
	public Long countInWarehouseOrder(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_s_in_warehouse_order where 1=1 ");
		if (inWarehouseOrder != null) {
			if (StringUtil.isNotNull(inWarehouseOrder.getTrackingNo())) {
				sb.append(" and tracking_no = '" + inWarehouseOrder.getTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseOrder.getRemark())) {
				sb.append(" and remark = '" + inWarehouseOrder.getRemark() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseOrder.getStatus())) {
				sb.append(" and status = '" + inWarehouseOrder.getStatus() + "' ");
			}
			if (inWarehouseOrder.getCreatedTime() != null) {
				sb.append(" and created_time = " + inWarehouseOrder.getCreatedTime());
			}
			if (inWarehouseOrder.getId() != null) {
				sb.append(" and id = " + inWarehouseOrder.getId());
			}
			if (inWarehouseOrder.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + inWarehouseOrder.getWarehouseId());
			}
			if (inWarehouseOrder.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + inWarehouseOrder.getUserIdOfCustomer());
			}
			if (inWarehouseOrder.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + inWarehouseOrder.getUserIdOfOperator());
			}
			if (inWarehouseOrder.getWeight() != null) {
				sb.append(" and weight = " + inWarehouseOrder.getWeight());
			}
			if (inWarehouseOrder.getCarrierCode() != null) {
				sb.append(" and carrier_code = " + inWarehouseOrder.getCarrierCode());
			}
			if (inWarehouseOrder.getLogisticsType() != null) {
				sb.append(" and logistics_type = " + inWarehouseOrder.getLogisticsType());
			}
			if (inWarehouseOrder.getCustomerReferenceNo() != null) {
				sb.append(" and customer_reference_no = " + inWarehouseOrder.getCustomerReferenceNo());
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
		logger.info("统计入库订单sql:" + sql);
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Long countInWarehouseOrderItemByTrackingNo(String trackingNo) {
		String sql = "select sum(quantity) from w_s_in_warehouse_order_item where order_id = (select id from w_s_in_warehouse_order WHERE tracking_no = '"
				+ trackingNo + "')";
		logger.info("统计入库订单预报物品数量sql:" + sql);
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	@Override
	public Long getUserIdByInWarehouseOrderId(Long inWarehouseOrderId) {
		String sql = "select user_id_of_customer from w_s_in_warehouse_order where id = " + inWarehouseOrderId;
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	@Override
	public Long countInWarehouseOrderItemByInWarehouseOrderId(Long inWarehouseOrderId) {
		String sql = "select sum(quantity) from w_s_in_warehouse_order_item where order_id =  " + inWarehouseOrderId;
		logger.info("统计入库订单预报物品数量sql:" + sql);
		return jdbcTemplate.queryForObject(sql, Long.class);
	}
}