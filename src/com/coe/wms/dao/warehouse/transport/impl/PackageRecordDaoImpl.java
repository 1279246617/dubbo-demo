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
import com.coe.wms.dao.warehouse.transport.IPackageRecordDao;
import com.coe.wms.model.warehouse.transport.PackageRecord;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("packageRecordDao")
public class PackageRecordDaoImpl implements IPackageRecordDao {

	Logger logger = Logger.getLogger(PackageRecordDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long savePackageRecord(final PackageRecord record) {
		final String sql = "insert into w_t_package_record (warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,remark,is_shiped) values (?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, record.getWarehouseId());
				ps.setLong(2, record.getUserIdOfCustomer());
				if (record.getUserIdOfOperator() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setLong(3, record.getUserIdOfOperator());
				}
				ps.setString(4, record.getCoeTrackingNo());
				ps.setLong(5, record.getCoeTrackingNoId());
				ps.setLong(6, record.getCreatedTime());
				ps.setString(7, record.getRemark());
				ps.setString(8, record.getIsShiped());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public PackageRecord getPackageRecordById(Long outWarehouseRecordId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,remark,is_shiped from w_t_package_record where id =" + outWarehouseRecordId;
		PackageRecord record = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<PackageRecord>(PackageRecord.class));
		return record;
	}

	/**
	 * 查询入库订单
	 * 
	 * 参数一律使用实体类加Map .
	 */
	@Override
	public List<PackageRecord> findPackageRecord(PackageRecord outWarehouseRecord, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,remark,is_shiped from w_t_package_record where 1=1 ");
		if (outWarehouseRecord != null) {
			if (outWarehouseRecord.getId() != null) {
				sb.append(" and id = " + outWarehouseRecord.getId());
			}
			if (outWarehouseRecord.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + outWarehouseRecord.getWarehouseId());
			}
			if (outWarehouseRecord.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + outWarehouseRecord.getUserIdOfCustomer());
			}
			if (outWarehouseRecord.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + outWarehouseRecord.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(outWarehouseRecord.getCoeTrackingNo())) {
				sb.append(" and coe_tracking_no = '" + outWarehouseRecord.getCoeTrackingNo() + "' ");
			}
			if (outWarehouseRecord.getCoeTrackingNoId() != null) {
				sb.append(" and coe_tracking_no_id = " + outWarehouseRecord.getCoeTrackingNoId());
			}
			if (outWarehouseRecord.getCreatedTime() != null) {
				sb.append(" and created_time = " + outWarehouseRecord.getCreatedTime());
			}
			if (StringUtil.isNotNull(outWarehouseRecord.getRemark())) {
				sb.append(" and remark = '" + outWarehouseRecord.getRemark() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseRecord.getIsShiped())) {
				sb.append(" and is_shiped = '" + outWarehouseRecord.getIsShiped() + "' ");
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
		List<PackageRecord> outWarehouseRecordList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(PackageRecord.class));
		return outWarehouseRecordList;
	}

	@Override
	public Long countPackageRecord(PackageRecord outWarehouseRecord, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_t_package_record where 1=1 ");
		if (outWarehouseRecord != null) {
			if (outWarehouseRecord.getId() != null) {
				sb.append(" and id = " + outWarehouseRecord.getId());
			}
			if (outWarehouseRecord.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + outWarehouseRecord.getWarehouseId());
			}
			if (outWarehouseRecord.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + outWarehouseRecord.getUserIdOfCustomer());
			}
			if (outWarehouseRecord.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + outWarehouseRecord.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(outWarehouseRecord.getCoeTrackingNo())) {
				sb.append(" and coe_tracking_no = '" + outWarehouseRecord.getCoeTrackingNo() + "' ");
			}
			if (outWarehouseRecord.getCoeTrackingNoId() != null) {
				sb.append(" and coe_tracking_no_id = " + outWarehouseRecord.getCoeTrackingNoId());
			}
			if (outWarehouseRecord.getCreatedTime() != null) {
				sb.append(" and created_time = " + outWarehouseRecord.getCreatedTime());
			}
			if (StringUtil.isNotNull(outWarehouseRecord.getRemark())) {
				sb.append(" and remark = '" + outWarehouseRecord.getRemark() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseRecord.getIsShiped())) {
				sb.append(" and is_shiped = '" + outWarehouseRecord.getIsShiped() + "' ");
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
	public int updatePackageRecordRemark(Long outWarehouseRecordId, String remark) {
		String sql = "update w_t_package_record set remark ='" + remark + "' where id=" + outWarehouseRecordId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updatePackageRecordIsShiped(Long outWarehouseRecordId, String isShiped) {
		String sql = "update w_t_package_record set is_shiped ='" + isShiped + "' where id=" + outWarehouseRecordId;
		return jdbcTemplate.update(sql);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}