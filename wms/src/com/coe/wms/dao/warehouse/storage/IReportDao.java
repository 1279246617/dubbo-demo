package com.coe.wms.dao.warehouse.storage;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.report.Report;
import com.coe.wms.util.Pagination;

public interface IReportDao {

	public long saveReport(Report report);

	public Report getReportById(Long reportId);

	public List<Report> findReport(Report report, Map<String, String> moreParam, Pagination page);

	public Long countReport(Report report, Map<String, String> moreParam);
}
