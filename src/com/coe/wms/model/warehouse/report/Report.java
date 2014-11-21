package com.coe.wms.model.warehouse.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coe.wms.util.StringUtil;

/**
 * 货架
 * 
 * @author Administrator
 * 
 */
public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4631003252872415047L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 创建时间
	 */
	private Long createdTime;
	/**
	 * 报表名称
	 */
	private String reportName;
	/**
	 * 报表类型
	 */
	private String reportType;
	/**
	 * 所属仓库
	 */
	private Long warehouseId;

	/**
	 * 报表所属客户id
	 */
	private Long userIdOfCustomer;

	/**
	 * 报表所属操作员id
	 */
	private Long userIdOfOperator;
	/**
	 * 报表文件地址
	 */
	private String filePath;
	/**
	 * 备注
	 */
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public Long getUserIdOfOperator() {
		return userIdOfOperator;
	}

	public void setUserIdOfOperator(Long userIdOfOperator) {
		this.userIdOfOperator = userIdOfOperator;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
