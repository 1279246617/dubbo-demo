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
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderSender;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("outWarehouseOrderSenderDao")
public class OutWarehouseOrderSenderDaoImpl implements IOutWarehouseOrderSenderDao {

	Logger logger = Logger.getLogger(OutWarehouseOrderSenderDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个发件人
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOutWarehouseOrderSender(final OutWarehouseOrderSender sender) {
		final String sql = "insert into w_s_out_warehouse_order_sender (out_warehouse_order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, sender.getOutWarehouseOrderId());
				ps.setString(2, sender.getName());
				ps.setString(3, sender.getCompany());
				ps.setString(4, sender.getFirstName());
				ps.setString(5, sender.getLastName());
				ps.setString(6, sender.getAddressLine1());
				ps.setString(7, sender.getStateOrProvince());
				ps.setString(8, sender.getCity());
				ps.setString(9, sender.getCounty());
				ps.setString(10, sender.getPostalCode());
				ps.setString(11, sender.getCountryCode());
				ps.setString(12, sender.getCountryName());
				ps.setString(13, sender.getPhoneNumber());
				ps.setString(14, sender.getEmail());
				ps.setString(15, sender.getMobileNumber());
				ps.setString(16, sender.getAddressLine2());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	/**
	 * 批量保存发件人
	 * */
	@Override
	@DataSource(DataSourceCode.WMS)
	public int saveBatchOutWarehouseOrderSender(final List<OutWarehouseOrderSender> senderList) {
		final String sql = "insert into w_s_out_warehouse_order_sender (out_warehouse_order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OutWarehouseOrderSender sender = senderList.get(i);
				ps.setLong(1, sender.getOutWarehouseOrderId());
				ps.setString(2, sender.getName());
				ps.setString(3, sender.getCompany());
				ps.setString(4, sender.getFirstName());
				ps.setString(5, sender.getLastName());
				ps.setString(6, sender.getAddressLine1());
				ps.setString(7, sender.getStateOrProvince());
				ps.setString(8, sender.getCity());
				ps.setString(9, sender.getCounty());
				ps.setString(10, sender.getPostalCode());
				ps.setString(11, sender.getCountryCode());
				ps.setString(12, sender.getCountryName());
				ps.setString(13, sender.getPhoneNumber());
				ps.setString(14, sender.getEmail());
				ps.setString(15, sender.getMobileNumber());
				ps.setString(16, sender.getAddressLine2());
			}

			@Override
			public int getBatchSize() {
				return senderList.size();
			}
		});
		return NumberUtil.sumArry(batchUpdateSize);
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public int saveBatchOutWarehouseOrderSenderWithOrderId(final List<OutWarehouseOrderSender> senderList, final Long orderId) {
		final String sql = "insert into w_s_out_warehouse_order_sender (out_warehouse_order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OutWarehouseOrderSender sender = senderList.get(i);
				ps.setLong(1, sender.getOutWarehouseOrderId());
				ps.setString(2, sender.getName());
				ps.setString(3, sender.getCompany());
				ps.setString(4, sender.getFirstName());
				ps.setString(5, sender.getLastName());
				ps.setString(6, sender.getAddressLine1());
				ps.setString(7, sender.getStateOrProvince());
				ps.setString(8, sender.getCity());
				ps.setString(9, sender.getCounty());
				ps.setString(10, sender.getPostalCode());
				ps.setString(11, sender.getCountryCode());
				ps.setString(12, sender.getCountryName());
				ps.setString(13, sender.getPhoneNumber());
				ps.setString(14, sender.getEmail());
				ps.setString(15, sender.getMobileNumber());
				ps.setString(16, sender.getAddressLine2());
			}

			@Override
			public int getBatchSize() {
				return senderList.size();
			}
		});
		return NumberUtil.sumArry(batchUpdateSize);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 查询出库订单发件人
	 * 
	 * 参数一律使用实体类加Map . 节省QueryVO
	 */
	@Override
	public List<OutWarehouseOrderSender> findOutWarehouseOrderSender(OutWarehouseOrderSender outWarehouseOrderSender,
			Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,out_warehouse_order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2 where 1=1 ");
		if (outWarehouseOrderSender != null) {
			if (StringUtil.isNotNull(outWarehouseOrderSender.getName())) {
				sb.append(" and name = '" + outWarehouseOrderSender.getName() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getCompany())) {
				sb.append(" and company = '" + outWarehouseOrderSender.getCompany() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getStateOrProvince())) {
				sb.append(" and state_or_province = '" + outWarehouseOrderSender.getStateOrProvince() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getCity())) {
				sb.append(" and city = '" + outWarehouseOrderSender.getCity() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getCounty())) {
				sb.append(" and county = '" + outWarehouseOrderSender.getCounty() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getPostalCode())) {
				sb.append(" and postal_code = '" + outWarehouseOrderSender.getPostalCode() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getCountryName())) {
				sb.append(" and country_name = '" + outWarehouseOrderSender.getCountryName() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getCountryCode())) {
				sb.append(" and country_code = '" + outWarehouseOrderSender.getCountryCode() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getFirstName())) {
				sb.append(" and first_name = '" + outWarehouseOrderSender.getFirstName() + "' ");
			}
			if (outWarehouseOrderSender.getLastName() != null) {
				sb.append(" and last_name = '" + outWarehouseOrderSender.getLastName() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getAddressLine1())) {
				sb.append(" and address_line1 = '" + outWarehouseOrderSender.getAddressLine1() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getAddressLine2())) {
				sb.append(" and address_line2 = '" + outWarehouseOrderSender.getAddressLine2() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getPhoneNumber())) {
				sb.append(" and phone_number = '" + outWarehouseOrderSender.getPhoneNumber() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getMobileNumber())) {
				sb.append(" and mobile_number = '" + outWarehouseOrderSender.getMobileNumber() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderSender.getEmail())) {
				sb.append(" and email = '" + outWarehouseOrderSender.getEmail() + "' ");
			}
			if (outWarehouseOrderSender.getId() != null) {
				sb.append(" and id = '" + outWarehouseOrderSender.getId() + "' ");
			}
			if (outWarehouseOrderSender.getOutWarehouseOrderId() != null) {
				sb.append(" and out_warehouse_order_id = '" + outWarehouseOrderSender.getOutWarehouseOrderId() + "' ");
			}
		}
		// 分页sql
		sb.append(page.generatePageSql());
		String sql = sb.toString();
		logger.info("查询出库订单发件人sql:" + sql);
		List<OutWarehouseOrderSender> outWarehouseOrderSenderList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(OutWarehouseOrderSender.class));
		return outWarehouseOrderSenderList;
	}
}