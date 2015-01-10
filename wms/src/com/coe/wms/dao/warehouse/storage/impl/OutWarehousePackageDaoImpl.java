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
import com.coe.wms.dao.warehouse.storage.IOutWarehousePackageDao;
import com.coe.wms.model.warehouse.storage.record.OutWarehousePackage;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("outWarehousePackageDao")
public class OutWarehousePackageDaoImpl implements IOutWarehousePackageDao {

	Logger logger = Logger.getLogger(OutWarehousePackageDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOutWarehousePackage(final OutWarehousePackage Package) {
		final String sql = "insert into w_s_out_warehouse_package (warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,remark,is_shiped) values (?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, Package.getWarehouseId());
				ps.setLong(2, Package.getUserIdOfCustomer());
				if (Package.getUserIdOfOperator() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setDouble(3, Package.getUserIdOfOperator());
				}
				ps.setString(4, Package.getCoeTrackingNo());
				ps.setLong(5, Package.getCoeTrackingNoId());
				ps.setLong(6, Package.getCreatedTime());
				ps.setString(7, Package.getRemark());
				ps.setString(8, Package.getIsShiped());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public OutWarehousePackage getOutWarehousePackageById(Long outWarehousePackageId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,remark,is_shiped,shipped_time from w_s_out_warehouse_package where id =" + outWarehousePackageId;
		OutWarehousePackage Package = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<OutWarehousePackage>(OutWarehousePackage.class));
		return Package;
	}

	/**
	 * 查询入库订单
	 * 
	 * 参数一律使用实体类加Map .
	 */
	@Override
	public List<OutWarehousePackage> findOutWarehousePackage(OutWarehousePackage outWarehousePackage, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,coe_tracking_no,coe_tracking_no_id,created_time,remark,is_shiped,shipped_time from w_s_out_warehouse_package where 1=1 ");
		if (outWarehousePackage != null) {
			if (outWarehousePackage.getId() != null) {
				sb.append(" and id = " + outWarehousePackage.getId());
			}
			if (outWarehousePackage.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + outWarehousePackage.getUserIdOfCustomer());
			}
			if (outWarehousePackage.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + outWarehousePackage.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(outWarehousePackage.getCoeTrackingNo())) {
				sb.append(" and coe_tracking_no = '" + outWarehousePackage.getCoeTrackingNo() + "' ");
			}
			if (outWarehousePackage.getCoeTrackingNoId() != null) {
				sb.append(" and coe_tracking_no_id = " + outWarehousePackage.getCoeTrackingNoId());
			}
			if (outWarehousePackage.getCreatedTime() != null) {
				sb.append(" and created_time = " + outWarehousePackage.getCreatedTime());
			}
			if (StringUtil.isNotNull(outWarehousePackage.getRemark())) {
				sb.append(" and remark = '" + outWarehousePackage.getRemark() + "' ");
			}
			if (StringUtil.isNotNull(outWarehousePackage.getIsShiped())) {
				sb.append(" and is_shiped = '" + outWarehousePackage.getIsShiped() + "' ");
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
		logger.debug("查询出库记录sql:" + sql);
		List<OutWarehousePackage> outWarehousePackageList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OutWarehousePackage.class));
		return outWarehousePackageList;
	}

	@Override
	public Long countOutWarehousePackage(OutWarehousePackage outWarehousePackage, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_s_out_warehouse_package where 1=1 ");
		if (outWarehousePackage != null) {
			if (outWarehousePackage.getId() != null) {
				sb.append(" and id = " + outWarehousePackage.getId());
			}
			if (outWarehousePackage.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + outWarehousePackage.getUserIdOfCustomer());
			}
			if (outWarehousePackage.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + outWarehousePackage.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(outWarehousePackage.getCoeTrackingNo())) {
				sb.append(" and coe_tracking_no = '" + outWarehousePackage.getCoeTrackingNo() + "' ");
			}
			if (outWarehousePackage.getCoeTrackingNoId() != null) {
				sb.append(" and coe_tracking_no_id = " + outWarehousePackage.getCoeTrackingNoId());
			}
			if (outWarehousePackage.getCreatedTime() != null) {
				sb.append(" and created_time = " + outWarehousePackage.getCreatedTime());
			}
			if (StringUtil.isNotNull(outWarehousePackage.getRemark())) {
				sb.append(" and remark = '" + outWarehousePackage.getRemark() + "' ");
			}
			if (StringUtil.isNotNull(outWarehousePackage.getIsShiped())) {
				sb.append(" and is_shiped = '" + outWarehousePackage.getIsShiped() + "' ");
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
		logger.debug("统计出库记录sql:" + sql);
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	@Override
	public int updateOutWarehousePackageRemark(Long outWarehousePackageId, String remark) {
		String sql = "update w_s_out_warehouse_package set remark ='" + remark + "' where id=" + outWarehousePackageId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateOutWarehousePackageIsShiped(Long outWarehouseRecordId, String isShiped, Long shippedTime) {
		String sql = "update w_s_out_warehouse_package set is_shiped = ?,shipped_time = ? where id= ?";
		return jdbcTemplate.update(sql, isShiped, shippedTime, outWarehouseRecordId);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}