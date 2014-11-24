package com.coe.wms.service.report;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.warehouse.report.Report;
import com.coe.wms.model.warehouse.report.ReportType;
import com.coe.wms.util.Pagination;

/**
 * 仓配 api service层
 * 
 * @author Administrator
 * 
 */
public interface IReportService {

	static final Logger logger = Logger.getLogger(IReportService.class);

	public List<ReportType> findAllReportType() throws ServiceException;

	/**
	 * 获取报表
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public Pagination getListReportData(Report param, Map<String, String> moreParam, Pagination page);

	/**
	 * 获取报表
	 * 
	 * @param reportId
	 * @return
	 * @throws ServiceException
	 */
	public Report getReportById(Long reportId) throws ServiceException;
}
