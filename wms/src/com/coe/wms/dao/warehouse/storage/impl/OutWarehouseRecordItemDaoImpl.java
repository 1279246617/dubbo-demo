package com.coe.wms.dao.warehouse.storage.impl;

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

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordItemDao;
import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecordItem;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("outWarehouseShippingDao")
public class OutWarehouseRecordItemDaoImpl implements IOutWarehouseRecordItemDao {

	Logger logger = Logger.getLogger(OutWarehouseRecordItemDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOutWarehouseRecordItem(final OutWarehouseRecordItem shipping) {
		final String sql = "insert into w_s_out_warehouse_shipping (warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,our_warehouse_order_tracking_no,out_warehouse_order_id) values (?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, shipping.getWarehouseId());
				ps.setLong(2, shipping.getUserIdOfCustomer());
				if (shipping.getUserIdOfOperator() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setDouble(3, shipping.getUserIdOfOperator());
				}
				ps.setString(4, shipping.getCoeTrackingNo());
				ps.setLong(5, shipping.getCoeTrackingNoId());
				ps.setLong(6, shipping.getCreatedTime());
				ps.setString(7, shipping.getOurWarehouseOrderTrackingNo());
				ps.setLong(8, shipping.getOutWarehouseOrderId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public OutWarehouseRecordItem getOutWarehouseRecordItemById(Long outWarehouseShippingId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,our_warehouse_order_tracking_no,out_warehouse_order_id from w_s_out_warehouse_shipping where id ="
				+ outWarehouseShippingId;
		OutWarehouseRecordItem shipping = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<OutWarehouseRecordItem>(OutWarehouseRecordItem.class));
		return shipping;
	}

	/**
	 * 查询入库订单
	 * 
	 * 参数一律使用实体类加Map . 节省QueryVO
	 */
	@Override
	public List<OutWarehouseRecordItem> findOutWarehouseRecordItem(OutWarehouseRecordItem outWarehouseShipping, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,our_warehouse_order_tracking_no,out_warehouse_order_id from w_s_out_warehouse_shipping where 1=1 ");
		if (outWarehouseShipping != null) {
			if (outWarehouseShipping.getId() != null) {
				sb.append(" and id = " + outWarehouseShipping.getId());
			}
			if (outWarehouseShipping.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + outWarehouseShipping.getWarehouseId());
			}
			if (outWarehouseShipping.getOutWarehouseOrderId() != null) {
				sb.append(" and out_warehouse_order_id = " + outWarehouseShipping.getOutWarehouseOrderId());
			}
			if (outWarehouseShipping.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + outWarehouseShipping.getUserIdOfCustomer());
			}
			if (outWarehouseShipping.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + outWarehouseShipping.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(outWarehouseShipping.getCoeTrackingNo())) {
				sb.append(" and coe_tracking_no = '" + outWarehouseShipping.getCoeTrackingNo() + "' ");
			}
			if (outWarehouseShipping.getCoeTrackingNoId() != null) {
				sb.append(" and coe_tracking_no_id = " + outWarehouseShipping.getCoeTrackingNoId());
			}
			if (outWarehouseShipping.getCreatedTime() != null) {
				sb.append(" and created_time = " + outWarehouseShipping.getCreatedTime());
			}
			if (StringUtil.isNotNull(outWarehouseShipping.getOurWarehouseOrderTrackingNo())) {
				sb.append(" and our_warehouse_order_tracking_no = '" + outWarehouseShipping.getOurWarehouseOrderTrackingNo() + "' ");
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
		if (page != null) {
			// 分页sql
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		List<OutWarehouseRecordItem> outWarehouseShippingList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OutWarehouseRecordItem.class));
		return outWarehouseShippingList;
	}

	@Override
	public Long countOutWarehouseRecordItem(OutWarehouseRecordItem outWarehouseShipping, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_s_out_warehouse_shipping where 1=1 ");
		if (outWarehouseShipping != null) {
			if (outWarehouseShipping.getId() != null) {
				sb.append(" and id = " + outWarehouseShipping.getId());
			}
			if (outWarehouseShipping.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + outWarehouseShipping.getWarehouseId());
			}
			if (outWarehouseShipping.getOutWarehouseOrderId() != null) {
				sb.append(" and out_warehouse_order_id = " + outWarehouseShipping.getOutWarehouseOrderId());
			}
			if (outWarehouseShipping.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + outWarehouseShipping.getUserIdOfCustomer());
			}
			if (outWarehouseShipping.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + outWarehouseShipping.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(outWarehouseShipping.getCoeTrackingNo())) {
				sb.append(" and coe_tracking_no = '" + outWarehouseShipping.getCoeTrackingNo() + "' ");
			}
			if (outWarehouseShipping.getCoeTrackingNoId() != null) {
				sb.append(" and coe_tracking_no_id = " + outWarehouseShipping.getCoeTrackingNoId());
			}
			if (outWarehouseShipping.getCreatedTime() != null) {
				sb.append(" and created_time = " + outWarehouseShipping.getCreatedTime());
			}
			if (StringUtil.isNotNull(outWarehouseShipping.getOurWarehouseOrderTrackingNo())) {
				sb.append(" and our_warehouse_order_tracking_no = '" + outWarehouseShipping.getOurWarehouseOrderTrackingNo() + "' ");
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
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	@Override
	public int deleteOutWarehouseRecordItemById(Long id) {
		String sql = "delete from w_s_out_warehouse_shipping where id =" + id;
		return jdbcTemplate.update(sql);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}