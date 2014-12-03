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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

	/**
	 * 查询产品
	 */
	@Override
	public List<Product> findProduct(Product product, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer(
				"select id,user_id_of_customer,product_name,product_type_id,sku,warehouse_sku,remark,currency,customs_weight,is_need_batch_no,model,customs_value,origin,last_update_time,created_time,tax_code,volume from p_product where 1=1 ");
		if (product != null) {
			if (product.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer= " + product.getUserIdOfCustomer());
			}
			if (product.getId() != null) {
				sb.append(" and id= " + product.getId());
			}
			if (product.getProductTypeId() != null) {
				sb.append(" and product_type_id= " + product.getProductTypeId());
			}
			if (product.getProductName() != null) {
				sb.append(" and product_name= '" + product.getProductName() + "'");
			}
			if (product.getSku() != null) {
				sb.append(" and sku= '" + product.getSku() + "'");
			}
			if (product.getModel() != null) {
				sb.append(" and model= '" + product.getModel() + "'");
			}
			if (product.getWarehouseSku() != null) {
				sb.append(" and warehouse_sku= '" + product.getWarehouseSku() + "'");
			}
			if (product.getTaxCode() != null) {
				sb.append(" and tax_code= '" + product.getTaxCode() + "'");
			}
		}
		if (moreParam != null) {
			if (StringUtil.isNotNull(moreParam.get("keyword"))) {
				sb.append(" and (product_name like '%" + moreParam.get("keyword") + "%'");
				sb.append(" or sku like '%" + moreParam.get("keyword") + "%' )");
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
		List<Product> prodcutList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(Product.class));
		return prodcutList;
	}

	/**
	 * 删除产品
	 */
	@Override
	public Long countProduct(Product product, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer("select count(id) from p_product where 1=1 ");
		if (product != null) {
			if (product.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer= " + product.getUserIdOfCustomer());
			}
			if (product.getId() != null) {
				sb.append(" and id= " + product.getId());
			}
			if (product.getProductTypeId() != null) {
				sb.append(" and product_type_id= " + product.getProductTypeId());
			}
			if (product.getProductName() != null) {
				sb.append(" and product_name= '" + product.getProductName() + "'");
			}
			if (product.getSku() != null) {
				sb.append(" and sku= '" + product.getSku() + "'");
			}
			if (product.getModel() != null) {
				sb.append(" and model= '" + product.getModel() + "'");
			}
			if (product.getWarehouseSku() != null) {
				sb.append(" and warehouse_sku= '" + product.getWarehouseSku() + "'");
			}
			if (product.getTaxCode() != null) {
				sb.append(" and tax_code= '" + product.getTaxCode() + "'");
			}
		}
		if (moreParam != null) {
			if (StringUtil.isNotNull(moreParam.get("keyword"))) {
				sb.append(" and (product_name like '%" + moreParam.get("keyword") + "%'");
				sb.append(" or sku like '%" + moreParam.get("keyword") + "%' )");
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
	 * 新增产品
	 */
	@Override
	public long addProduct(final Product product) {
		final String sql = "insert into p_product (user_id_of_customer,product_name,product_type_id,sku,warehouse_sku,remark,currency,customs_weight,is_need_batch_no,model,customs_value,origin,last_update_time,created_time,tax_code,volume) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, product.getUserIdOfCustomer());
				ps.setString(2, product.getProductName());
				if (product.getProductTypeId() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setLong(3, product.getProductTypeId());
				}
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
					ps.setNull(13, Types.BIGINT);
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
		String sql = "select id,user_id_of_customer,product_name,product_type_id,sku,warehouse_sku,remark,currency,customs_weight,is_need_batch_no,model,customs_value,origin,last_update_time,created_time,tax_code,volume from p_product where id= "
				+ id;
		Product product = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Product>(Product.class));
		return product;
	}

	@Override
	public int deleteProductById(Long id) {
		String sql = "delete from p_product where id=?";
		int count = jdbcTemplate.update(sql, id);
		return count;
	}

	@Override
	public int updateProduct(Product product) {
		String sql = "update p_product set user_id_of_customer=?,product_name=?,product_type_id=?,sku=?,warehouse_sku=?,remark=?,currency=?,customs_weight=?,is_need_batch_no=?,model=?,customs_value=?,origin=?,last_update_time=?,tax_code=?,volume=? where id=?";
		int count = jdbcTemplate.update(sql, product.getUserIdOfCustomer(), product.getProductName(), product.getProductTypeId(), product.getSku(), product.getWarehouseSku(), product.getRemark(), product.getCurrency(), product.getCustomsWeight(),
				product.getIsNeedBatchNo(), product.getModel(), product.getCustomsValue(), product.getOrigin(), product.getLastUpdateTime(), product.getTaxCode(), product.getVolume(), product.getId());
		return count;
	}

	@Override
	public int deleteProductByIds(String ids) {
		String sql = "delete from p_product where id in(" + ids + ")";
		return jdbcTemplate.update(sql);
	}
}
