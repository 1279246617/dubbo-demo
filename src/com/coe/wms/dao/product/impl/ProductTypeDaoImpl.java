package com.coe.wms.dao.product.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.product.IProductTypeDao;
import com.coe.wms.model.product.ProductType;

@Repository("productTypeDao")
public class ProductTypeDaoImpl implements IProductTypeDao {

	Logger logger = Logger.getLogger(ProductDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplatek(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public ProductType getProductTypeById(Long id) {
		String sql = "select id,user_id_of_customer,product_type_name from p_product_type where id = " + id;
		ProductType productType = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ProductType>(ProductType.class));
		return productType;
	}

	@Override
	public List<ProductType> getAllProductType() {
		String sql = "select id,user_id_of_customer,product_type_name from p_product_type";
		return jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ProductType.class));
	}

	@Override
	public List<ProductType> getProductTypeByCustomerId(Long userIdOfCustomer) {
		String sql = "select id,user_id_of_customer,product_type_name from p_product_type where user_id_of_customer = ?";
		return jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ProductType.class), userIdOfCustomer);
	}
}
