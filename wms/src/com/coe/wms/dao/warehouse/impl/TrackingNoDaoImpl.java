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
		String sql = "select id,type,tracking_no,tracking_no,created_time,is_used,used_time from w_w_tracking_no where type = '" + type + "' and (is_used is null or is_used = 'N') " + pagination.generatePageSql();
		logger.info("查询跟踪号sql:" + sql);
		List<TrackingNo> trackingNoList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(TrackingNo.class));
		if (trackingNoList.size() > 0) {
			return trackingNoList.get(0);
		}
		return null;
	}

	/**
	 * 更新是否已经使用
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public int updateTrackingNo(TrackingNo trackingNo) {
		String sql = "update w_w_tracking_no set is_used ='" + trackingNo.getIsUsed() + "' , used_time = " + trackingNo.getUsedTime() + " where id=" + trackingNo.getId();
		return jdbcTemplate.update(sql);
	}

}
