package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
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
		final String sql = "insert into w_s_in_warehouse_record (ware_house_id,user_id_of_customer,user_id_of_operator,batch_no,package_no,package_tracking_no,is_un_know_customer,created_time,remark) values (?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, record.getWareHouseId() != null ? record.getWareHouseId() : 0);
				ps.setLong(2, record.getUserIdOfCustomer());
				ps.setLong(3, record.getUserIdOfOperator());
				ps.setString(4, record.getBatchNo());
				ps.setString(5, record.getPackageNo());
				ps.setString(6, record.getPackageTrackingNo());
				ps.setString(7, record.getIsUnKnowCustomer());
				ps.setLong(8, record.getCreatedTime());
				ps.setString(9, record.getRemark());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public InWarehouseRecord getInWarehouseRecordById(Long InWarehouseRecordId) {

		return null;
	}

	/**
	 * 查询入库记录
	 */
	@Override
	public List<InWarehouseRecord> findInWarehouseRecord(InWarehouseRecord InWarehouseRecord,
			Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,ware_house_id,user_id,batch_no,package_no,package_tracking_no,is_un_know_customer,created_time,remark from w_s_in_warehouse_record where 1=1 ");
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
			if (InWarehouseRecord.getWareHouseId() != null) {
				sb.append(" and ware_house_id = " + InWarehouseRecord.getWareHouseId());
			}
			if (InWarehouseRecord.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + InWarehouseRecord.getUserIdOfCustomer());
			}
			if (InWarehouseRecord.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + InWarehouseRecord.getUserIdOfOperator());
			}
		}
		if (moreParam != null) {
			if (moreParam.get("createdTimeStart") != null) {
				sb.append(" and created_time >= " + Long.valueOf(moreParam.get("createdTimeStart")));
			}
			if (moreParam.get("createdTimeEnd") != null) {
				sb.append(" and created_time <= " + Long.valueOf(moreParam.get("createdTimeEnd")));
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

}