package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
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
		final String sql = "insert into w_s_in_warehouse_order (user_id,package_no,package_tracking_no,weight,small_package_quantity,created_time,remark,status) values (?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, order.getUserId());
				ps.setString(2, order.getPackageNo());
				ps.setString(3, order.getPackageTrackingNo());
				ps.setDouble(4, order.getWeight() != null ? order.getWeight() : 0);
				ps.setInt(5, order.getSmallPackageQuantity());
				ps.setLong(6, order.getCreatedTime());
				ps.setString(7, order.getRemark());
				ps.setString(8, order.getStatus());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public InWarehouseOrder getInWarehouseOrderById(Long inWarehouseOrderId) {

		return null;
	}

	/**
	 * 查询入库订单
	 * 
	 * 参数一律使用实体类加Map . 节省QueryVO
	 */
	@Override
	public List<InWarehouseOrder> findInWarehouseOrder(InWarehouseOrder inWarehouseOrder,
			Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,user_id,package_no,package_tracking_no,weight,small_package_quantity,created_time,remark,status,received_quantity from w_s_in_warehouse_order where 1=1 ");
		if (inWarehouseOrder != null) {
			if (StringUtil.isNotNull(inWarehouseOrder.getPackageNo())) {
				sb.append(" and package_no = '" + inWarehouseOrder.getPackageNo() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseOrder.getPackageTrackingNo())) {
				sb.append(" and package_tracking_no = '" + inWarehouseOrder.getPackageTrackingNo() + "' ");
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
			if (inWarehouseOrder.getReceivedQuantity() != null) {
				sb.append(" and received_quantity = " + inWarehouseOrder.getReceivedQuantity());
			}
			if (inWarehouseOrder.getUserId() != null) {
				sb.append(" and user_id = " + inWarehouseOrder.getUserId());
			}
			if (inWarehouseOrder.getWeight() != null) {
				sb.append(" and weight = " + inWarehouseOrder.getWeight());
			}
		}
		if (moreParam != null) {
			if (moreParam.get("createdTimeStart") != null) {
				sb.append(" and created_time >= " + Long.valueOf(moreParam.get("createdTimeStart")));
			}
			if (moreParam.get("createdTimeEnd") != null) {
				sb.append(" and created_time <= " + Long.valueOf(moreParam.get("createdTimeEnd")));
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

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}