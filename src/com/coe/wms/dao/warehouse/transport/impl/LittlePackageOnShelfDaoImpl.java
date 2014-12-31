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

import com.coe.wms.dao.warehouse.transport.ILittlePackageOnShelfDao;
import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

@Repository("littlePackageOnShelfDao")
public class LittlePackageOnShelfDaoImpl implements ILittlePackageOnShelfDao {

	Logger logger = Logger.getLogger(LittlePackageOnShelfDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public long saveLittlePackageOnShelf(final FirstWaybillOnShelf littlePackageOnShelf) {
		final String sql = "insert into w_t_first_waybill_on_shelf ( warehouse_id,little_package_id,big_package_id,user_id_of_customer,user_id_of_operator,seat_code,tracking_no,created_time,last_update_time,status) values (?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, littlePackageOnShelf.getWarehouseId());
				ps.setLong(2, littlePackageOnShelf.getLittlePackageId());
				ps.setLong(3, littlePackageOnShelf.getBigPackageId());
				ps.setLong(4, littlePackageOnShelf.getUserIdOfCustomer());
				if (littlePackageOnShelf.getUserIdOfOperator() == null) {
					ps.setNull(5, Types.BIGINT);
				} else {
					ps.setLong(5, littlePackageOnShelf.getUserIdOfOperator());
				}
				ps.setString(6, littlePackageOnShelf.getSeatCode());
				ps.setString(7, littlePackageOnShelf.getTrackingNo());
				ps.setLong(8, littlePackageOnShelf.getCreatedTime());
				if (littlePackageOnShelf.getLastUpdateTime() == null) {
					ps.setNull(9, Types.BIGINT);
				} else {
					ps.setLong(9, littlePackageOnShelf.getLastUpdateTime());
				}
				ps.setString(10, littlePackageOnShelf.getStatus());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public List<FirstWaybillOnShelf> findLittlePackageOnShelf(FirstWaybillOnShelf littlePackageOnShelf, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select  id,warehouse_id,little_package_id,big_package_id,user_id_of_customer,user_id_of_operator,seat_code,tracking_no,created_time,last_update_time,status from w_t_first_waybill_on_shelf where 1=1");
		if (littlePackageOnShelf.getId() != null) {
			sb.append(" and id = " + littlePackageOnShelf.getId());
		}
		if (littlePackageOnShelf.getWarehouseId() != null) {
			sb.append(" and warehouse_id = " + littlePackageOnShelf.getWarehouseId());
		}
		if (littlePackageOnShelf.getLittlePackageId() != null) {
			sb.append(" and little_package_id = " + littlePackageOnShelf.getLittlePackageId());
		}
		if (littlePackageOnShelf.getBigPackageId() != null) {
			sb.append(" and big_package_id = " + littlePackageOnShelf.getBigPackageId());
		}
		if (littlePackageOnShelf.getUserIdOfCustomer() != null) {
			sb.append(" and user_id_of_customer = " + littlePackageOnShelf.getUserIdOfCustomer());
		}
		if (littlePackageOnShelf.getUserIdOfOperator() != null) {
			sb.append(" and user_id_of_operator = " + littlePackageOnShelf.getUserIdOfOperator());
		}
		if (StringUtil.isNotNull(littlePackageOnShelf.getSeatCode())) {
			sb.append(" and seat_code = '" + littlePackageOnShelf.getSeatCode() + "'");
		}
		if (StringUtil.isNotNull(littlePackageOnShelf.getTrackingNo())) {
			sb.append(" and tracking_no = '" + littlePackageOnShelf.getTrackingNo() + "'");
		}
		if (StringUtil.isNotNull(littlePackageOnShelf.getStatus())) {
			sb.append(" and status = '" + littlePackageOnShelf.getStatus() + "'");
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
	public Long countLittlePackageOnShelf(FirstWaybillOnShelf littlePackageOnShelf, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select  count(1) from w_t_first_waybill_on_shelf where 1=1");
		if (littlePackageOnShelf.getId() != null) {
			sb.append(" and id = " + littlePackageOnShelf.getId());
		}
		if (littlePackageOnShelf.getWarehouseId() != null) {
			sb.append(" and warehouse_id = " + littlePackageOnShelf.getWarehouseId());
		}
		if (littlePackageOnShelf.getLittlePackageId() != null) {
			sb.append(" and little_package_id = " + littlePackageOnShelf.getLittlePackageId());
		}
		if (littlePackageOnShelf.getBigPackageId() != null) {
			sb.append(" and big_package_id = " + littlePackageOnShelf.getBigPackageId());
		}
		if (littlePackageOnShelf.getUserIdOfCustomer() != null) {
			sb.append(" and user_id_of_customer = " + littlePackageOnShelf.getUserIdOfCustomer());
		}
		if (littlePackageOnShelf.getUserIdOfOperator() != null) {
			sb.append(" and user_id_of_operator = " + littlePackageOnShelf.getUserIdOfOperator());
		}
		if (StringUtil.isNotNull(littlePackageOnShelf.getSeatCode())) {
			sb.append(" and seat_code = '" + littlePackageOnShelf.getSeatCode() + "'");
		}
		if (StringUtil.isNotNull(littlePackageOnShelf.getTrackingNo())) {
			sb.append(" and tracking_no = '" + littlePackageOnShelf.getTrackingNo() + "'");
		}
		if (StringUtil.isNotNull(littlePackageOnShelf.getStatus())) {
			sb.append(" and status = '" + littlePackageOnShelf.getStatus() + "'");
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
	public int updateLittlePackageOnShelf(Long bigPackageId, String newStatus) {
		String sql = "update w_t_first_waybill_on_shelf set status= ?,last_update_time= ? where big_package_id=?";
		return jdbcTemplate.update(sql, newStatus, System.currentTimeMillis(), bigPackageId);
	}

	@Override
	public int updateLittlePackageOnShelf(FirstWaybillOnShelf littlePackageOnShelf) {
		String sql = "update w_t_first_waybill_on_shelf set status= ?,last_update_time= ? where id=" + littlePackageOnShelf.getId();
		return jdbcTemplate.update(sql, littlePackageOnShelf.getStatus(), littlePackageOnShelf.getLastUpdateTime());
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
	public String findStatusByLittlePackageId(Long littlePackageId) {
		String sql = "SELECT status FROM `w_t_first_waybill_on_shelf` where little_package_id = " + littlePackageId;
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
	public FirstWaybillOnShelf findLittlePackageOnShelfByLittlePackageId(Long littlePackageId) {
		String sql = "SELECT id,warehouse_id,little_package_id,big_package_id,user_id_of_customer,user_id_of_operator,seat_code,tracking_no,created_time,last_update_time,status FROM `w_t_first_waybill_on_shelf` where little_package_id = "
				+ littlePackageId;
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
