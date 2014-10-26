package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
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
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.model.warehouse.storage.record.OnShelf;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("onShelfDao")
public class OnShelfDaoImpl implements IOnShelfDao {

	Logger logger = Logger.getLogger(OnShelfDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个物品
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOnShelf(final OnShelf item) {
		final String sql = "insert into w_s_on_shelf (warehouse_id,user_id_of_operator,user_id_of_customer,in_warehouse_record_id,batch_no,seat_code,quantity,sku,created_time) values (?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, item.getWarehouseId());
				ps.setLong(2, item.getUserIdOfOperator());
				ps.setLong(3, item.getUserIdOfCustomer());
				ps.setLong(4, item.getInWarehouseRecordId());
				ps.setString(5, item.getBatchNo());
				ps.setString(6, item.getSeatCode());
				ps.setInt(7, item.getQuantity());
				ps.setString(8, item.getSku());
				ps.setLong(9, item.getCreatedTime());
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
	public List<OnShelf> findOnShelf(OnShelf onShelf, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_operator,user_id_of_customer,in_warehouse_record_id,batch_no,seat_code,quantity,sku,created_time from w_s_on_shelf where 1=1 ");
		if (onShelf != null) {
			if (onShelf.getId() != null) {
				sb.append(" and id = " + onShelf.getId());
			}
			if (StringUtil.isNotNull(onShelf.getSku())) {
				sb.append(" and sku = '" + onShelf.getSku() + "' ");
			}
			if (StringUtil.isNotNull(onShelf.getBatchNo())) {
				sb.append(" and batch_no = '" + onShelf.getBatchNo() + "' ");
			}
			if (StringUtil.isNotNull(onShelf.getSeatCode())) {
				sb.append(" and seat_code = '" + onShelf.getSeatCode() + "' ");
			}
			if (onShelf.getInWarehouseRecordId() != null) {
				sb.append(" and in_warehouse_record_id = " + onShelf.getInWarehouseRecordId());
			}
			if (onShelf.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + onShelf.getWarehouseId());
			}
			if (onShelf.getQuantity() != null) {
				sb.append(" and quantity = " + onShelf.getQuantity());
			}
			if (onShelf.getCreatedTime() != null) {
				sb.append(" and created_time = " + onShelf.getCreatedTime());
			}
			if (onShelf.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + onShelf.getUserIdOfOperator());
			}
			if (onShelf.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + onShelf.getUserIdOfCustomer());
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
		List<OnShelf> onShelfList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OnShelf.class));
		logger.info("查询上架记录明细sql:" + sql + " size:" + onShelfList.size());
		return onShelfList;
	}

	/**
	 * 查询入库明细记录
	 */
	@Override
	public Long countOnShelf(OnShelf onShelf, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1) from w_s_on_shelf where 1=1 ");
		if (onShelf != null) {
			if (onShelf.getId() != null) {
				sb.append(" and id = " + onShelf.getId());
			}
			if (StringUtil.isNotNull(onShelf.getSku())) {
				sb.append(" and sku = '" + onShelf.getSku() + "' ");
			}
			if (StringUtil.isNotNull(onShelf.getBatchNo())) {
				sb.append(" and batch_no = '" + onShelf.getBatchNo() + "' ");
			}
			if (StringUtil.isNotNull(onShelf.getSeatCode())) {
				sb.append(" and seat_code = '" + onShelf.getSeatCode() + "' ");
			}
			if (onShelf.getInWarehouseRecordId() != null) {
				sb.append(" and in_warehouse_record_id = " + onShelf.getInWarehouseRecordId());
			}
			if (onShelf.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + onShelf.getWarehouseId());
			}
			if (onShelf.getQuantity() != null) {
				sb.append(" and quantity = " + onShelf.getQuantity());
			}
			if (onShelf.getCreatedTime() != null) {
				sb.append(" and created_time = " + onShelf.getCreatedTime());
			}
			if (onShelf.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + onShelf.getUserIdOfOperator());
			}
			if (onShelf.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + onShelf.getUserIdOfCustomer());
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
	public Integer countOnShelfSkuQuantity(Long inWarehouseRecordId, String sku) {
		String sql = "select sum(quantity) from w_s_on_shelf where sku = '" + sku + "' and in_warehouse_record_" + inWarehouseRecordId;
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		return count.intValue();
	}
}