package com.coe.wms.model.warehouse.storage.record;

import java.io.Serializable;

/**
 * 出库建包记录
 * 
 * 建包是否发货,根据OutWarehouseRecord 是否有记录...(变更需求原因,导致,是否发货需要新建一张表)
 * 
 * 
 * 出库建包: 根据coeTrackingNoId 找OutWarehouseRecordId, 找到本次建包订单
 * 
 * 
 * @author Administrator
 * 
 */
public class OutWarehousePackage implements Serializable {

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

	/**
	 * 创建时间
	 */
	private Long shippedTime;

	/**
	 * 是否已经扫描出货
	 */
	private String isShiped;
	
	
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

	public Long getShippedTime() {
		return shippedTime;
	}

	public void setShippedTime(Long shippedTime) {
		this.shippedTime = shippedTime;
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

	public String getIsShiped() {
		return isShiped;
	}

	public void setIsShiped(String isShiped) {
		this.isShiped = isShiped;
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
