package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IOutShelfDao;
import com.coe.wms.model.warehouse.storage.record.OutShelf;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("outShelfDao")
public class OutShelfDaoImpl implements IOutShelfDao {

	Logger logger = Logger.getLogger(OutShelfDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个物品
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOutShelf(final OutShelf item) {
		final String sql = "insert into w_s_out_shelf (warehouse_id,user_id_of_operator,user_id_of_customer,out_warehouse_order_id,batch_no,seat_code,quantity,sku,created_time,customer_reference_no) values (?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, item.getWarehouseId());
				ps.setLong(2, item.getUserIdOfOperator());
				ps.setLong(3, item.getUserIdOfCustomer());
				ps.setLong(4, item.getOutWarehouseOrderId());
				ps.setString(5, item.getBatchNo());
				ps.setString(6, item.getSeatCode());
				ps.setInt(7, item.getQuantity());
				ps.setString(8, item.getSku());
				ps.setLong(9, item.getCreatedTime());
				ps.setString(10, item.getCustomerReferenceNo());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 查询入库记录
	 * 
	 * 参数一律使用实体类加Map . 节省QueryVO
	 */
	@Override
	public List<OutShelf> findOutShelf(OutShelf outShelf, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_operator,user_id_of_customer,out_warehouse_order_id,batch_no,seat_code,quantity,sku,created_time,customer_reference_no from w_s_out_shelf where 1=1 ");
		if (outShelf != null) {
			if (outShelf.getId() != null) {
				sb.append(" and id = " + outShelf.getId());
			}
			if (StringUtil.isNotNull(outShelf.getSku())) {
				sb.append(" and sku = '" + outShelf.getSku() + "' ");
			}
			if (StringUtil.isNotNull(outShelf.getCustomerReferenceNo())) {
				sb.append(" and customer_reference_no = '" + outShelf.getCustomerReferenceNo() + "' ");
			}
			if (StringUtil.isNotNull(outShelf.getBatchNo())) {
				sb.append(" and batch_no = '" + outShelf.getBatchNo() + "' ");
			}
			if (StringUtil.isNotNull(outShelf.getSeatCode())) {
				sb.append(" and seat_code = '" + outShelf.getSeatCode() + "' ");
			}
			if (outShelf.getOutWarehouseOrderId() != null) {
				sb.append(" and out_warehouse_order_id = " + outShelf.getOutWarehouseOrderId());
			}
			if (outShelf.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + outShelf.getWarehouseId());
			}
			if (outShelf.getQuantity() != null) {
				sb.append(" and quantity = " + outShelf.getQuantity());
			}
			if (outShelf.getCreatedTime() != null) {
				sb.append(" and created_time = " + outShelf.getCreatedTime());
			}
			if (outShelf.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + outShelf.getUserIdOfOperator());
			}
			if (outShelf.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + outShelf.getUserIdOfCustomer());
			}
		}
		if (moreParam != null) {
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
		// 分页sql
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		logger.info("查询上架记录明细sql:" + sql);
		List<OutShelf> outShelfList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OutShelf.class));
		logger.info("查询上架记录明细sql:" + sql + " size:" + outShelfList.size());
		return outShelfList;
	}

	/**
	 * 查询入库明细记录
	 */
	@Override
	public Long countOutShelf(OutShelf outShelf, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1) from w_s_out_shelf where 1=1 ");
		if (outShelf != null) {
			if (outShelf.getId() != null) {
				sb.append(" and id = " + outShelf.getId());
			}
			if (StringUtil.isNotNull(outShelf.getSku())) {
				sb.append(" and sku = '" + outShelf.getSku() + "' ");
			}
			if (StringUtil.isNotNull(outShelf.getCustomerReferenceNo())) {
				sb.append(" and customer_reference_no = '" + outShelf.getCustomerReferenceNo() + "' ");
			}
			if (StringUtil.isNotNull(outShelf.getBatchNo())) {
				sb.append(" and batch_no = '" + outShelf.getBatchNo() + "' ");
			}
			if (StringUtil.isNotNull(outShelf.getSeatCode())) {
				sb.append(" and seat_code = '" + outShelf.getSeatCode() + "' ");
			}
			if (outShelf.getOutWarehouseOrderId() != null) {
				sb.append(" and out_warehouse_order_id = " + outShelf.getOutWarehouseOrderId());
			}
			if (outShelf.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + outShelf.getWarehouseId());
			}
			if (outShelf.getQuantity() != null) {
				sb.append(" and quantity = " + outShelf.getQuantity());
			}
			if (outShelf.getCreatedTime() != null) {
				sb.append(" and created_time = " + outShelf.getCreatedTime());
			}
			if (outShelf.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + outShelf.getUserIdOfOperator());
			}
			if (outShelf.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + outShelf.getUserIdOfCustomer());
			}
		}
		if (moreParam != null) {
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
		String sql = sb.toString();
		logger.info("统计上架明细记录sql:" + sql);
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	@Override
	public Integer countOutShelfSkuQuantity(Long outWarehouseOrderId, String sku) {
		String sql = "select sum(quantity) from w_s_out_shelf where sku = '" + sku + "' and out_warehouse_order_id = " + outWarehouseOrderId;
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		if (count == null) {
			return 0;
		}
		return count.intValue();
	}
}