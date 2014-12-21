package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 出库单 (主单)
 * 
 * 
 * 出库主单: 根据coeTrackingNoId 找OutWarehouseShippping, 找到本次出库订单
 * 
 * 
 * @author Administrator
 * 
 */
public class PackageRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1475638002960975692L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 仓库id
	 */
	private Long warehouseId;

	/**
	 * 操作员Id
	 */
	private Long userIdOfOperator;

	/**
	 * 客户的用户Id
	 */
	private Long userIdOfCustomer;

	/**
	 * coe跟踪号
	 */
	private String coeTrackingNo;

	/**
	 * coe跟踪号id
	 */
	private Long coeTrackingNoId;

	/**
	 * 创建时间
	 */
	private Long createdTime;

	private String remark;

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

	public String getCoeTrackingNo() {
		return coeTrackingNo;
	}

	public void setCoeTrackingNo(String coeTrackingNo) {
		this.coeTrackingNo = coeTrackingNo;
	}

	public Long getCoeTrackingNoId() {
		return coeTrackingNoId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCoeTrackingNoId(Long coeTrackingNoId) {
		this.coeTrackingNoId = coeTrackingNoId;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

}
