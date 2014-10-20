package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderAdditionalSfDao;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderAdditionalSf;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("outWarehouseOrderAdditionalSfDao")
public class OutWarehouseOrderAdditionalSfDaoImpl implements IOutWarehouseOrderAdditionalSfDao {

	Logger logger = Logger.getLogger(OutWarehouseOrderAdditionalSfDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOutWarehouseOrderAdditionalSf(final OutWarehouseOrderAdditionalSf receiver) {
		final String sql = "insert into w_s_out_warehouse_order_additional_sf (out_warehouse_order_id,carrier_code,mail_no,sender_address,cust_id,pay_method,shipper_code,delivery_code,order_id) values (?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, receiver.getOutWarehouseOrderId());
				ps.setString(2, receiver.getCarrierCode());
				ps.setString(3, receiver.getMailNo());
				ps.setString(4, receiver.getSenderAddress());
				ps.setString(5, receiver.getCustId());
				ps.setString(6, receiver.getPayMethod());
				ps.setString(7, receiver.getShipperCode());
				ps.setString(8, receiver.getDeliveryCode());
				ps.setString(9, receiver.getOrderId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	/**
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public int saveBatchOutWarehouseOrderAdditionalSf(final List<OutWarehouseOrderAdditionalSf> receiverList) {
		final String sql = "insert into w_s_out_warehouse_order_additional_sf (out_warehouse_order_id,carrier_code,mail_no,sender_address,cust_id,pay_method,shipper_code,delivery_code,order_id) values (?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OutWarehouseOrderAdditionalSf receiver = receiverList.get(i);
				ps.setLong(1, receiver.getOutWarehouseOrderId());
				ps.setString(2, receiver.getCarrierCode());
				ps.setString(3, receiver.getMailNo());
				ps.setString(4, receiver.getSenderAddress());
				ps.setString(5, receiver.getCustId());
				ps.setString(6, receiver.getPayMethod());
				ps.setString(7, receiver.getShipperCode());
				ps.setString(8, receiver.getDeliveryCode());
				ps.setString(9, receiver.getOrderId());
			}

			@Override
			public int getBatchSize() {
				return receiverList.size();
			}
		});
		return NumberUtil.sumArry(batchUpdateSize);
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public int saveBatchOutWarehouseOrderAdditionalSfWithOrderId(final List<OutWarehouseOrderAdditionalSf> receiverList, final Long orderId) {
		final String sql = "insert into w_s_out_warehouse_order_additional_sf (out_warehouse_order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OutWarehouseOrderAdditionalSf receiver = receiverList.get(i);
				ps.setLong(1, orderId);
				ps.setString(2, receiver.getCarrierCode());
				ps.setString(3, receiver.getMailNo());
				ps.setString(4, receiver.getSenderAddress());
				ps.setString(5, receiver.getCustId());
				ps.setString(6, receiver.getPayMethod());
				ps.setString(7, receiver.getShipperCode());
				ps.setString(8, receiver.getDeliveryCode());
				ps.setString(9, receiver.getOrderId());
			}

			@Override
			public int getBatchSize() {
				return receiverList.size();
			}
		});
		return NumberUtil.sumArry(batchUpdateSize);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 查询出库订单收件人
	 * 
	 * 参数一律使用实体类加Map . 节省QueryVO
	 */
	@Override
	public List<OutWarehouseOrderAdditionalSf> findOutWarehouseOrderAdditionalSf(
			OutWarehouseOrderAdditionalSf OutWarehouseOrderAdditionalSf, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,out_warehouse_order_id,carrier_code,mail_no,sender_address,cust_id,pay_method,shipper_code,delivery_code,order_id from w_s_out_warehouse_order_additional_sf where 1=1 ");
		if (OutWarehouseOrderAdditionalSf != null) {
			if (StringUtil.isNotNull(OutWarehouseOrderAdditionalSf.getCarrierCode())) {
				sb.append(" and carrier_code = '" + OutWarehouseOrderAdditionalSf.getCarrierCode() + "' ");
			}
			if (StringUtil.isNotNull(OutWarehouseOrderAdditionalSf.getMailNo())) {
				sb.append(" and mail_no = '" + OutWarehouseOrderAdditionalSf.getMailNo() + "' ");
			}
			if (OutWarehouseOrderAdditionalSf.getId() != null) {
				sb.append(" and id = '" + OutWarehouseOrderAdditionalSf.getId() + "' ");
			}
			if (OutWarehouseOrderAdditionalSf.getOutWarehouseOrderId() != null) {
				sb.append(" and out_warehouse_order_id = '" + OutWarehouseOrderAdditionalSf.getOutWarehouseOrderId() + "' ");
			}
		}
		// 分页sql
		sb.append(page.generatePageSql());
		String sql = sb.toString();
		logger.info("查询出库订单收件人sql:" + sql);
		List<OutWarehouseOrderAdditionalSf> OutWarehouseOrderAdditionalSfList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(OutWarehouseOrderAdditionalSf.class));
		return OutWarehouseOrderAdditionalSfList;
	}

	@Override
	public OutWarehouseOrderAdditionalSf getOutWarehouseOrderAdditionalSfByOrderId(Long outWarehouseOrderId) {
		String sql = "select id,out_warehouse_order_id,carrier_code,mail_no,sender_address,cust_id,pay_method,shipper_code,delivery_code,order_id from w_s_out_warehouse_order_additional_sf where out_warehouse_order_id = "
				+ outWarehouseOrderId;
		logger.info("查询出库订单顺丰标签附加内容sql:" + sql);
		List<OutWarehouseOrderAdditionalSf> OutWarehouseOrderAdditionalSfList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(OutWarehouseOrderAdditionalSf.class));
		if (OutWarehouseOrderAdditionalSfList != null && OutWarehouseOrderAdditionalSfList.size() > 0) {
			return OutWarehouseOrderAdditionalSfList.get(0);
		}
		return null;
	}
}