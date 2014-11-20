package com.coe.wms.dao.warehouse.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.ITrackingNoDao;
import com.coe.wms.model.warehouse.TrackingNo;
import com.coe.wms.util.Pagination;

@Repository("trackingNoDao")
public class TrackingNoDaoImpl implements ITrackingNoDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private Logger logger = Logger.getLogger(TrackingNoDaoImpl.class);

	/**
	 * 获取可用的跟踪号
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public TrackingNo getAvailableTrackingNoByType(String type) {
		Pagination pagination = new Pagination();
		pagination.curPage = 1;
		pagination.pageSize = 1;
		pagination.sortName = "created_time";
		pagination.sortOrder = "asc";
		String sql = "select id,type,tracking_no,created_time,status,used_time,locked_time from w_w_tracking_no where type = '" + type + "' and (status is null or status = '0') " + pagination.generatePageSql();
		logger.info("查询跟踪号sql:" + sql);
		List<TrackingNo> trackingNoList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(TrackingNo.class));
		if (trackingNoList.size() > 0) {
			return trackingNoList.get(0);
		}
		return null;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public int usedTrackingNo(Long trackingNoId) {
		String sql = "update w_w_tracking_no set status = " + TrackingNo.STATUS_USED + " , used_time = " + System.currentTimeMillis() + " where id=" + trackingNoId;
		return jdbcTemplate.update(sql);
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public int lockTrackingNo(Long trackingNoId) {
		String sql = "update w_w_tracking_no set status = " + TrackingNo.STATUS_LOCKED + " , locked_time = " + System.currentTimeMillis() + " where id=" + trackingNoId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public List<TrackingNo> findTrackingNo(String trackingNo, String type) {
		String sql = "select id,type,tracking_no,created_time,status,used_time,locked_time from w_w_tracking_no where type = '" + type + "' and tracking_no = '" + trackingNo + "'";
		List<TrackingNo> trackingNoList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(TrackingNo.class));
		return trackingNoList;
	}

	@Override
	public TrackingNo getTrackingNoById(Long id) {
		String sql = "select id,type,tracking_no,created_time,status,used_time,locked_time from w_w_tracking_no where id=" + id;
		List<TrackingNo> trackingNoList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(TrackingNo.class));
		if (trackingNoList == null || trackingNoList.size() <= 0) {
			return null;
		}
		return trackingNoList.get(0);
	}
}
