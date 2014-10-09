package com.coe.wms.dao.warehouse.storage.impl;

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
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
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
		final String sql = "insert into w_s_out_warehouse_order (warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no) values (?,?,?,?,?,?,?,?)";
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
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public OutWarehouseOrder getOutWarehouseOrderById(Long outWarehouseOrderId) {

		return null;
	}

	/**
	 * 查询入库订单
	 * 
	 * 参数一律使用实体类加Map . 节省QueryVO
	 */
	@Override
	public List<OutWarehouseOrder> findOutWarehouseOrder(OutWarehouseOrder outWarehouseOrder, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no from w_s_out_warehouse_order where 1=1 ");
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
		return jdbcTemplate.queryForLong(sql);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}