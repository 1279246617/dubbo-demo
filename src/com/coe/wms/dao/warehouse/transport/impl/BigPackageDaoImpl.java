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
import com.coe.wms.dao.warehouse.transport.IBigPackageDao;
import com.coe.wms.model.warehouse.transport.BigPackage;
import com.coe.wms.model.warehouse.transport.BigPackageStatus.BigPackageStatusCode;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("bigPackageDao")
public class BigPackageDaoImpl implements IBigPackageDao {

	Logger logger = Logger.getLogger(BigPackageDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveBigPackage(final BigPackage bigPackage) {
		final String sql = "insert into w_t_big_package (warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,out_warehouse_weight,weight_code,trade_remark,tracking_no,transport_type) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, bigPackage.getWarehouseId());
				ps.setLong(2, bigPackage.getUserIdOfCustomer());

				if (bigPackage.getUserIdOfOperator() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setDouble(3, bigPackage.getUserIdOfOperator());
				}
				ps.setString(4, bigPackage.getShipwayCode());
				ps.setLong(5, bigPackage.getCreatedTime());
				ps.setString(6, bigPackage.getStatus());
				ps.setString(7, bigPackage.getRemark());
				ps.setString(8, bigPackage.getCustomerReferenceNo());
				if (bigPackage.getOutWarehouseWeight() == null) {
					ps.setNull(9, Types.DOUBLE);
				} else {
					ps.setDouble(9, bigPackage.getOutWarehouseWeight());
				}
				ps.setString(10, bigPackage.getWeightCode());
				ps.setString(11, bigPackage.getTradeRemark());
				ps.setString(12, bigPackage.getTrackingNo());
				ps.setString(13, bigPackage.getTransportType());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public BigPackage getBigPackageById(Long bigPackageId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,callback_send_weight_is_success,callback_send_weigh_count,callback_send_status_is_success,callback_send_status_count,out_warehouse_weight,weight_code,trade_remark,tracking_no,callback_send_check_is_success,callback_send_check_count,check_result,transport_type from w_t_big_package where id ="
				+ bigPackageId;
		BigPackage bigPackage = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<BigPackage>(BigPackage.class));
		return bigPackage;
	}

	/**
	 * 
	 * 参数一律使用实体类加Map .
	 */
	@Override
	public List<BigPackage> findBigPackage(BigPackage bigPackage, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,shipway_code,created_time,status,remark,customer_reference_no,callback_send_weight_is_success,callback_send_weigh_count,callback_send_status_is_success,callback_send_status_count,out_warehouse_weight,weight_code,trade_remark,tracking_no,callback_send_check_is_success,callback_send_check_count,check_result,transport_type from w_t_big_package where 1=1 ");
		if (moreParam != null && StringUtil.isNotNull(moreParam.get("nos"))) {
			// 按单号 批量查询 开始---------------
			String noType = moreParam.get("noType");
			String nos = moreParam.get("nos");
			String noArray[] = StringUtil.splitW(nos);
			String in = "";
			for (String no : noArray) {
				in += ("'" + no + "',");
			}
			if (in.endsWith(",")) {
				in = in.substring(0, in.length() - 1);
			}
			if (StringUtil.isEqual(noType, "1")) {
				sb.append(" and customer_reference_no in(" + in + ")");
			}
			if (StringUtil.isEqual(noType, "2")) {
				sb.append(" and tracking_no in(" + in + ")");
			}
			// 按单号 批量查询 结束------------
		} else {
			if (bigPackage != null) {
				if (bigPackage.getId() != null) {
					sb.append(" and id = " + bigPackage.getId());
				}
				if (bigPackage.getUserIdOfCustomer() != null) {
					sb.append(" and user_id_of_customer = " + bigPackage.getUserIdOfCustomer());
				}
				if (bigPackage.getUserIdOfOperator() != null) {
					sb.append(" and user_id_of_operator = " + bigPackage.getUserIdOfOperator());
				}
				if (StringUtil.isNotNull(bigPackage.getShipwayCode())) {
					sb.append(" and shipway_code = '" + bigPackage.getShipwayCode() + "' ");
				}
				if (bigPackage.getCreatedTime() != null) {
					sb.append(" and created_time = " + bigPackage.getCreatedTime());
				}
				if (StringUtil.isNotNull(bigPackage.getStatus())) {
					sb.append(" and status = '" + bigPackage.getStatus() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getRemark())) {
					sb.append(" and remark = '" + bigPackage.getRemark() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendCheckIsSuccess())) {
					sb.append(" and callback_send_check_is_success = '" + bigPackage.getCallbackSendCheckIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendCheckCount() != null) {
					sb.append(" and callback_send_check_count = " + bigPackage.getCallbackSendCheckCount());
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendWeightIsSuccess())) {
					sb.append(" and callback_send_weight_is_success = '" + bigPackage.getCallbackSendWeightIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendWeighCount() != null) {
					sb.append(" and callback_send_weigh_count = " + bigPackage.getCallbackSendWeighCount());
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendStatusIsSuccess())) {
					sb.append(" and callback_send_status_is_success = '" + bigPackage.getCallbackSendStatusIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendStatusCount() != null) {
					sb.append(" and callback_send_status_count = " + bigPackage.getCallbackSendStatusCount());
				}
				if (bigPackage.getOutWarehouseWeight() != null) {
					sb.append(" and out_warehouse_weight = " + bigPackage.getOutWarehouseWeight());
				}
				if (StringUtil.isNotNull(bigPackage.getWeightCode())) {
					sb.append(" and weight_code = '" + bigPackage.getWeightCode() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getTradeRemark())) {
					sb.append(" and trade_remark = '" + bigPackage.getTradeRemark() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getTrackingNo())) {
					sb.append(" and tracking_no = '" + bigPackage.getTrackingNo() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getCheckResult())) {
					sb.append(" and check_result = '" + bigPackage.getCheckResult() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getCustomerReferenceNo())) {
					sb.append(" and customer_reference_no = '" + bigPackage.getCustomerReferenceNo() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getTransportType())) {
					sb.append(" and transport_type = '" + bigPackage.getTransportType() + "' ");
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
		}
		String sql = sb.toString();
		List<BigPackage> bigPackageList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(BigPackage.class));
		return bigPackageList;
	}

	@Override
	public Long countBigPackage(BigPackage bigPackage, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_t_big_package where 1=1");
		if (moreParam != null && StringUtil.isNotNull(moreParam.get("nos"))) {
			// 按单号 批量查询 开始---------------
			String noType = moreParam.get("noType");
			String nos = moreParam.get("nos");
			String noArray[] = StringUtil.splitW(nos);
			String in = "";
			for (String no : noArray) {
				in += ("'" + no + "',");
			}
			if (in.endsWith(",")) {
				in = in.substring(0, in.length() - 1);
			}
			if (StringUtil.isEqual(noType, "1")) {
				sb.append(" and customer_reference_no in(" + in + ")");
			}
			if (StringUtil.isEqual(noType, "2")) {
				sb.append(" and tracking_no in(" + in + ")");
			}
			// 按单号 批量查询 结束------------
		} else {
			if (bigPackage != null) {
				if (bigPackage.getId() != null) {
					sb.append(" and id = " + bigPackage.getId());
				}
				if (bigPackage.getUserIdOfCustomer() != null) {
					sb.append(" and user_id_of_customer = " + bigPackage.getUserIdOfCustomer());
				}
				if (bigPackage.getUserIdOfOperator() != null) {
					sb.append(" and user_id_of_operator = " + bigPackage.getUserIdOfOperator());
				}
				if (StringUtil.isNotNull(bigPackage.getShipwayCode())) {
					sb.append(" and shipway_code = '" + bigPackage.getShipwayCode() + "' ");
				}
				if (bigPackage.getCreatedTime() != null) {
					sb.append(" and created_time = " + bigPackage.getCreatedTime());
				}
				if (StringUtil.isNotNull(bigPackage.getStatus())) {
					sb.append(" and status = '" + bigPackage.getStatus() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getRemark())) {
					sb.append(" and remark = '" + bigPackage.getRemark() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendCheckIsSuccess())) {
					sb.append(" and callback_send_check_is_success = '" + bigPackage.getCallbackSendCheckIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendCheckCount() != null) {
					sb.append(" and callback_send_check_count = " + bigPackage.getCallbackSendCheckCount());
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendWeightIsSuccess())) {
					sb.append(" and callback_send_weight_is_success = '" + bigPackage.getCallbackSendWeightIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendWeighCount() != null) {
					sb.append(" and callback_send_weigh_count = " + bigPackage.getCallbackSendWeighCount());
				}
				if (StringUtil.isNotNull(bigPackage.getCallbackSendStatusIsSuccess())) {
					sb.append(" and callback_send_status_is_success = '" + bigPackage.getCallbackSendStatusIsSuccess() + "' ");
				}
				if (bigPackage.getCallbackSendStatusCount() != null) {
					sb.append(" and callback_send_status_count = " + bigPackage.getCallbackSendStatusCount());
				}
				if (bigPackage.getOutWarehouseWeight() != null) {
					sb.append(" and out_warehouse_weight = " + bigPackage.getOutWarehouseWeight());
				}
				if (bigPackage.getWeightCode() != null) {
					sb.append(" and weight_code = '" + bigPackage.getWeightCode() + "'");
				}
				if (bigPackage.getTradeRemark() != null) {
					sb.append(" and trade_remark = '" + bigPackage.getTradeRemark() + "'");
				}
				if (bigPackage.getTrackingNo() != null) {
					sb.append(" and tracking_no = '" + bigPackage.getTrackingNo() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getCheckResult())) {
					sb.append(" and check_result = '" + bigPackage.getCheckResult() + "'");
				}
				if (StringUtil.isNotNull(bigPackage.getCustomerReferenceNo())) {
					sb.append(" and customer_reference_no = '" + bigPackage.getCustomerReferenceNo() + "' ");
				}
				if (StringUtil.isNotNull(bigPackage.getTransportType())) {
					sb.append(" and transport_type = '" + bigPackage.getTransportType() + "' ");
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
		}
		String sql = sb.toString();
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int updateBigPackageStatus(Long bigPackageId, String newStatus) {
		String sql = "update w_t_big_package set status = '" + newStatus + "' where id = " + bigPackageId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public String getBigPackageStatus(Long bigPackageId) {
		String sql = "select status from w_t_big_package where id = " + bigPackageId;
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	/**
	 * 获取回调未成功的记录id
	 * 
	 * 1,必须有客户id, 2,必须有重量, 3回调未成功 或未回调
	 */
	@Override
	public List<Long> findCallbackSendWeightUnSuccessBigPackageId() {
		String sql = "select id from w_t_big_package where status ='" + BigPackageStatusCode.WSW + "' and (callback_send_weight_is_success = 'N' or  callback_send_weight_is_success is null)";
		List<Long> bigPackageIdList = jdbcTemplate.queryForList(sql, Long.class);
		return bigPackageIdList;
	}

	/**
	 * 获取回调未成功的记录id
	 * 
	 * 1,必须有客户id, 2,必须有重量, 3回调未成功 或未回调
	 */
	@Override
	public List<Long> findCallbackSendStatusUnSuccessBigPackageId() {
		String sql = "select id from w_t_big_package where status ='" + BigPackageStatusCode.SUCCESS + "' and (callback_send_status_is_success = 'N' or  callback_send_status_is_success is null)";
		List<Long> bigPackageIdList = jdbcTemplate.queryForList(sql, Long.class);
		return bigPackageIdList;
	}

	/**
	 * 更新回调顺丰状态
	 * 
	 * @param bigPackage
	 * @return
	 */
	@Override
	public int updateBigPackageCallbackSendWeight(BigPackage bigPackage) {
		String sql = "update w_t_big_package set callback_send_weight_is_success='" + bigPackage.getCallbackSendWeightIsSuccess() + "' ,callback_send_weigh_count = " + bigPackage.getCallbackSendWeighCount() + " , status='" + bigPackage.getStatus()
				+ "' where id=" + bigPackage.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 更新回调顺丰状态
	 * 
	 * @param bigPackage
	 * @return
	 */
	@Override
	public int updateBigPackageCallbackSendStatus(BigPackage bigPackage) {
		String sql = "update w_t_big_package set callback_send_status_is_success='" + bigPackage.getCallbackSendStatusIsSuccess() + "' ,callback_send_status_count = " + bigPackage.getCallbackSendStatusCount() + " , status='" + bigPackage.getStatus()
				+ "' where id=" + bigPackage.getId();
		return jdbcTemplate.update(sql);
	}

	/**
	 * 
	 */
	@Override
	public int updateBigPackageWeight(BigPackage bigPackage) {
		String sql = "update w_t_big_package set out_warehouse_weight=" + bigPackage.getOutWarehouseWeight() + ",weight_code='" + bigPackage.getWeightCode() + "' , status='" + bigPackage.getStatus() + "' where id=" + bigPackage.getId();
		return jdbcTemplate.update(sql);
	}

	@Override
	public List<Long> findCallbackSendCheckUnSuccessBigPackageId() {
		String sql = "select id from w_t_big_package where status ='" + BigPackageStatusCode.WCI + "' and (callback_send_check_is_success = 'N' or  callback_send_check_is_success is null)";
		List<Long> bigPackageIdList = jdbcTemplate.queryForList(sql, Long.class);
		return bigPackageIdList;
	}

	@Override
	public int updateBigPackageCallbackSendCheck(BigPackage bigPackage) {
		String sql = "update w_t_big_package set callback_send_check_is_success='" + bigPackage.getCallbackSendCheckIsSuccess() + "' ,callback_send_check_count = " + bigPackage.getCallbackSendCheckCount() + " , status='" + bigPackage.getStatus()
				+ "' where id=" + bigPackage.getId();
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateBigPackageCheckResult(Long bigPackageId, String checkResult) {
		String sql = "update w_t_big_package set check_result = '" + checkResult + "' where id = " + bigPackageId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public String getCustomerReferenceNoById(Long bigPackageId) {
		String sql = "select customer_reference_no from w_t_big_package where id = " + bigPackageId;
		return jdbcTemplate.queryForObject(sql, String.class);
	}
}