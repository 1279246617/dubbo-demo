package com.coe.wms.dao.unit.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.unit.ICurrencyDao;
import com.coe.wms.model.unit.Currency;
import com.coe.wms.util.SsmNameSpace;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * @author Administrator
 * 
 */
@Repository("currencyDao")
public class CurrencyDaoImpl implements ICurrencyDao {

	Logger logger = Logger.getLogger(CurrencyDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.CURRENCY, expiration = 36000)
	public Currency findCurrencyByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from unit_currency where code ='" + code + "'";
		Currency packageStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Currency>(Currency.class));
		return packageStatus;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughAssignCache(assignedKey = "AllCurrency", namespace = SsmNameSpace.CURRENCY, expiration = 3600)
	public List<Currency> findAllCurrency() {
		String sql = "select id,code,cn,en from unit_currency";
		List<Currency> statusList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(Currency.class));
		return statusList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}