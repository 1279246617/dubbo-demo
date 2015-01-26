package com.coe.wms.dao.warehouse.transport.impl;

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
import com.coe.wms.dao.warehouse.transport.IOrderSenderDao;
import com.coe.wms.model.warehouse.transport.OrderSender;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("orderSenderDao")
public class OrderSenderDaoImpl implements IOrderSenderDao {

	Logger logger = Logger.getLogger(OrderSenderDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个发件人
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOrderSender(final OrderSender sender) {
		final String sql = "insert into w_t_order_sender (order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, sender.getOrderId());
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
	public int saveBatchOrderSender(final List<OrderSender> senderList) {
		final String sql = "insert into w_t_order_sender (order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OrderSender sender = senderList.get(i);
				ps.setLong(1, sender.getOrderId());
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
	public int saveBatchOrderSenderWithPackageId(final List<OrderSender> senderList, final Long orderId) {
		final String sql = "insert into w_t_order_sender (order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OrderSender sender = senderList.get(i);
				ps.setLong(1, sender.getOrderId());
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
	 * 
	 */
	@Override
	public List<OrderSender> findOrderSender(OrderSender orderSender, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2 from w_t_order_sender where 1=1 ");
		if (orderSender != null) {
			if (StringUtil.isNotNull(orderSender.getName())) {
				sb.append(" and name = '" + orderSender.getName() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getCompany())) {
				sb.append(" and company = '" + orderSender.getCompany() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getStateOrProvince())) {
				sb.append(" and state_or_province = '" + orderSender.getStateOrProvince() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getCity())) {
				sb.append(" and city = '" + orderSender.getCity() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getCounty())) {
				sb.append(" and county = '" + orderSender.getCounty() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getPostalCode())) {
				sb.append(" and postal_code = '" + orderSender.getPostalCode() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getCountryName())) {
				sb.append(" and country_name = '" + orderSender.getCountryName() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getCountryCode())) {
				sb.append(" and country_code = '" + orderSender.getCountryCode() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getFirstName())) {
				sb.append(" and first_name = '" + orderSender.getFirstName() + "' ");
			}
			if (orderSender.getLastName() != null) {
				sb.append(" and last_name = '" + orderSender.getLastName() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getAddressLine1())) {
				sb.append(" and address_line1 = '" + orderSender.getAddressLine1() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getAddressLine2())) {
				sb.append(" and address_line2 = '" + orderSender.getAddressLine2() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getPhoneNumber())) {
				sb.append(" and phone_number = '" + orderSender.getPhoneNumber() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getMobileNumber())) {
				sb.append(" and mobile_number = '" + orderSender.getMobileNumber() + "' ");
			}
			if (StringUtil.isNotNull(orderSender.getEmail())) {
				sb.append(" and email = '" + orderSender.getEmail() + "' ");
			}
			if (orderSender.getId() != null) {
				sb.append(" and id = " + orderSender.getId());
			}
			if (orderSender.getOrderId() != null) {
				sb.append(" and order_id = " + orderSender.getOrderId());
			}
		}
		// 分页sql
		sb.append(page.generatePageSql());
		String sql = sb.toString();
		List<OrderSender> orderSenderList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderSender.class));
		return orderSenderList;
	}

	@Override
	public OrderSender getOrderSenderByOrderId(Long orderId) {
		String sql = "select id,order_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2 from w_t_order_sender  where order_id =  "
				+ orderId;
		List<OrderSender> orderSenderList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderSender.class));
		if (orderSenderList != null && orderSenderList.size() > 0) {
			return orderSenderList.get(0);
		}
		return null;
	}
}