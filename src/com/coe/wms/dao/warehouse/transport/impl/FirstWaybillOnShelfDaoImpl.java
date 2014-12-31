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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.warehouse.transport.IFirstWaybillOnShelfDao;
import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

@Repository("firstWaybillOnShelfDao")
public class FirstWaybillOnShelfDaoImpl implements IFirstWaybillOnShelfDao {

	Logger logger = Logger.getLogger(FirstWaybillOnShelfDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public long saveFirstWaybillOnShelf(final FirstWaybillOnShelf FirstWaybillOnShelf) {
		final String sql = "insert into w_t_first_waybill_on_shelf ( warehouse_id,first_waybill_id,order_id,user_id_of_customer,user_id_of_operator,seat_code,tracking_no,created_time,last_update_time,status) values (?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, FirstWaybillOnShelf.getWarehouseId());
				ps.setLong(2, FirstWaybillOnShelf.getFirstWaybillId());
				ps.setLong(3, FirstWaybillOnShelf.getOrderId());
				ps.setLong(4, FirstWaybillOnShelf.getUserIdOfCustomer());
				if (FirstWaybillOnShelf.getUserIdOfOperator() == null) {
					ps.setNull(5, Types.BIGINT);
				} else {
					ps.setLong(5, FirstWaybillOnShelf.getUserIdOfOperator());
				}
				ps.setString(6, FirstWaybillOnShelf.getSeatCode());
				ps.setString(7, FirstWaybillOnShelf.getTrackingNo());
				ps.setLong(8, FirstWaybillOnShelf.getCreatedTime());
				if (FirstWaybillOnShelf.getLastUpdateTime() == null) {
					ps.setNull(9, Types.BIGINT);
				} else {
					ps.setLong(9, FirstWaybillOnShelf.getLastUpdateTime());
				}
				ps.setString(10, FirstWaybillOnShelf.getStatus());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public List<FirstWaybillOnShelf> findFirstWaybillOnShelf(FirstWaybillOnShelf FirstWaybillOnShelf, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select  id,warehouse_id,first_waybill_id,order_id,user_id_of_customer,user_id_of_operator,seat_code,tracking_no,created_time,last_update_time,status from w_t_first_waybill_on_shelf where 1=1");
		if (FirstWaybillOnShelf.getId() != null) {
			sb.append(" and id = " + FirstWaybillOnShelf.getId());
		}
		if (FirstWaybillOnShelf.getWarehouseId() != null) {
			sb.append(" and warehouse_id = " + FirstWaybillOnShelf.getWarehouseId());
		}
		if (FirstWaybillOnShelf.getFirstWaybillId() != null) {
			sb.append(" and first_waybill_id = " + FirstWaybillOnShelf.getFirstWaybillId());
		}
		if (FirstWaybillOnShelf.getOrderId() != null) {
			sb.append(" and order_id = " + FirstWaybillOnShelf.getOrderId());
		}
		if (FirstWaybillOnShelf.getUserIdOfCustomer() != null) {
			sb.append(" and user_id_of_customer = " + FirstWaybillOnShelf.getUserIdOfCustomer());
		}
		if (FirstWaybillOnShelf.getUserIdOfOperator() != null) {
			sb.append(" and user_id_of_operator = " + FirstWaybillOnShelf.getUserIdOfOperator());
		}
		if (StringUtil.isNotNull(FirstWaybillOnShelf.getSeatCode())) {
			sb.append(" and seat_code = '" + FirstWaybillOnShelf.getSeatCode() + "'");
		}
		if (StringUtil.isNotNull(FirstWaybillOnShelf.getTrackingNo())) {
			sb.append(" and tracking_no = '" + FirstWaybillOnShelf.getTrackingNo() + "'");
		}
		if (StringUtil.isNotNull(FirstWaybillOnShelf.getStatus())) {
			sb.append(" and status = '" + FirstWaybillOnShelf.getStatus() + "'");
		}
		if (moreParam != null) {
			if (StringUtil.isNotNull(moreParam.get("createdTimeStart"))) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time >= " + date.getTime());
				}
			}
			if (StringUtil.isNotNull(moreParam.get("createdTimeEnd"))) {
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
		List<FirstWaybillOnShelf> onShelfList = jdbcTemplate.query(sb.toString(), ParameterizedBeanPropertyRowMapper.newInstance(FirstWaybillOnShelf.class));
		return onShelfList;
	}

	@Override
	public Long countFirstWaybillOnShelf(FirstWaybillOnShelf FirstWaybillOnShelf, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select  count(1) from w_t_first_waybill_on_shelf where 1=1");
		if (FirstWaybillOnShelf.getId() != null) {
			sb.append(" and id = " + FirstWaybillOnShelf.getId());
		}
		if (FirstWaybillOnShelf.getWarehouseId() != null) {
			sb.append(" and warehouse_id = " + FirstWaybillOnShelf.getWarehouseId());
		}
		if (FirstWaybillOnShelf.getFirstWaybillId() != null) {
			sb.append(" and first_waybill_id = " + FirstWaybillOnShelf.getFirstWaybillId());
		}
		if (FirstWaybillOnShelf.getOrderId() != null) {
			sb.append(" and order_id = " + FirstWaybillOnShelf.getOrderId());
		}
		if (FirstWaybillOnShelf.getUserIdOfCustomer() != null) {
			sb.append(" and user_id_of_customer = " + FirstWaybillOnShelf.getUserIdOfCustomer());
		}
		if (FirstWaybillOnShelf.getUserIdOfOperator() != null) {
			sb.append(" and user_id_of_operator = " + FirstWaybillOnShelf.getUserIdOfOperator());
		}
		if (StringUtil.isNotNull(FirstWaybillOnShelf.getSeatCode())) {
			sb.append(" and seat_code = '" + FirstWaybillOnShelf.getSeatCode() + "'");
		}
		if (StringUtil.isNotNull(FirstWaybillOnShelf.getTrackingNo())) {
			sb.append(" and tracking_no = '" + FirstWaybillOnShelf.getTrackingNo() + "'");
		}
		if (StringUtil.isNotNull(FirstWaybillOnShelf.getStatus())) {
			sb.append(" and status = '" + FirstWaybillOnShelf.getStatus() + "'");
		}
		if (moreParam != null) {
			if (StringUtil.isNotNull(moreParam.get("createdTimeStart"))) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time >= " + date.getTime());
				}
			}
			if (StringUtil.isNotNull(moreParam.get("createdTimeEnd"))) {
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
	public int updateFirstWaybillOnShelf(Long OrderId, String newStatus) {
		String sql = "update w_t_first_waybill_on_shelf set status= ?,last_update_time= ? where order_id=?";
		return jdbcTemplate.update(sql, newStatus, System.currentTimeMillis(), OrderId);
	}

	@Override
	public int updateFirstWaybillOnShelf(FirstWaybillOnShelf FirstWaybillOnShelf) {
		String sql = "update w_t_first_waybill_on_shelf set status= ?,last_update_time= ? where id=" + FirstWaybillOnShelf.getId();
		return jdbcTemplate.update(sql, FirstWaybillOnShelf.getStatus(), FirstWaybillOnShelf.getLastUpdateTime());
	}

	@Override
	public String findSeatCodeForOnShelf(String businessType) {
		String sql = "SELECT a.seat_code FROM `w_w_seat` a LEFT JOIN w_t_first_waybill_on_shelf b on a.seat_code = b.seat_code  left outer join w_w_shelf c on a.shelf_code = c.shelf_code WHERE (b.`status` IS NULL or b.`status` = 'OUT' )  and c.business_type = '"
				+ businessType + "' ";
		Pagination page = new Pagination();
		page.curPage = 1;
		page.pageSize = 1;
		page.sortOrder = "asc";
		page.sortName = "id";
		sql += page.generatePageSqlOnTable("a");

		List<String> seatCodes = jdbcTemplate.queryForList(sql, String.class);
		if (seatCodes != null && seatCodes.size() > 0) {
			return seatCodes.get(0);
		}
		return null;
	}

	@Override
	public String findStatusByFirstWaybillId(Long FirstWaybillId) {
		String sql = "SELECT status FROM `w_t_first_waybill_on_shelf` where first_waybill_id = " + FirstWaybillId;
		Pagination page = new Pagination();
		page.curPage = 1;
		page.pageSize = 1;
		page.sortOrder = "desc";
		page.sortName = "created_time";
		sql += page.generatePageSql();
		List<String> statuss = jdbcTemplate.queryForList(sql, String.class);
		if (statuss != null && statuss.size() > 0) {
			return statuss.get(0);
		}
		return null;
	}

	@Override
	public FirstWaybillOnShelf findFirstWaybillOnShelfByFirstWaybillId(Long FirstWaybillId) {
		String sql = "SELECT id,warehouse_id,first_waybill_id,order_id,user_id_of_customer,user_id_of_operator,seat_code,tracking_no,created_time,last_update_time,status FROM `w_t_first_waybill_on_shelf` where first_waybill_id = "
				+ FirstWaybillId;
		Pagination page = new Pagination();
		page.curPage = 1;
		page.pageSize = 1;
		page.sortOrder = "desc";
		page.sortName = "created_time";
		sql += page.generatePageSql();
		List<FirstWaybillOnShelf> onShelfList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(FirstWaybillOnShelf.class));
		if (onShelfList != null && onShelfList.size() > 0) {
			return onShelfList.get(0);
		}
		return null;
	}
}
