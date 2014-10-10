package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("inWarehouseRecordDao")
public class InWarehouseRecordDaoImpl implements IInWarehouseRecordDao {

	Logger logger = Logger.getLogger(InWarehouseRecordDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveInWarehouseRecord(final InWarehouseRecord record) {
		final String sql = "insert into w_s_in_warehouse_record (warehouse_id,user_id_of_customer,user_id_of_operator,batch_no,package_no,package_tracking_no,is_un_know_customer,created_time,remark,callback_is_success,callback_count,in_warehouse_order_id) values (?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, record.getWarehouseId() != null ? record.getWarehouseId() : 0);
				ps.setLong(2, record.getUserIdOfCustomer());
				ps.setLong(3, record.getUserIdOfOperator());
				ps.setString(4, record.getBatchNo());
				ps.setString(5, record.getPackageNo());
				ps.setString(6, record.getPackageTrackingNo());
				ps.setString(7, record.getIsUnKnowCustomer());
				ps.setLong(8, record.getCreatedTime());
				ps.setString(9, record.getRemark());
				ps.setString(10, record.getCallbackIsSuccess());
				ps.setInt(11, record.getCallbackCount() != null ? record.getCallbackCount() : 0);
				ps.setLong(12, record.getInWarehouseOrderId() != null ? record.getInWarehouseOrderId() : 0);
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public InWarehouseRecord getInWarehouseRecordById(Long InWarehouseRecordId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,batch_no,package_no,package_tracking_no,is_un_know_customer,created_time,remark,callback_is_success,callback_count,in_warehouse_order_id from w_s_in_warehouse_record where id= "+ InWarehouseRecordId;
		InWarehouseRecord record = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<InWarehouseRecord>(InWarehouseRecord.class));
		return record;
	}

	/**
	 * 查询入库记录
	 */
	@Override
	public List<InWarehouseRecord> findInWarehouseRecord(InWarehouseRecord InWarehouseRecord, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,batch_no,package_no,package_tracking_no,is_un_know_customer,created_time,remark,callback_is_success,callback_count,in_warehouse_order_id from w_s_in_warehouse_record where 1=1 ");
		if (InWarehouseRecord != null) {
			if (StringUtil.isNotNull(InWarehouseRecord.getPackageNo())) {
				sb.append(" and package_no = '" + InWarehouseRecord.getPackageNo() + "' ");
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getPackageTrackingNo())) {
				sb.append(" and package_tracking_no = '" + InWarehouseRecord.getPackageTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getBatchNo())) {
				sb.append(" and batch_no = '" + InWarehouseRecord.getBatchNo() + "' ");
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getIsUnKnowCustomer())) {
				sb.append(" and is_un_know_customer = '" + InWarehouseRecord.getIsUnKnowCustomer() + "' ");
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getRemark())) {
				sb.append(" and remark = '" + InWarehouseRecord.getRemark() + "' ");
			}
			if (InWarehouseRecord.getCreatedTime() != null) {
				sb.append(" and created_time = " + InWarehouseRecord.getCreatedTime());
			}
			if (InWarehouseRecord.getId() != null) {
				sb.append(" and id = " + InWarehouseRecord.getId());
			}
			if (InWarehouseRecord.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + InWarehouseRecord.getWarehouseId());
			}
			if (InWarehouseRecord.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + InWarehouseRecord.getUserIdOfCustomer());
			}
			if (InWarehouseRecord.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + InWarehouseRecord.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getCallbackIsSuccess())) {
				sb.append(" and callback_is_success = '" + InWarehouseRecord.getCallbackIsSuccess() + "' ");
			}
			if (InWarehouseRecord.getCallbackCount() != null) {
				sb.append(" and callback_count = " + InWarehouseRecord.getCallbackCount());
			}
			if (InWarehouseRecord.getInWarehouseOrderId() != null) {
				sb.append(" and in_warehouse_order_id = " + InWarehouseRecord.getInWarehouseOrderId());
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
		logger.info("查询入库记录sql:" + sql);
		List<InWarehouseRecord> InWarehouseRecordList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(InWarehouseRecord.class));
		return InWarehouseRecordList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Long countInWarehouseRecord(InWarehouseRecord InWarehouseRecord, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*)  from w_s_in_warehouse_record where 1=1 ");
		if (InWarehouseRecord != null) {
			if (StringUtil.isNotNull(InWarehouseRecord.getPackageNo())) {
				sb.append(" and package_no = '" + InWarehouseRecord.getPackageNo() + "' ");
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getPackageTrackingNo())) {
				sb.append(" and package_tracking_no = '" + InWarehouseRecord.getPackageTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getBatchNo())) {
				sb.append(" and batch_no = '" + InWarehouseRecord.getBatchNo() + "' ");
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getIsUnKnowCustomer())) {
				sb.append(" and is_un_know_customer = '" + InWarehouseRecord.getIsUnKnowCustomer() + "' ");
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getRemark())) {
				sb.append(" and remark = '" + InWarehouseRecord.getRemark() + "' ");
			}
			if (InWarehouseRecord.getCreatedTime() != null) {
				sb.append(" and created_time = " + InWarehouseRecord.getCreatedTime());
			}
			if (InWarehouseRecord.getId() != null) {
				sb.append(" and id = " + InWarehouseRecord.getId());
			}
			if (InWarehouseRecord.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + InWarehouseRecord.getWarehouseId());
			}
			if (InWarehouseRecord.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + InWarehouseRecord.getUserIdOfCustomer());
			}
			if (InWarehouseRecord.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + InWarehouseRecord.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getCallbackIsSuccess())) {
				sb.append(" and callback_is_success = '" + InWarehouseRecord.getCallbackIsSuccess() + "' ");
			}
			if (InWarehouseRecord.getCallbackCount() != null) {
				sb.append(" and callback_count = " + InWarehouseRecord.getCallbackCount());
			}
			if (InWarehouseRecord.getInWarehouseOrderId() != null) {
				sb.append(" and in_warehouse_order_id = " + InWarehouseRecord.getInWarehouseOrderId());
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
		logger.info("统计入库记录sql:" + sql);
		return jdbcTemplate.queryForLong(sql);
	}

	/**
	 * 更新回调顺丰状态
	 */
	@Override
	public int updateInWarehouseRecordCallback(InWarehouseRecord InWarehouseRecord) {
		String sql = "update w_s_in_warehouse_record set callback_is_success='" + InWarehouseRecord.getCallbackIsSuccess()
				+ "' ,callback_count = " + InWarehouseRecord.getCallbackCount() + " where id=" + InWarehouseRecord.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 获取回调未成功的记录id
	 */
	@Override
	public List<Long> findCallbackUnSuccessRecordId() {
		String sql = "select id from w_s_in_warehouse_record where user_id_of_customer is not null and (callback_is_success = 'N' or  callback_is_success is null)";
		List<Long> recordIdList = jdbcTemplate.queryForList(sql, Long.class);
		return recordIdList;
	}
}