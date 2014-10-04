package com.coe.wms.model.warehouse.storage.order;

import java.io.Serializable;

/**
 * 
 * 出库主单
 * 
 * @author Administrator
 * 
 */
public class OutWarehouseOrder implements Serializable {

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
	 * 订单所属客户id
	 */
	private Long userIdOfCustomer;

	/**
	 * 订单所属操作员id
	 */
	private Long userIdOfOperator;
	/**
	 * 出货渠道
	 */
	private String shipwayCode;
	/**
	 * 批次号
	 */
	private String batchNo;

	/**
	 * 客户下的大包号
	 * 
	 * 要求一个入库单中只能包含同一个大包号的sku
	 */
	private String packageNo;

	/**
	 * 创建时间 (收货时间)
	 */
	private Long createdTime;

	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 出库摘要
	 */
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


	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getShipwayCode() {
		return shipwayCode;
	}

	public void setShipwayCode(String shipwayCode) {
		this.shipwayCode = shipwayCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
}
