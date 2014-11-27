package com.coe.wms.dao.product.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
		String sql = "select id,product_type_name from p_product_type where id = "+id;
		ProductType productType = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ProductType>(ProductType.class));
		logger.debug("从数据库查询产品类型:" + sql + " 参数:主键:" + id);
		return productType;
	}

}
