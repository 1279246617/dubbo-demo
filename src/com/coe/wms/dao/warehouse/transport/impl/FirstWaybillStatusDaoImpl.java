package com.coe.wms.dao.warehouse.transport.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.transport.IFirstWaybillStatusDao;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus;
import com.coe.wms.util.SsmNameSpace;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * 
 * 入库订单状态 DAO
 * 
 * @author Administrator
 * 
 */
@Repository("firstWaybillStatusDao")
public class FirstWaybillStatusDaoImpl implements IFirstWaybillStatusDao {

	Logger logger = Logger.getLogger(FirstWaybillStatusDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.TRANSPORT_FIRST_WAYBILL_STATUS, expiration = 36000)
	public FirstWaybillStatus findFirstWaybillStatusByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from w_t_first_waybill_status where code ='" + code + "'";
		FirstWaybillStatus packageStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<FirstWaybillStatus>(FirstWaybillStatus.class));
		return packageStatus;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughAssignCache(assignedKey = "AllFirstWaybillStatus", namespace = SsmNameSpace.TRANSPORT_FIRST_WAYBILL_STATUS, expiration = 3600)
	public List<FirstWaybillStatus> findAllFirstWaybillStatus() {
		String sql = "select id,code,cn,en from w_t_first_waybill_status";
		List<FirstWaybillStatus> statusList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(FirstWaybillStatus.class));
		return statusList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}