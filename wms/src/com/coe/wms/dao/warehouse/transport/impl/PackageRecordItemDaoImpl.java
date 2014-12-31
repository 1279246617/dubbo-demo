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
import com.coe.wms.model.warehouse.transport.OutWarehousePackageItem;
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
	public long savePackageRecordItem(final OutWarehousePackageItem item) {
		final String sql = "insert into w_t_out_warehouse_package_item (warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,big_package_tracking_no,big_package_id) values (?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, item.getWarehouseId());
				ps.setLong(2, item.getUserIdOfCustomer());
				if (item.getUserIdOfOperator() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setDouble(3, item.getUserIdOfOperator());
				}
				ps.setString(4, item.getCoeTrackingNo());
				ps.setLong(5, item.getCoeTrackingNoId());
				ps.setLong(6, item.getCreatedTime());
				ps.setString(7, item.getBigPackageTrackingNo());
				ps.setLong(8, item.getBigPackageId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public OutWarehousePackageItem getPackageRecordItemById(Long packageRecordItemId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,big_package_tracking_no,big_package_id from w_t_out_warehouse_package_item where id =" + packageRecordItemId;
		OutWarehousePackageItem item = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<OutWarehousePackageItem>(OutWarehousePackageItem.class));
		return item;
	}

	/**
	 * 查询入库订单
	 * 
	 * 参数一律使用实体类加Map .
	 */
	@Override
	public List<OutWarehousePackageItem> findPackageRecordItem(OutWarehousePackageItem packageRecordItem, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,big_package_tracking_no,big_package_id from w_t_out_warehouse_package_item where 1=1 ");
		if (packageRecordItem != null) {
			if (packageRecordItem.getId() != null) {
				sb.append(" and id = " + packageRecordItem.getId());
			}
			if (packageRecordItem.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + packageRecordItem.getWarehouseId());
			}
			if (packageRecordItem.getBigPackageId() != null) {
				sb.append(" and big_package_id = " + packageRecordItem.getBigPackageId());
			}
			if (packageRecordItem.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + packageRecordItem.getUserIdOfCustomer());
			}
			if (packageRecordItem.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + packageRecordItem.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(packageRecordItem.getCoeTrackingNo())) {
				sb.append(" and coe_tracking_no = '" + packageRecordItem.getCoeTrackingNo() + "' ");
			}
			if (packageRecordItem.getCoeTrackingNoId() != null) {
				sb.append(" and coe_tracking_no_id = " + packageRecordItem.getCoeTrackingNoId());
			}
			if (packageRecordItem.getCreatedTime() != null) {
				sb.append(" and created_time = " + packageRecordItem.getCreatedTime());
			}
			if (StringUtil.isNotNull(packageRecordItem.getBigPackageTrackingNo())) {
				sb.append(" and big_package_tracking_no = '" + packageRecordItem.getBigPackageTrackingNo() + "' ");
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
		List<OutWarehousePackageItem> packageRecordItemList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OutWarehousePackageItem.class));
		return packageRecordItemList;
	}

	@Override
	public Long countPackageRecordItem(OutWarehousePackageItem packageRecordItem, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_t_out_warehouse_package_item where 1=1 ");
		if (packageRecordItem != null) {
			if (packageRecordItem.getId() != null) {
				sb.append(" and id = " + packageRecordItem.getId());
			}
			if (packageRecordItem.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + packageRecordItem.getWarehouseId());
			}
			if (packageRecordItem.getBigPackageId() != null) {
				sb.append(" and big_package_id = " + packageRecordItem.getBigPackageId());
			}
			if (packageRecordItem.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + packageRecordItem.getUserIdOfCustomer());
			}
			if (packageRecordItem.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + packageRecordItem.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(packageRecordItem.getCoeTrackingNo())) {
				sb.append(" and coe_tracking_no = '" + packageRecordItem.getCoeTrackingNo() + "' ");
			}
			if (packageRecordItem.getCoeTrackingNoId() != null) {
				sb.append(" and coe_tracking_no_id = " + packageRecordItem.getCoeTrackingNoId());
			}
			if (packageRecordItem.getCreatedTime() != null) {
				sb.append(" and created_time = " + packageRecordItem.getCreatedTime());
			}
			if (StringUtil.isNotNull(packageRecordItem.getBigPackageTrackingNo())) {
				sb.append(" and big_package_tracking_no = '" + packageRecordItem.getBigPackageTrackingNo() + "' ");
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
		String sql = "delete from w_t_out_warehouse_package_item where id =" + id;
		return jdbcTemplate.update(sql);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Long> getBigPackageIdsByRecordTime(String startTime, String endTime, Long userIdOfCustomer, Long warehouseId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select big_package_id  from w_t_out_warehouse_package_item i inner join w_t_first_waybill r on i.coe_tracking_no_id=r.coe_tracking_no_id where 1=1 ");
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
		List<Long> packageRecordItemList = jdbcTemplate.queryForList(sb.toString(), Long.class);
		return packageRecordItemList;
	}

}