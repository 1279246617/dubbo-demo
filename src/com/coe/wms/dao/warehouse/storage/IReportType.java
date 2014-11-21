package com.coe.wms.dao.warehouse.storage;

import java.util.List;

import com.coe.wms.model.warehouse.report.ReportType;

public interface IReportType {

	public ReportType findReportTypeByCode(String code);

	public List<ReportType> findAllReportType();
}
