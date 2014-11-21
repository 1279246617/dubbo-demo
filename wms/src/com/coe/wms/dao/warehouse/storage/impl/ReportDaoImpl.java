package com.coe.wms.dao.warehouse.storage.impl;

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
import com.coe.wms.dao.warehouse.storage.IReportDao;
import com.coe.wms.model.warehouse.report.Report;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("reportDao")
public class ReportDaoImpl implements IReportDao {

	Logger logger = Logger.getLogger(ReportDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveReport(final Report order) {
		final String sql = "insert into w_w_report (warehouse_id,user_id_of_customer,user_id_of_operator,created_time,report_name,report_type,file_path,remark) values (?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, order.getWarehouseId());
				ps.setLong(2, order.getUserIdOfCustomer());
				if (order.getUserIdOfOperator() == null) {
					ps.setNull(3, Types.BIGINT);
				} else {
					ps.setDouble(3, order.getUserIdOfOperator());
				}
				ps.setLong(4, order.getCreatedTime());
				ps.setString(5, order.getReportName());
				ps.setString(6, order.getReportType());
				ps.setString(7, order.getFilePath());
				ps.setString(8, order.getRemark());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public Report getReportById(Long reportId) {
		String sql = "select id,warehouse_id,user_id_of_customer,user_id_of_operator,created_time,report_name,report_type,file_path,remark from w_w_report where id =" + reportId;
		Report order = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Report>(Report.class));
		return order;
	}

	/**
	 * 
	 * 参数一律使用实体类加Map . 
	 */
	@Override
	public List<Report> findReport(Report report, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,user_id_of_customer,user_id_of_operator,created_time,report_name,report_type,file_path,remark from w_w_report where 1=1 ");
		if (report != null) {
			if (report.getId() != null) {
				sb.append(" and id = " + report.getId());
			}
			if (report.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + report.getUserIdOfCustomer());
			}
			if (report.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + report.getUserIdOfOperator());
			}
			if (report.getCreatedTime() != null) {
				sb.append(" and created_time = " + report.getCreatedTime());
			}
			if (StringUtil.isNotNull(report.getReportName())) {
				sb.append(" and report_name = '" + report.getReportName() + "' ");
			}
			if (StringUtil.isNotNull(report.getReportType())) {
				sb.append(" and report_type = '" + report.getReportType() + "' ");
			}
			if (StringUtil.isNotNull(report.getFilePath())) {
				sb.append(" and file_path = '" + report.getFilePath() + "' ");
			}
			if (StringUtil.isNotNull(report.getRemark())) {
				sb.append(" and remark = '" + report.getRemark() + "' ");
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
		List<Report> reportList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(Report.class));
		return reportList;
	}

	@Override
	public Long countReport(Report report, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_w_report where 1=1 ");
		if (report != null) {
			if (report.getId() != null) {
				sb.append(" and id = " + report.getId());
			}
			if (report.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + report.getUserIdOfCustomer());
			}
			if (report.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + report.getUserIdOfOperator());
			}
			if (report.getCreatedTime() != null) {
				sb.append(" and created_time = " + report.getCreatedTime());
			}
			if (StringUtil.isNotNull(report.getReportName())) {
				sb.append(" and report_name = '" + report.getReportName() + "' ");
			}
			if (StringUtil.isNotNull(report.getReportType())) {
				sb.append(" and report_type = '" + report.getReportType() + "' ");
			}
			if (StringUtil.isNotNull(report.getFilePath())) {
				sb.append(" and file_path = '" + report.getFilePath() + "' ");
			}
			if (StringUtil.isNotNull(report.getRemark())) {
				sb.append(" and remark = '" + report.getRemark() + "' ");
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
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}