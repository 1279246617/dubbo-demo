package com.coe.wms.dao.warehouse.shipway.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.warehouse.shipway.IShipwayApiAccountDao;
import com.coe.wms.model.warehouse.shipway.ShipwayApiAccount;

/**
 * @author Administrator
 * 
 */
@Repository("shipwayApiAccountDao")
public class ShipwayApiAccountDaoImpl implements IShipwayApiAccountDao {

	Logger logger = Logger.getLogger(ShipwayApiAccountDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public ShipwayApiAccount getShipwayApiAccountByUserId(Long userId, String shipwayCode) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,user_id,shipway_code,api_account,token,token_key,url,extra1 from w_w_shipway_api_account where shipway_code ='" + shipwayCode + "'");
		if (userId == null) {
			sb.append(" and user_id is null");
		} else {
			sb.append(" and user_id  = " + userId);
		}
		List<ShipwayApiAccount> shipwayApiAccountList = jdbcTemplate.query(sb.toString(), ParameterizedBeanPropertyRowMapper.newInstance(ShipwayApiAccount.class));
		if (shipwayApiAccountList == null || shipwayApiAccountList.size() == 0) {
			return null;
		}
		return shipwayApiAccountList.get(0);
	}
}