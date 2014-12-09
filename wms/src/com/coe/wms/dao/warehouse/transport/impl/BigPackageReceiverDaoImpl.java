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
import com.coe.wms.dao.warehouse.transport.IBigPackageReceiverDao;
import com.coe.wms.model.warehouse.transport.BigPackageReceiver;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("bigPackageReceiverDao")
public class BigPackageReceiverDaoImpl implements IBigPackageReceiverDao {

	Logger logger = Logger.getLogger(BigPackageReceiverDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个出库订单收件人
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveBigPackageReceiver(final BigPackageReceiver receiver) {
		final String sql = "insert into w_t_big_package_receiver (big_package_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, receiver.getBigPackageId());
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
	public int saveBatchBigPackageReceiver(final List<BigPackageReceiver> receiverList) {
		final String sql = "insert into w_t_big_package_receiver (big_package_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				BigPackageReceiver receiver = receiverList.get(i);
				ps.setLong(1, receiver.getBigPackageId());
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
	public int saveBatchBigPackageReceiverWithPackageId(final List<BigPackageReceiver> receiverList, final Long orderId) {
		final String sql = "insert into w_t_big_package_receiver (big_package_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				BigPackageReceiver receiver = receiverList.get(i);
				ps.setLong(1, receiver.getBigPackageId());
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
	 * 参数一律使用实体类加Map .
	 */
	@Override
	public List<BigPackageReceiver> findBigPackageReceiver(BigPackageReceiver orderReceiver, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,big_package_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2 from w_t_big_package_receiver where 1=1 ");
		if (orderReceiver != null) {
			if (StringUtil.isNotNull(orderReceiver.getName())) {
				sb.append(" and name = '" + orderReceiver.getName() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getCompany())) {
				sb.append(" and company = '" + orderReceiver.getCompany() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getStateOrProvince())) {
				sb.append(" and state_or_province = '" + orderReceiver.getStateOrProvince() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getCity())) {
				sb.append(" and city = '" + orderReceiver.getCity() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getCounty())) {
				sb.append(" and county = '" + orderReceiver.getCounty() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getPostalCode())) {
				sb.append(" and postal_code = '" + orderReceiver.getPostalCode() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getCountryName())) {
				sb.append(" and country_name = '" + orderReceiver.getCountryName() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getCountryCode())) {
				sb.append(" and country_code = '" + orderReceiver.getCountryCode() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getFirstName())) {
				sb.append(" and first_name = '" + orderReceiver.getFirstName() + "' ");
			}
			if (orderReceiver.getLastName() != null) {
				sb.append(" and last_name = '" + orderReceiver.getLastName() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getAddressLine1())) {
				sb.append(" and address_line1 = '" + orderReceiver.getAddressLine1() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getAddressLine2())) {
				sb.append(" and address_line2 = '" + orderReceiver.getAddressLine2() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getPhoneNumber())) {
				sb.append(" and phone_number = '" + orderReceiver.getPhoneNumber() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getMobileNumber())) {
				sb.append(" and mobile_number = '" + orderReceiver.getMobileNumber() + "' ");
			}
			if (StringUtil.isNotNull(orderReceiver.getEmail())) {
				sb.append(" and email = '" + orderReceiver.getEmail() + "' ");
			}
			if (orderReceiver.getId() != null) {
				sb.append(" and id = " + orderReceiver.getId());
			}
			if (orderReceiver.getBigPackageId() != null) {
				sb.append(" and big_package_id = " + orderReceiver.getBigPackageId());
			}
		}
		// 分页sql
		sb.append(page.generatePageSql());
		String sql = sb.toString();
		List<BigPackageReceiver> orderReceiverList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(BigPackageReceiver.class));
		return orderReceiverList;
	}

	@Override
	public BigPackageReceiver getBigPackageReceiverByPackageId(Long orderId) {
		String sql = "select id,big_package_id,name,company,first_name,last_name,address_line1,state_or_province,city,county,postal_code,country_code,country_name,phone_number,email,mobile_number,address_line2 from w_t_big_package_receiver where big_package_id = "
				+ orderId;
		List<BigPackageReceiver> orderReceiverList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(BigPackageReceiver.class));
		if (orderReceiverList != null && orderReceiverList.size() > 0) {
			return orderReceiverList.get(0);
		}
		return null;
	}
}