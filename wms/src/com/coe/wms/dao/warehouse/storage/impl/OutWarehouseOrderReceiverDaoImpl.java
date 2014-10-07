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
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("outWarehouseOrderReceiverDao")
public class OutWarehouseOrderReceiverDaoImpl implements IOutWarehouseOrderReceiverDao {

	Logger logger = Logger.getLogger(OutWarehouseOrderReceiverDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个出库订单收件人
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOutWarehouseOrderReceiver(final OutWarehouseOrderReceiver receiver) {
		final String sql = "insert into w_s_out_warehouse_order_receiver (out_warehouse_order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, receiver.getOutWarehouseOrderId());
				ps.setString(2, receiver.getName());
				ps.setString(3, receiver.getCompany());
				ps.setString(4, receiver.getFirstName());
				ps.setString(5, receiver.getLastName());
				ps.setString(6, receiver.getAddressLine1());
				ps.setString(7, receiver.getStateOrProvince());
				ps.setString(8, receiver.getCity());
				ps.setString(9, receiver.getCounty());
				ps.setString(10, receiver.getPostalCode());
				ps.setString(11, receiver.getCountryCode());
				ps.setString(12, receiver.getCountryName());
				ps.setString(13, receiver.getPhoneNumber());
				ps.setString(14, receiver.getEmail());
				ps.setString(15, receiver.getMobileNumber());
				ps.setString(16, receiver.getAddressLine2());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	/**
	 * 批量保存出库订单收件人
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public int saveBatchOutWarehouseOrderReceiver(final List<OutWarehouseOrderReceiver> receiverList) {
		final String sql = "insert into w_s_out_warehouse_order_receiver (out_warehouse_order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OutWarehouseOrderReceiver receiver = receiverList.get(i);
				ps.setLong(1, receiver.getOutWarehouseOrderId());
				ps.setString(2, receiver.getName());
				ps.setString(3, receiver.getCompany());
				ps.setString(4, receiver.getFirstName());
				ps.setString(5, receiver.getLastName());
				ps.setString(6, receiver.getAddressLine1());
				ps.setString(7, receiver.getStateOrProvince());
				ps.setString(8, receiver.getCity());
				ps.setString(9, receiver.getCounty());
				ps.setString(10, receiver.getPostalCode());
				ps.setString(11, receiver.getCountryCode());
				ps.setString(12, receiver.getCountryName());
				ps.setString(13, receiver.getPhoneNumber());
				ps.setString(14, receiver.getEmail());
				ps.setString(15, receiver.getMobileNumber());
				ps.setString(16, receiver.getAddressLine2());
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
	public int saveBatchOutWarehouseOrderReceiverWithOrderId(final List<OutWarehouseOrderReceiver> receiverList, final Long orderId) {
		final String sql = "insert into w_s_out_warehouse_order_receiver (out_warehouse_order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OutWarehouseOrderReceiver receiver = receiverList.get(i);
				ps.setLong(1, receiver.getOutWarehouseOrderId());
				ps.setString(2, receiver.getName());
				ps.setString(3, receiver.getCompany());
				ps.setString(4, receiver.getFirstName());
				ps.setString(5, receiver.getLastName());
				ps.setString(6, receiver.getAddressLine1());
				ps.setString(7, receiver.getStateOrProvince());
				ps.setString(8, receiver.getCity());
				ps.setString(9, receiver.getCounty());
				ps.setString(10, receiver.getPostalCode());
				ps.setString(11, receiver.getCountryCode());
				ps.setString(12, receiver.getCountryName());
				ps.setString(13, receiver.getPhoneNumber());
				ps.setString(14, receiver.getEmail());
				ps.setString(15, receiver.getMobileNumber());
				ps.setString(16, receiver.getAddressLine2());
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
	public List<OutWarehouseOrderReceiver> findOutWarehouseOrderReceiver(OutWarehouseOrderReceiver outWarehouseOrderReceiver,
			Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,out_warehouse_order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2 from w_s_out_warehouse_order_receiver where 1=1 ");
		if (outWarehouseOrderReceiver != null) {
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getName())) {
				sb.append(" and name = '" + outWarehouseOrderReceiver.getName() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getCompany())) {
				sb.append(" and company = '" + outWarehouseOrderReceiver.getCompany() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getStateOrProvince())) {
				sb.append(" and state_or_province = '" + outWarehouseOrderReceiver.getStateOrProvince() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getCity())) {
				sb.append(" and city = '" + outWarehouseOrderReceiver.getCity() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getCounty())) {
				sb.append(" and county = '" + outWarehouseOrderReceiver.getCounty() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getPostalCode())) {
				sb.append(" and postal_code = '" + outWarehouseOrderReceiver.getPostalCode() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getCountryName())) {
				sb.append(" and country_name = '" + outWarehouseOrderReceiver.getCountryName() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getCountryCode())) {
				sb.append(" and country_code = '" + outWarehouseOrderReceiver.getCountryCode() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getFirstName())) {
				sb.append(" and first_name = '" + outWarehouseOrderReceiver.getFirstName() + "' ");
			}
			if (outWarehouseOrderReceiver.getLastName() != null) {
				sb.append(" and last_name = '" + outWarehouseOrderReceiver.getLastName() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getAddressLine1())) {
				sb.append(" and address_line1 = '" + outWarehouseOrderReceiver.getAddressLine1() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getAddressLine2())) {
				sb.append(" and address_line2 = '" + outWarehouseOrderReceiver.getAddressLine2() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getPhoneNumber())) {
				sb.append(" and phone_number = '" + outWarehouseOrderReceiver.getPhoneNumber() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getMobileNumber())) {
				sb.append(" and mobile_number = '" + outWarehouseOrderReceiver.getMobileNumber() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderReceiver.getEmail())) {
				sb.append(" and email = '" + outWarehouseOrderReceiver.getEmail() + "' ");
			}
			if (outWarehouseOrderReceiver.getId() != null) {
				sb.append(" and id = '" + outWarehouseOrderReceiver.getId() + "' ");
			}
			if (outWarehouseOrderReceiver.getOutWarehouseOrderId() != null) {
				sb.append(" and out_warehouse_order_id = '" + outWarehouseOrderReceiver.getOutWarehouseOrderId() + "' ");
			}
		}
		// 分页sql
		sb.append(page.generatePageSql());
		String sql = sb.toString();
		logger.info("查询出库订单收件人sql:" + sql);
		List<OutWarehouseOrderReceiver> outWarehouseOrderReceiverList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(OutWarehouseOrderReceiver.class));
		return outWarehouseOrderReceiverList;
	}

	@Override
	public OutWarehouseOrderReceiver getOutWarehouseOrderReceiverByOrderId(Long outWarehouseOrderId) {
		String sql = "select id,out_warehouse_order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2 from w_s_out_warehouse_order_receiver where out_warehouse_order_id = "+ outWarehouseOrderId;
		logger.info("查询出库订单收件人sql:" + sql);
		List<OutWarehouseOrderReceiver> outWarehouseOrderReceiverList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(OutWarehouseOrderReceiver.class));
		if (outWarehouseOrderReceiverList != null && outWarehouseOrderReceiverList.size() > 0) {
			return outWarehouseOrderReceiverList.get(0);
		}
		return null;
	}
}