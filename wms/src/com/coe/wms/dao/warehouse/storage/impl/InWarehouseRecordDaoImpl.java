package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordStatus.InWarehouseRecordStatusCode;
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
		final String sql = "insert into w_s_in_warehouse_record (warehouse_id,user_id_of_customer,user_id_of_operator,batch_no,tracking_no,created_time,remark,callback_is_success,callback_count,in_warehouse_order_id,status) values (?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, record.getWarehouseId());
				ps.setLong(2, record.getUserIdOfCustomer());
				ps.setLong(3, record.getUserIdOfOperator());
				ps.setString(4, record.getBatchNo());
				ps.setString(5, record.getTrackingNo());
				ps.setLong(6, record.getCreatedTime());
				ps.setString(7, record.getRemark());
				ps.setString(8, record.getCallbackIsSuccess());
				ps.setInt(9, record.getCallbackCount() != null ? record.getCallbackCount() : 0);
				ps.setLong(10, record.getInWarehouseOrderId());
				ps.setString(11, record.getStatus());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public InWarehouseRecord getInWarehouseRecordById(Long InWarehouseRecordId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,batch_no,tracking_no,created_time,remark,callback_is_success,callback_count,in_warehouse_order_id,status from w_s_in_warehouse_record where id= "
				+ InWarehouseRecordId;
		InWarehouseRecord record = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<InWarehouseRecord>(InWarehouseRecord.class));
		return record;
	}

	/**
	 * 查询入库记录
	 */
	@Override
	public List<InWarehouseRecord> findInWarehouseRecord(InWarehouseRecord InWarehouseRecord, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,batch_no,tracking_no,created_time,remark,callback_is_success,callback_count,in_warehouse_order_id,status from w_s_in_warehouse_record where 1=1 ");
		if (InWarehouseRecord != null) {
			if (StringUtil.isNotNull(InWarehouseRecord.getTrackingNo())) {
				sb.append(" and tracking_no = '" + InWarehouseRecord.getTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getBatchNo())) {
				sb.append(" and batch_no = '" + InWarehouseRecord.getBatchNo() + "' ");
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
			if (StringUtil.isNotNull(InWarehouseRecord.getStatus())) {
				sb.append(" and status = '" + InWarehouseRecord.getStatus() + "' ");
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
		logger.debug("查询入库记录sql:" + sql);
		List<InWarehouseRecord> InWarehouseRecordList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(InWarehouseRecord.class));
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
			if (StringUtil.isNotNull(InWarehouseRecord.getTrackingNo())) {
				sb.append(" and tracking_no = '" + InWarehouseRecord.getTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(InWarehouseRecord.getBatchNo())) {
				sb.append(" and batch_no = '" + InWarehouseRecord.getBatchNo() + "' ");
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
			if (StringUtil.isNotNull(InWarehouseRecord.getStatus())) {
				sb.append(" and status = '" + InWarehouseRecord.getStatus() + "' ");
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
		logger.debug("统计入库记录sql:" + sql);
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	/**
	 * 更新回调顺丰状态
	 */
	@Override
	public int updateInWarehouseRecordCallback(InWarehouseRecord InWarehouseRecord) {
		String sql = "update w_s_in_warehouse_record set callback_is_success='" + InWarehouseRecord.getCallbackIsSuccess() + "' ,callback_count = " + InWarehouseRecord.getCallbackCount() + " where id=" + InWarehouseRecord.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 获取回调未成功的记录id
	 */
	@Override
	public List<Long> findCallbackUnSuccessRecordId() {
		String sql = "select id from w_s_in_warehouse_record where user_id_of_customer is not null and (callback_is_success = 'N' or  callback_is_success is null) and status !='NEW' ";
		List<Long> recordIdList = jdbcTemplate.queryForList(sql, Long.class);
		return recordIdList;
	}

	@Override
	public Long getInWarehouseOrderIdByRecordId(Long InWarehouseRecordId) {
		String sql = "select in_warehouse_order_id from w_s_in_warehouse_record where id= " + InWarehouseRecordId;
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	@Override
	public int updateInWarehouseRecordStatus(InWarehouseRecord InWarehouseRecord) {
		String sql = "update w_s_in_warehouse_record set status='" + InWarehouseRecord.getStatus() + "' where id=" + InWarehouseRecord.getId();
		return jdbcTemplate.update(sql);
	}

	@Override
	public List<Long> findUnCompleteInWarehouseRecordId() {
		String sql = "select id from w_s_in_warehouse_record where status is null or status !='" + InWarehouseRecordStatusCode.COMPLETE + "'";
		List<Long> orderIdList = jdbcTemplate.queryForList(sql, Long.class);
		return orderIdList;
	}

	@Override
	public int deleteInWarehouseRecordById(Long InWarehouseRecordId) {
		String sql = "delete from w_s_in_warehouse_record where id=" + InWarehouseRecordId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateInWarehouseRecordRemark(Long inWarehouseRecordId, String remark) {
		String sql = "update w_s_in_warehouse_record set remark ='" + remark + "' where id=" + inWarehouseRecordId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public List<Map<String, Object>> findInWarehouseRecordOnShelf(Long userIdOfCustomer, Long warehouseId, String trackingNo, String batchNo, String sku, String receivedTimeStart, String receivedTimeEnd, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct r.warehouse_id as warehouseId,r.in_warehouse_order_id,r.user_id_of_customer as userIdOfCustomer ");
		sb.append(" ,r.batch_no,r.tracking_no as trackingNo,i.sku,i.sku_no as skuNo,i.quantity as receivedQuantity,i.remark,i.created_time as receivedTime ");
		sb.append(" ,i.user_id_of_operator as userIdOfOperator");
		sb.append(" ,i.in_warehouse_record_id as inWarehouseRecordId");
		sb.append(" from w_s_in_warehouse_record_item i left join w_s_in_warehouse_record r on i.in_warehouse_record_id=r.id ");
		sb.append("  where 1=1");
		if (userIdOfCustomer != null) {
			sb.append(" and r.user_id_of_customer = " + userIdOfCustomer);
		}
		if (StringUtil.isNotNull(trackingNo)) {
			sb.append(" and r.tracking_no = '" + trackingNo + "'");
		}
		if (StringUtil.isNotNull(batchNo)) {
			sb.append(" and r.batch_no = '" + batchNo + "'");
		}
		if (warehouseId != null) {
			sb.append(" and r.warehouse_id = " + warehouseId);
		}
		if (StringUtil.isNotNull(sku)) {
			sb.append(" and i.sku = '" + sku + "'");
		}
		if (StringUtil.isNotNull(receivedTimeStart)) {
			Date date = DateUtil.stringConvertDate(receivedTimeStart, DateUtil.yyyy_MM_ddHHmmss);
			if (date != null) {
				sb.append(" and i.created_time >= " + date.getTime());
			}
		}
		if (StringUtil.isNotNull(receivedTimeEnd)) {
			Date date = DateUtil.stringConvertDate(receivedTimeEnd, DateUtil.yyyy_MM_ddHHmmss);
			if (date != null) {
				sb.append(" and i.created_time <= " + date.getTime());
			}
		}
		if (page != null) {
			sb.append(page.generatePageSqlOnTable("i"));
		}
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sb.toString());
		return mapList;
	}

	@Override
	public Long countInWarehouseRecordOnShelf(Long userIdOfCustomer, Long warehouseId, String trackingNo, String batchNo, String sku, String receivedTimeStart, String receivedTimeEnd) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) ");
		sb.append(" from w_s_in_warehouse_record_item i left join w_s_in_warehouse_record r on i.in_warehouse_record_id=r.id ");
		sb.append(" where 1=1");
		if (userIdOfCustomer != null) {
			sb.append(" and r.user_id_of_customer = " + userIdOfCustomer);
		}
		if (StringUtil.isNotNull(trackingNo)) {
			sb.append(" and r.tracking_no = '" + trackingNo + "'");
		}
		if (StringUtil.isNotNull(batchNo)) {
			sb.append(" and r.batch_no = '" + batchNo + "'");
		}
		if (warehouseId != null) {
			sb.append(" and r.warehouse_id = " + warehouseId);
		}
		if (StringUtil.isNotNull(sku)) {
			sb.append(" and i.sku = '" + sku + "'");
		}
		if (StringUtil.isNotNull(receivedTimeStart)) {
			Date date = DateUtil.stringConvertDate(receivedTimeStart, DateUtil.yyyy_MM_ddHHmmss);
			if (date != null) {
				sb.append(" and i.created_time >= " + date.getTime());
			}
		}
		if (StringUtil.isNotNull(receivedTimeEnd)) {
			Date date = DateUtil.stringConvertDate(receivedTimeEnd, DateUtil.yyyy_MM_ddHHmmss);
			if (date != null) {
				sb.append(" and i.created_time <= " + date.getTime());
			}
		}
		Long count = jdbcTemplate.queryForObject(sb.toString(), Long.class);
		if (count == null) {
			return 0l;
		}
		return count;
	}

}