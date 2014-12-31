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
import com.coe.wms.dao.warehouse.transport.IBigPackageAdditionalSfDao;
import com.coe.wms.model.warehouse.transport.OrderAdditionalSf;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("bigPackageAdditionalSfDao")
public class BigPackageAdditionalSfDaoImpl implements IBigPackageAdditionalSfDao {

	Logger logger = Logger.getLogger(BigPackageAdditionalSfDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveBigPackageAdditionalSf(final OrderAdditionalSf additionalSf) {
		final String sql = "insert into w_t_order_additional_sf (big_package_id,carrier_code,mail_no,sender_address,cust_id,pay_method,shipper_code,delivery_code,customer_order_id) values (?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, additionalSf.getBigPackageId());
				ps.setString(2, additionalSf.getCarrierCode());
				ps.setString(3, additionalSf.getMailNo());
				ps.setString(4, additionalSf.getSenderAddress());
				ps.setString(5, additionalSf.getCustId());
				ps.setString(6, additionalSf.getPayMethod());
				ps.setString(7, additionalSf.getShipperCode());
				ps.setString(8, additionalSf.getDeliveryCode());
				ps.setString(9, additionalSf.getCustomerOrderId());
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
	public int saveBatchBigPackageAdditionalSf(final List<OrderAdditionalSf> receiverList) {
		final String sql = "insert into w_t_order_additional_sf (big_package_id,carrier_code,mail_no,sender_address,cust_id,pay_method,shipper_code,delivery_code,customer_order_id) values (?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OrderAdditionalSf additionalSf = receiverList.get(i);
				ps.setLong(1, additionalSf.getBigPackageId());
				ps.setString(2, additionalSf.getCarrierCode());
				ps.setString(3, additionalSf.getMailNo());
				ps.setString(4, additionalSf.getSenderAddress());
				ps.setString(5, additionalSf.getCustId());
				ps.setString(6, additionalSf.getPayMethod());
				ps.setString(7, additionalSf.getShipperCode());
				ps.setString(8, additionalSf.getDeliveryCode());
				ps.setString(9, additionalSf.getCustomerOrderId());
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
	public int saveBatchBigPackageAdditionalSfWithPackageId(final List<OrderAdditionalSf> receiverList, final Long bigPackageId) {
		final String sql = "insert into w_t_order_additional_sf (big_package_id,carrier_code,mail_no,sender_address,cust_id,pay_method,shipper_code,delivery_code,customer_order_id) values (?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OrderAdditionalSf additionalSf = receiverList.get(i);
				ps.setLong(1, bigPackageId);
				ps.setString(2, additionalSf.getCarrierCode());
				ps.setString(3, additionalSf.getMailNo());
				ps.setString(4, additionalSf.getSenderAddress());
				ps.setString(5, additionalSf.getCustId());
				ps.setString(6, additionalSf.getPayMethod());
				ps.setString(7, additionalSf.getShipperCode());
				ps.setString(8, additionalSf.getDeliveryCode());
				ps.setString(9, additionalSf.getCustomerOrderId());
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
	public List<OrderAdditionalSf> findBigPackageAdditionalSf(OrderAdditionalSf BigPackageAdditionalSf, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,big_package_id,carrier_code,mail_no,sender_address,cust_id,pay_method,shipper_code,delivery_code,customer_order_id from w_t_order_additional_sf where 1=1 ");
		if (BigPackageAdditionalSf != null) {
			if (StringUtil.isNotNull(BigPackageAdditionalSf.getCarrierCode())) {
				sb.append(" and carrier_code = '" + BigPackageAdditionalSf.getCarrierCode() + "' ");
			}
			if (StringUtil.isNotNull(BigPackageAdditionalSf.getMailNo())) {
				sb.append(" and mail_no = '" + BigPackageAdditionalSf.getMailNo() + "' ");
			}
			if (BigPackageAdditionalSf.getId() != null) {
				sb.append(" and id = '" + BigPackageAdditionalSf.getId() + "' ");
			}
			if (BigPackageAdditionalSf.getBigPackageId() != null) {
				sb.append(" and big_package_id = " + BigPackageAdditionalSf.getBigPackageId());
			}
		}
		// 分页sql
		sb.append(page.generatePageSql());
		String sql = sb.toString();
		List<OrderAdditionalSf> BigPackageAdditionalSfList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderAdditionalSf.class));
		return BigPackageAdditionalSfList;
	}

	@Override
	public OrderAdditionalSf getBigPackageAdditionalSfByPackageId(Long orderId) {
		String sql = "select id,big_package_id,carrier_code,mail_no,sender_address,cust_id,pay_method,shipper_code,delivery_code,customer_order_id from w_t_order_additional_sf where big_package_id = " + orderId;
		List<OrderAdditionalSf> BigPackageAdditionalSfList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OrderAdditionalSf.class));
		if (BigPackageAdditionalSfList != null && BigPackageAdditionalSfList.size() > 0) {
			return BigPackageAdditionalSfList.get(0);
		}
		return null;
	}
}