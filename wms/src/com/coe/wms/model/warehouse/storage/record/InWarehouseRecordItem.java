package com.coe.wms.model.warehouse.storage.record;

import java.io.Serializable;

/**
 * 入库详情单
 * 
 * @author Administrator
 * 
 */
public class InWarehouseRecordItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2079149253927077125L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 入库主单id
	 */
	private Long inWarehouseRecordId;

	/**
	 * 创建时间(收货时间)
	 */
	private Long createdTime;
	/**
	 * 数量
	 */
	private Integer quantity;

	/**
	 * sku
	 */
	private String sku;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 仓库id
	 */
	private Long warehouseId;

	/**
	 * 货架编号
	 */
	private String shelvesNo;

	/**
	 * 货位编号
	 */
	private String seatNo;
	
	public String getShelvesNo() {
		return shelvesNo;
	}

	public void setShelvesNo(String shelvesNo) {
		this.shelvesNo = shelvesNo;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	/**
	 * 收货人
	 */
	private Long userIdOfOperator;

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

	public Long getInWarehouseRecordId() {
		return inWarehouseRecordId;
	}

	public void setInWarehouseRecordId(Long inWareHouseRecordId) {
		this.inWarehouseRecordId = inWareHouseRecordId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getRemark() {
		return remark;
	}

	public Long getUserIdOfOperator() {
		return userIdOfOperator;
	}

	public void setUserIdOfOperator(Long userIdOfOperator) {
		this.userIdOfOperator = userIdOfOperator;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
}
