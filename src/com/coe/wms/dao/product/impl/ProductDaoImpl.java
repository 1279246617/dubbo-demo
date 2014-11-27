package com.coe.wms.dao.product.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.product.IProductDao;
import com.coe.wms.model.product.Product;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

@Repository("productDao")
public class ProductDaoImpl implements IProductDao {
	Logger logger = Logger.getLogger(ProductDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplatek(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Product> findAllProduct(Product product,
			Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer(
				"select id,user_id_of_customer,product_name,product_type_id,sku,warehouse_sku,remark,currency,customs_weight,is_need_batch_no,model,customs_value,origin,last_update_time,created_time,tax_code,volume from p_product where 1=1 ");
		if (product != null) {
			if (product.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer= '"+product.getUserIdOfCustomer()+"' ");
			}
			if(StringUtil.isNotNull(product.getProductName())){
				sb.append(" and product_name like '%"+product.getProductName()+"%' ");
			}
			if(StringUtil.isNotNull(product.getSku())){
				sb.append(" and product_name like '%"+product.getSku()+"%' ");
				sb.append(" and sku like '%"+product.getSku()+"%' ");
			}
		}
		if (moreParam != null) {
			if (moreParam.get("createdTimeStart") != null) {
				Date date = DateUtil.stringConvertDate(
						moreParam.get("createdTimeStart"),
						DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time >= " + date.getTime());
				}
			}
			if (moreParam.get("createdTimeEnd") != null) {
				Date date = DateUtil.stringConvertDate(
						moreParam.get("createdTimeEnd"),
						DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time <= " + date.getTime());
				}
			}
		}
		if (page != null) {
			// 分页sql
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		logger.info("查询产品信息SQL："+sql);
		List<Product> prodcutList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(Product.class));
		return prodcutList;
	}

	@Override
	public Long countProduct(Product product, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer(
				"select count(id) from p_product where 1=1 ");
		String sql = sb.toString();
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	@Override
	public long saveProduct(final Product product) {
		final String sql = "insert into p_product (id,user_id_of_customer,product_name,product_type_id,sku,warehouse_sku,remark,currency,customs_weight,is_need_batch_no,model,customs_value,origin,last_update_time,created_time,tax_code,volume) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, product.getUserIdOfCustomer());
				ps.setString(2, product.getProductName());
				ps.setLong(3, product.getProductTypeId());
				ps.setString(4, product.getSku());
				ps.setString(5, product.getWarehouseSku());
				ps.setString(6, product.getRemark());
				ps.setString(7, product.getCurrency());
				if (product.getCustomsWeight() == null) {
					ps.setNull(8, Types.DOUBLE);
				} else {
					ps.setDouble(8, product.getCustomsWeight());
				}
				ps.setString(9, product.getIsNeedBatchNo());
				ps.setString(10, product.getModel());
				if (product.getCustomsValue() == null) {
					ps.setNull(11, Types.DOUBLE);
				} else {
					ps.setDouble(11, product.getCustomsValue());
				}
				ps.setString(12, product.getOrigin());
				if (product.getLastUpdateTime() == null) {
					ps.setNull(13, Types.INTEGER);
				} else {

					ps.setLong(13, product.getLastUpdateTime());
				}
				ps.setLong(14, product.getCreatedTime());
				ps.setString(15, product.getTaxCode());
				if (product.getVolume() == null) {
					ps.setNull(16, Types.DOUBLE);
				} else {
					ps.setDouble(16, product.getVolume());
				}
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}
	
	@Override
	public Product getProductById(Long id) {
		String sql = "select id,user_id_of_customer,product_name,product_type_id,sku,warehouse_sku,remark,currency,customs_weight,is_need_batch_no,model,customs_value,origin,last_update_time,created_time,tax_code,volume from p_product where id= "+ id;
		Product product = jdbcTemplate.queryForObject(sql, Product.class);
		return product;
	}
	 
}
