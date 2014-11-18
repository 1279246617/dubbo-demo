package com.coe.wms.dao.warehouse.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.ISeatDao;
import com.coe.wms.model.warehouse.Seat;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;

@Repository("seatDao")
public class SeatDaoImpl implements ISeatDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private Logger logger = Logger.getLogger(SeatDaoImpl.class);

	@Override
	@DataSource(DataSourceCode.WMS)
	public Seat getSeatByCode(Long code) {
		String sql = "select id,warehouse_id,shelf_code,seat_code,remark from w_w_seat where seat_code = ?";
		List<Seat> seatList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(Seat.class));
		if (seatList.size() > 0) {
			return seatList.get(0);
		}
		return null;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public List<Seat> findSeat(Seat seat, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,shelf_code,seat_code,remark from w_w_seat where 1=1");
		if (seat.getId() != null) {
			sb.append(" and id = " + seat.getId());
		}
		if (StringUtil.isNotNull(seat.getSeatCode())) {
			sb.append(" and seat_code = '" + seat.getSeatCode() + "'");
		}
		if (StringUtil.isNotNull(seat.getShelfCode())) {
			sb.append(" and shelf_code = '" + seat.getShelfCode() + "'");
		}
		if (StringUtil.isNotNull(seat.getRemark())) {
			sb.append(" and remark = '" + seat.getRemark() + "'");
		}
		if (seat.getWarehouseId() != null) {
			sb.append(" and warehouse_id = " + seat.getWarehouseId());
		}
		if (page != null) {
			// 分页sql
			sb.append(page.generatePageSql());
		}
		List<Seat> warehouseList = jdbcTemplate.query(sb.toString(), ParameterizedBeanPropertyRowMapper.newInstance(Seat.class));
		return warehouseList;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public Long countSeat(Seat seat) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1) from w_w_seat where 1=1");
		if (seat.getId() != null) {
			sb.append(" and id = " + seat.getId());
		}
		if (StringUtil.isNotNull(seat.getSeatCode())) {
			sb.append(" and seat_code = '" + seat.getSeatCode() + "'");
		}
		if (StringUtil.isNotNull(seat.getShelfCode())) {
			sb.append(" and shelf_code = '" + seat.getShelfCode() + "'");
		}
		if (StringUtil.isNotNull(seat.getRemark())) {
			sb.append(" and remark = '" + seat.getRemark() + "'");
		}
		if (seat.getWarehouseId() != null) {
			sb.append(" and warehouse_id = " + seat.getWarehouseId());
		}
		String sql = sb.toString();
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		if (count == null) {
			return 0l;
		}
		return count;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public int saveBatchSeat(final List<Seat> seatList) {
		final String sql = "insert into w_w_seat (seat_code,shelf_code,warehouse_id,remark) values (?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Seat seat = seatList.get(i);
				ps.setString(1, seat.getSeatCode());
				ps.setString(2, seat.getShelfCode());
				ps.setLong(3, seat.getWarehouseId());
				ps.setString(4, seat.getRemark());
			}

			@Override
			public int getBatchSize() {
				return seatList.size();
			}
		});
		return NumberUtil.sumArry(batchUpdateSize);
	}
}
