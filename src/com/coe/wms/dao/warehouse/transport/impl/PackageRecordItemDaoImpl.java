package com.coe.wms.dao.warehouse.transport.impl;

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
import com.coe.wms.dao.warehouse.transport.IPackageRecordItemDao;
import com.coe.wms.model.warehouse.transport.PackageRecordItem;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("packageRecordItemDao")
public class PackageRecordItemDaoImpl implements IPackageRecordItemDao {

	Logger logger = Logger.getLogger(PackageRecordItemDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long savePackageRecordItem(final PackageRecordItem shipping) {
		final String sql = "insert into w_t_out_warehouse_package_record (warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,big_package_tracking_no,big_package_id) values (?,?,?,?,?,?,?,?)";
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
				ps.setString(7, shipping.getBigPackageTrackingNo());
				ps.setLong(8, shipping.getBigPackageId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public PackageRecordItem getPackageRecordItemById(Long outWarehouseShippingId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,big_package_tracking_no,big_package_id from w_t_out_warehouse_package_record where id =" + outWarehouseShippingId;
		PackageRecordItem shipping = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<PackageRecordItem>(PackageRecordItem.class));
		return shipping;
	}

	/**
	 * 查询入库订单
	 * 
	 * 参数一律使用实体类加Map .
	 */
	@Override
	public List<PackageRecordItem> findPackageRecordItem(PackageRecordItem outWarehouseShipping, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,big_package_tracking_no,big_package_id from w_t_out_warehouse_package_record where 1=1 ");
		if (outWarehouseShipping != null) {
			if (outWarehouseShipping.getId() != null) {
				sb.append(" and id = " + outWarehouseShipping.getId());
			}
			if (outWarehouseShipping.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + outWarehouseShipping.getWarehouseId());
			}
			if (outWarehouseShipping.getBigPackageId() != null) {
				sb.append(" and big_package_id = " + outWarehouseShipping.getBigPackageId());
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
			if (StringUtil.isNotNull(outWarehouseShipping.getBigPackageTrackingNo())) {
				sb.append(" and big_package_tracking_no = '" + outWarehouseShipping.getBigPackageTrackingNo() + "' ");
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
		List<PackageRecordItem> outWarehouseShippingList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(PackageRecordItem.class));
		return outWarehouseShippingList;
	}

	@Override
	public Long countPackageRecordItem(PackageRecordItem outWarehouseShipping, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_t_out_warehouse_package_record where 1=1 ");
		if (outWarehouseShipping != null) {
			if (outWarehouseShipping.getId() != null) {
				sb.append(" and id = " + outWarehouseShipping.getId());
			}
			if (outWarehouseShipping.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + outWarehouseShipping.getWarehouseId());
			}
			if (outWarehouseShipping.getBigPackageId() != null) {
				sb.append(" and big_package_id = " + outWarehouseShipping.getBigPackageId());
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
			if (StringUtil.isNotNull(outWarehouseShipping.getBigPackageTrackingNo())) {
				sb.append(" and big_package_tracking_no = '" + outWarehouseShipping.getBigPackageTrackingNo() + "' ");
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
	public int deletePackageRecordItemById(Long id) {
		String sql = "delete from w_t_out_warehouse_package_record where id =" + id;
		return jdbcTemplate.update(sql);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Long> getBigPackageIdsByRecordTime(String startTime, String endTime, Long userIdOfCustomer, Long warehouseId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select big_package_id  from w_t_out_warehouse_package_record i inner join w_s_out_warehouse_record r on i.coe_tracking_no_id=r.coe_tracking_no_id where 1=1 ");
		sb.append(" and r.user_id_of_customer = " + userIdOfCustomer);
		sb.append(" and r.warehouse_id = " + warehouseId);
		if (startTime != null) {
			Date date = DateUtil.stringConvertDate(startTime, DateUtil.yyyy_MM_ddHHmmss);
			if (date != null) {
				sb.append(" and r.created_time >= " + date.getTime());
			}
		}
		if (endTime != null) {
			Date date = DateUtil.stringConvertDate(endTime, DateUtil.yyyy_MM_ddHHmmss);
			if (date != null) {
				sb.append(" and r.created_time <= " + date.getTime());
			}
		}
		List<Long> outWarehouseShippingList = jdbcTemplate.queryForList(sb.toString(), Long.class);
		return outWarehouseShippingList;
	}

}