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
import com.coe.wms.dao.warehouse.transport.ILittlePackageDao;
import com.coe.wms.model.warehouse.transport.LittlePackage;
import com.coe.wms.model.warehouse.transport.LittlePackageStatus.LittlePackageStatusCode;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("littlePackageDao")
public class LittlePackageDaoImpl implements ILittlePackageDao {

	Logger logger = Logger.getLogger(LittlePackageDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveLittlePackage(final LittlePackage littlePackage) {
		final String sql = "insert into w_t_little_package (warehouse_id,user_id_of_customer,user_id_of_operator,carrier_code,tracking_no,created_time,remark,callback_is_success,callback_count,big_package_id,status,received_time,po_no) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, littlePackage.getWarehouseId());
				ps.setLong(2, littlePackage.getUserIdOfCustomer());
				if (littlePackage.getUserIdOfOperator() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setLong(3, littlePackage.getUserIdOfOperator());
				}
				ps.setString(4, littlePackage.getCarrierCode());
				ps.setString(5, littlePackage.getTrackingNo());
				ps.setLong(6, littlePackage.getCreatedTime());
				ps.setString(7, littlePackage.getRemark());
				ps.setString(8, littlePackage.getCallbackIsSuccess());
				ps.setInt(9, littlePackage.getCallbackCount() != null ? littlePackage.getCallbackCount() : 0);
				ps.setLong(10, littlePackage.getBigPackageId());
				ps.setString(11, littlePackage.getStatus());
				if (littlePackage.getReceivedTime() == null) {
					ps.setNull(12, Types.BIGINT);
				} else {
					ps.setLong(12, littlePackage.getReceivedTime());
				}
				ps.setString(13, littlePackage.getPoNo());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public LittlePackage getLittlePackageById(Long LittlePackageId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,carrier_code,tracking_no,created_time,remark,callback_is_success,callback_count,big_package_id,status,received_time,po_no from w_t_little_package where id= "
				+ LittlePackageId;
		LittlePackage littlePackage = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<LittlePackage>(LittlePackage.class));
		return littlePackage;
	}

	/**
	 * 查询入库记录
	 */
	@Override
	public List<LittlePackage> findLittlePackage(LittlePackage LittlePackage, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,carrier_code,tracking_no,created_time,remark,callback_is_success,callback_count,big_package_id,status,received_time,po_no from w_t_little_package where 1=1 ");
		if (LittlePackage != null) {
			if (StringUtil.isNotNull(LittlePackage.getTrackingNo())) {
				sb.append(" and tracking_no = '" + LittlePackage.getTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(LittlePackage.getCarrierCode())) {
				sb.append(" and carrier_code = '" + LittlePackage.getCarrierCode() + "' ");
			}
			if (StringUtil.isNotNull(LittlePackage.getRemark())) {
				sb.append(" and remark = '" + LittlePackage.getRemark() + "' ");
			}
			if (LittlePackage.getCreatedTime() != null) {
				sb.append(" and created_time = " + LittlePackage.getCreatedTime());
			}
			if (LittlePackage.getId() != null) {
				sb.append(" and id = " + LittlePackage.getId());
			}
			if (LittlePackage.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + LittlePackage.getWarehouseId());
			}
			if (LittlePackage.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + LittlePackage.getUserIdOfCustomer());
			}
			if (LittlePackage.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + LittlePackage.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(LittlePackage.getCallbackIsSuccess())) {
				sb.append(" and callback_is_success = '" + LittlePackage.getCallbackIsSuccess() + "' ");
			}
			if (LittlePackage.getCallbackCount() != null) {
				sb.append(" and callback_count = " + LittlePackage.getCallbackCount());
			}
			if (LittlePackage.getBigPackageId() != null) {
				sb.append(" and big_package_id = " + LittlePackage.getBigPackageId());
			}
			if (StringUtil.isNotNull(LittlePackage.getStatus())) {
				sb.append(" and status = '" + LittlePackage.getStatus() + "' ");
			}
			if (StringUtil.isNotNull(LittlePackage.getPoNo())) {
				sb.append(" and po_no = '" + LittlePackage.getPoNo() + "' ");
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
			// isReceived = Y ,表示已收货的记录, 状态不能等于待仓库收货
			if (StringUtil.isEqual(moreParam.get("isReceived"), Constant.Y)) {
				sb.append(" and status != '" + LittlePackageStatusCode.WWR + "'");
			}
		}
		if (page != null) {
			// 分页sql
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		List<LittlePackage> LittlePackageList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(LittlePackage.class));
		return LittlePackageList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Long countLittlePackage(LittlePackage LittlePackage, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*)  from w_t_little_package where 1=1 ");
		if (LittlePackage != null) {
			if (StringUtil.isNotNull(LittlePackage.getTrackingNo())) {
				sb.append(" and tracking_no = '" + LittlePackage.getTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(LittlePackage.getCarrierCode())) {
				sb.append(" and carrier_code = '" + LittlePackage.getCarrierCode() + "' ");
			}
			if (StringUtil.isNotNull(LittlePackage.getRemark())) {
				sb.append(" and remark = '" + LittlePackage.getRemark() + "' ");
			}
			if (LittlePackage.getCreatedTime() != null) {
				sb.append(" and created_time = " + LittlePackage.getCreatedTime());
			}
			if (LittlePackage.getId() != null) {
				sb.append(" and id = " + LittlePackage.getId());
			}
			if (LittlePackage.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + LittlePackage.getWarehouseId());
			}
			if (LittlePackage.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + LittlePackage.getUserIdOfCustomer());
			}
			if (LittlePackage.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + LittlePackage.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(LittlePackage.getCallbackIsSuccess())) {
				sb.append(" and callback_is_success = '" + LittlePackage.getCallbackIsSuccess() + "' ");
			}
			if (LittlePackage.getCallbackCount() != null) {
				sb.append(" and callback_count = " + LittlePackage.getCallbackCount());
			}
			if (LittlePackage.getBigPackageId() != null) {
				sb.append(" and big_package_id = " + LittlePackage.getBigPackageId());
			}
			if (StringUtil.isNotNull(LittlePackage.getStatus())) {
				sb.append(" and status = '" + LittlePackage.getStatus() + "' ");
			}
			if (StringUtil.isNotNull(LittlePackage.getPoNo())) {
				sb.append(" and po_no = '" + LittlePackage.getPoNo() + "' ");
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

	/**
	 * 更新回调顺丰状态
	 */
	@Override
	public int updateLittlePackageCallback(LittlePackage LittlePackage) {
		String sql = "update w_t_little_package set callback_is_success='" + LittlePackage.getCallbackIsSuccess() + "' ,callback_count = " + LittlePackage.getCallbackCount() + " ,status = '" + LittlePackage.getStatus() + "' where id="
				+ LittlePackage.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 获取回调未成功的记录id
	 */
	@Override
	public List<Long> findCallbackUnSuccessPackageId() {
		String sql = "select id from w_t_little_package where status ='" + LittlePackageStatusCode.WSR + "' and (callback_is_success = 'N' or  callback_is_success is null)";
		List<Long> recordIdList = jdbcTemplate.queryForList(sql, Long.class);
		return recordIdList;
	}

	@Override
	public int updateLittlePackageStatus(LittlePackage LittlePackage) {
		String sql = "update w_t_little_package set status='" + LittlePackage.getStatus() + "' where id=" + LittlePackage.getId();
		return jdbcTemplate.update(sql);
	}

	@Override
	public int deleteLittlePackageById(Long LittlePackageId) {
		String sql = "delete from w_t_little_package where id=" + LittlePackageId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateLittlePackageStatusAndReceivedTime(LittlePackage LittlePackage) {
		String sql = "update w_t_little_package set status='" + LittlePackage.getStatus() + "', received_time=" + LittlePackage.getReceivedTime() + " where id=" + LittlePackage.getId();
		return jdbcTemplate.update(sql);
	}

}