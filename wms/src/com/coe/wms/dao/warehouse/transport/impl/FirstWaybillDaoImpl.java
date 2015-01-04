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
import com.coe.wms.dao.warehouse.transport.IFirstWaybillDao;
import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus.FirstWaybillStatusCode;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("firstWaybillDao")
public class FirstWaybillDaoImpl implements IFirstWaybillDao {

	Logger logger = Logger.getLogger(FirstWaybillDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveFirstWaybill(final FirstWaybill firstWaybill) {
		final String sql = "insert into w_t_first_waybill (warehouse_id,user_id_of_customer,user_id_of_operator,carrier_code,tracking_no,created_time,remark,callback_is_success,callback_count,order_id,status,received_time,po_no,transport_type,order_package_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, firstWaybill.getWarehouseId());
				ps.setLong(2, firstWaybill.getUserIdOfCustomer());
				if (firstWaybill.getUserIdOfOperator() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setLong(3, firstWaybill.getUserIdOfOperator());
				}
				ps.setString(4, firstWaybill.getCarrierCode());
				ps.setString(5, firstWaybill.getTrackingNo());
				ps.setLong(6, firstWaybill.getCreatedTime());
				ps.setString(7, firstWaybill.getRemark());
				ps.setString(8, firstWaybill.getCallbackIsSuccess());
				ps.setInt(9, firstWaybill.getCallbackCount() != null ? firstWaybill.getCallbackCount() : 0);
				if (firstWaybill.getOrderId() == null) {
					ps.setNull(10, Types.BIGINT);
				} else {
					ps.setLong(10, firstWaybill.getOrderId());
				}
				ps.setString(11, firstWaybill.getStatus());
				if (firstWaybill.getReceivedTime() == null) {
					ps.setNull(12, Types.BIGINT);
				} else {
					ps.setLong(12, firstWaybill.getReceivedTime());
				}
				ps.setString(13, firstWaybill.getPoNo());
				ps.setString(14, firstWaybill.getTransportType());

				if (firstWaybill.getOrderPackageId() == null) {
					ps.setNull(15, Types.BIGINT);
				} else {
					ps.setLong(15, firstWaybill.getOrderPackageId());
				}
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public FirstWaybill getFirstWaybillById(Long FirstWaybillId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,carrier_code,tracking_no,created_time,remark,callback_is_success,callback_count,order_id,status,received_time,po_no,transport_type,seat_code,order_package_id from w_t_first_waybill where id= "
				+ FirstWaybillId;
		FirstWaybill firstWaybill = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<FirstWaybill>(FirstWaybill.class));
		return firstWaybill;
	}

	/**
	 * 查询入库记录
	 */
	@Override
	public List<FirstWaybill> findFirstWaybill(FirstWaybill firstWaybill, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,carrier_code,tracking_no,created_time,remark,callback_is_success,callback_count,order_id,status,received_time,po_no,transport_type,seat_code,order_package_id from w_t_first_waybill where 1=1 ");
		if (firstWaybill != null) {
			if (StringUtil.isNotNull(firstWaybill.getTrackingNo())) {
				sb.append(" and tracking_no = '" + firstWaybill.getTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(firstWaybill.getCarrierCode())) {
				sb.append(" and carrier_code = '" + firstWaybill.getCarrierCode() + "' ");
			}
			if (StringUtil.isNotNull(firstWaybill.getRemark())) {
				sb.append(" and remark = '" + firstWaybill.getRemark() + "' ");
			}
			if (firstWaybill.getCreatedTime() != null) {
				sb.append(" and created_time = " + firstWaybill.getCreatedTime());
			}
			if (firstWaybill.getId() != null) {
				sb.append(" and id = " + firstWaybill.getId());
			}
			if (firstWaybill.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + firstWaybill.getWarehouseId());
			}
			if (firstWaybill.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + firstWaybill.getUserIdOfCustomer());
			}
			if (firstWaybill.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + firstWaybill.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(firstWaybill.getCallbackIsSuccess())) {
				sb.append(" and callback_is_success = '" + firstWaybill.getCallbackIsSuccess() + "' ");
			}
			if (firstWaybill.getCallbackCount() != null) {
				sb.append(" and callback_count = " + firstWaybill.getCallbackCount());
			}
			if (firstWaybill.getOrderId() != null) {
				sb.append(" and order_id = " + firstWaybill.getOrderId());
			}
			if (firstWaybill.getOrderPackageId() != null) {
				sb.append(" and order_package_id = " + firstWaybill.getOrderPackageId());
			}
			if (StringUtil.isNotNull(firstWaybill.getStatus())) {
				sb.append(" and status = '" + firstWaybill.getStatus() + "' ");
			}
			if (StringUtil.isNotNull(firstWaybill.getPoNo())) {
				sb.append(" and po_no = '" + firstWaybill.getPoNo() + "' ");
			}
			if (StringUtil.isNotNull(firstWaybill.getTransportType())) {
				sb.append(" and transport_type = '" + firstWaybill.getTransportType() + "' ");
			}
			if (StringUtil.isNotNull(firstWaybill.getSeatCode())) {
				sb.append(" and seat_code = '" + firstWaybill.getSeatCode() + "' ");
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
			if (moreParam.get("receivedTimeStart") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("receivedTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and received_time >= " + date.getTime());
				}
			}
			if (moreParam.get("receivedTimeEnd") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("receivedTimeEnd"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and received_time <= " + date.getTime());
				}
			}
			// isReceived = Y ,表示已收货的记录, 状态不能等于待仓库收货
			if (StringUtil.isEqual(moreParam.get("isReceived"), Constant.Y)) {
				sb.append(" and status != '" + FirstWaybillStatusCode.WWR + "'");
			}
		}
		if (page != null) {
			// 分页sql
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		List<FirstWaybill> firstWaybillList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(FirstWaybill.class));
		return firstWaybillList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Long countFirstWaybill(FirstWaybill firstWaybill, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*)  from w_t_first_waybill where 1=1 ");
		if (firstWaybill != null) {
			if (StringUtil.isNotNull(firstWaybill.getTrackingNo())) {
				sb.append(" and tracking_no = '" + firstWaybill.getTrackingNo() + "' ");
			}
			if (StringUtil.isNotNull(firstWaybill.getCarrierCode())) {
				sb.append(" and carrier_code = '" + firstWaybill.getCarrierCode() + "' ");
			}
			if (StringUtil.isNotNull(firstWaybill.getRemark())) {
				sb.append(" and remark = '" + firstWaybill.getRemark() + "' ");
			}
			if (firstWaybill.getCreatedTime() != null) {
				sb.append(" and created_time = " + firstWaybill.getCreatedTime());
			}
			if (firstWaybill.getId() != null) {
				sb.append(" and id = " + firstWaybill.getId());
			}
			if (firstWaybill.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + firstWaybill.getWarehouseId());
			}
			if (firstWaybill.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + firstWaybill.getUserIdOfCustomer());
			}
			if (firstWaybill.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + firstWaybill.getUserIdOfOperator());
			}
			if (StringUtil.isNotNull(firstWaybill.getCallbackIsSuccess())) {
				sb.append(" and callback_is_success = '" + firstWaybill.getCallbackIsSuccess() + "' ");
			}
			if (firstWaybill.getCallbackCount() != null) {
				sb.append(" and callback_count = " + firstWaybill.getCallbackCount());
			}
			if (firstWaybill.getOrderId() != null) {
				sb.append(" and order_id = " + firstWaybill.getOrderId());
			}
			if (firstWaybill.getOrderPackageId() != null) {
				sb.append(" and order_package_id = " + firstWaybill.getOrderPackageId());
			}
			if (StringUtil.isNotNull(firstWaybill.getStatus())) {
				sb.append(" and status = '" + firstWaybill.getStatus() + "' ");
			}
			if (StringUtil.isNotNull(firstWaybill.getPoNo())) {
				sb.append(" and po_no = '" + firstWaybill.getPoNo() + "' ");
			}
			if (StringUtil.isNotNull(firstWaybill.getTransportType())) {
				sb.append(" and transport_type = '" + firstWaybill.getTransportType() + "' ");
			}
			if (StringUtil.isNotNull(firstWaybill.getSeatCode())) {
				sb.append(" and seat_code = '" + firstWaybill.getSeatCode() + "' ");
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
			if (moreParam.get("receivedTimeStart") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("receivedTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and received_time >= " + date.getTime());
				}
			}
			if (moreParam.get("receivedTimeEnd") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("receivedTimeEnd"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and received_time <= " + date.getTime());
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
	public int updateFirstWaybillCallback(FirstWaybill firstWaybill) {
		String sql = "update w_t_first_waybill set callback_is_success='" + firstWaybill.getCallbackIsSuccess() + "' ,callback_count = " + firstWaybill.getCallbackCount() + " ,status = '" + firstWaybill.getStatus() + "' where id="
				+ firstWaybill.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 获取回调未成功的记录id
	 */
	@Override
	public List<Long> findCallbackUnSuccessFirstWaybillId() {
		String sql = "select id from w_t_first_waybill where status ='" + FirstWaybillStatusCode.WSR + "' and (callback_is_success = 'N' or  callback_is_success is null)";
		List<Long> recordIdList = jdbcTemplate.queryForList(sql, Long.class);
		return recordIdList;
	}

	@Override
	public int updateFirstWaybillStatus(FirstWaybill firstWaybill) {
		String sql = "update w_t_first_waybill set status='" + firstWaybill.getStatus() + "' where id=" + firstWaybill.getId();
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateFirstWaybillStatus(Long OrderId, String newStatus) {
		String sql = "update w_t_first_waybill set status='" + newStatus + "' where order_id=" + OrderId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int deleteFirstWaybillById(Long FirstWaybillId) {
		String sql = "delete from w_t_first_waybill where id=" + FirstWaybillId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int receivedFirstWaybill(FirstWaybill firstWaybill) {
		String sql = "update w_t_first_waybill set status=? ,received_time=?,seat_code = ? where id=" + firstWaybill.getId();
		return jdbcTemplate.update(sql, firstWaybill.getStatus(), firstWaybill.getReceivedTime(), firstWaybill.getSeatCode());
	}

	@Override
	public int updateFirstWaybillSeatCode(FirstWaybill firstWaybill) {
		String sql = "update w_t_first_waybill set seat_code='" + firstWaybill.getSeatCode() + "' where id=" + firstWaybill.getId();
		return jdbcTemplate.update(sql);
	}

	@Override
	public List<String> findFirstWaybillTrackingNos(Long OrderId) {
		String sql = "select tracking_no from w_t_first_waybill where order_id= " + OrderId;
		return jdbcTemplate.queryForList(sql, String.class);
	}

}