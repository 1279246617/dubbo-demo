package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 出库运单记录
 * 
 * 一个coe单号对应多个顺丰单号
 * 
 * 
 * @author Administrator
 * 
 */
public class PackageRecordItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -982988052612905850L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 仓库id
	 */
	private Long warehouseId;

	/**
	 * 出货时绑定的coe跟踪号码
	 */
	private String coeTrackingNo;

	/**
	 * 跟踪号码的id
	 */
	private Long coeTrackingNoId;

	/**
	 * 操作员Id
	 */
	private Long userIdOfOperator;

	/**
	 * 客户的用户Id
	 */
	private Long userIdOfCustomer;

	/**
	 * 顺丰运单
	 * 
	 * 出库订单的跟踪号, 如果是顺丰渠道,则是顺丰单号,如果是ETK渠道则是ETK单号
	 */
	private String bigPackageTrackingNo;

	private Long bigPackageId;
	/**
	 * 创建时间
	 */
	private Long createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getCoeTrackingNo() {
		return coeTrackingNo;
	}

	public void setCoeTrackingNo(String coeTrackingNo) {
		this.coeTrackingNo = coeTrackingNo;
	}

	public Long getCoeTrackingNoId() {
		return coeTrackingNoId;
	}

	public void setCoeTrackingNoId(Long coeTrackingNoId) {
		this.coeTrackingNoId = coeTrackingNoId;
	}

	public Long getUserIdOfOperator() {
		return userIdOfOperator;
	}

	public void setUserIdOfOperator(Long userIdOfOperator) {
		this.userIdOfOperator = userIdOfOperator;
	}

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getBigPackageTrackingNo() {
		return bigPackageTrackingNo;
	}

	public void setBigPackageTrackingNo(String bigPackageTrackingNo) {
		this.bigPackageTrackingNo = bigPackageTrackingNo;
	}

	public Long getBigPackageId() {
		return bigPackageId;
	}

	public void setBigPackageId(Long bigPackageId) {
		this.bigPackageId = bigPackageId;
	}
}
