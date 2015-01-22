package com.coe.wms.dao.warehouse.shipway.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.warehouse.shipway.IShipwayTaxDao;
import com.coe.wms.model.warehouse.shipway.ShipwayTax;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

@Repository("shipwayTaxDao")
public class ShipwayTaxDaoImpl implements IShipwayTaxDao {
	Logger logger = Logger.getLogger(ShipwayTaxDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 查询商品
	 */
	@Override
	public List<ShipwayTax> findShipwayTax(ShipwayTax shipwayTax, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer(
				"select id,user_id_of_customer,shipwayTax_name,shipwayTax_type_id,sku,warehouse_sku,remark,currency,customs_weight,is_need_batch_no,model,customs_value,origin,last_update_time,created_time,tax_code,volume,barcode from p_shipwayTax where 1=1 ");
		if (shipwayTax != null) {
			if (shipwayTax.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer= " + shipwayTax.getUserIdOfCustomer());
			}
			if (shipwayTax.getId() != null) {
				sb.append(" and id= " + shipwayTax.getId());
			}
		}
		if (moreParam != null) {
			if (StringUtil.isNotNull(moreParam.get("keyword"))) {
				sb.append(" and (shipwayTax_name like '%" + moreParam.get("keyword") + "%'");
				sb.append(" or sku like '%" + moreParam.get("keyword") + "%' ");
				sb.append(" or barcode like '%" + moreParam.get("keyword") + "%' )");
			}
			if (moreParam.get("createdTimeStart") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time >= " + date.getTime());
				}
			}
			if (moreParam.get("createdTimeEnd") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeEnd"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time <= " + date.getTime());
				}
			}
		}
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		List<ShipwayTax> prodcutList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ShipwayTax.class));
		return prodcutList;
	}

	/**
	 * 删除商品
	 */
	@Override
	public Long countShipwayTax(ShipwayTax shipwayTax, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer("select count(id) from p_shipwayTax where 1=1 ");
		if (shipwayTax != null) {
			if (shipwayTax.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer= " + shipwayTax.getUserIdOfCustomer());
			}
			if (shipwayTax.getId() != null) {
				sb.append(" and id= " + shipwayTax.getId());
			}
		}
		if (moreParam != null) {
			if (StringUtil.isNotNull(moreParam.get("keyword"))) {
				sb.append(" and (shipwayTax_name like '%" + moreParam.get("keyword") + "%'");
				sb.append(" or sku like '%" + moreParam.get("keyword") + "%' ");
				sb.append(" or barcode like '%" + moreParam.get("keyword") + "%' )");
			}
			if (moreParam.get("createdTimeStart") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time >= " + date.getTime());
				}
			}
			if (moreParam.get("createdTimeEnd") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeEnd"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time <= " + date.getTime());
				}
			}
		}
		return jdbcTemplate.queryForObject(sb.toString(), Long.class);
	}

	/**
	 * 新增商品
	 */
	@Override
	public long addShipwayTax(final ShipwayTax shipwayTax) {
		final String sql = "insert into p_shipwayTax (user_id_of_customer,shipwayTax_name,shipwayTax_type_id,sku,warehouse_sku,remark,currency,customs_weight,is_need_batch_no,model,customs_value,origin,last_update_time,created_time,tax_code,volume,barcode) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, shipwayTax.getUserIdOfCustomer());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public ShipwayTax getShipwayTaxById(Long id) {
		String sql = "select id,user_id_of_customer,shipwayTax_name,shipwayTax_type_id,sku,warehouse_sku,remark,currency,customs_weight,is_need_batch_no,model,customs_value,origin,last_update_time,created_time,tax_code,volume,barcode from p_shipwayTax where id= "
				+ id;
		ShipwayTax shipwayTax = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ShipwayTax>(ShipwayTax.class));
		return shipwayTax;
	}

	@Override
	public int deleteShipwayTaxById(Long id) {
		String sql = "delete from p_shipwayTax where id=?";
		int count = jdbcTemplate.update(sql, id);
		return count;
	}

	@Override
	public int deleteShipwayTaxByIds(String ids) {
		String sql = "delete from p_shipwayTax where id in(" + ids + ")";
		return jdbcTemplate.update(sql);
	}

	@Override
	public ShipwayTax findShipwayTaxByReferenceNo(Long customerId, String barcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateShipwayTax(ShipwayTax shipwayTax) {
		// TODO Auto-generated method stub
		return 0;
	}

}
