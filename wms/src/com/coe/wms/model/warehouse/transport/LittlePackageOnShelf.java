package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 转运订单小包上架
 * 
 * 
 * @author Administrator
 * 
 */
public class LittlePackageOnShelf implements Serializable {
	/**
	 * 预分配状态,未上架
	 */
	public static final String STATUS_PRE_ON_SHELF = "PRE";

	/**
	 * 已上架
	 */
	public static final String STATUS_ON_SHELF = "ON";

	/**
	 * 已下架
	 */
	public static final String STATUS_OUT_SHELF = "OUT";

	/**
	 * 
	 */
	private static final long serialVersionUID = -1616729612042254593L;
	private Long id;
	/**
	 * 客户
	 */
	private Long userIdOfCustomer;

	/**
	 * 操作员
	 */
	private Long userIdOfOperator;

	private Long warehouseId;
	/**
	 * 小包id
	 */
	private Long littlePackageId;
	/**
	 * 大包id
	 */
	private Long bigPackageId;
	/**
	 * 跟踪号小包的到货单号
	 */
	private String trackingNo;
	/**
	 * 货位号
	 */
	private String seatCode;
	/**
	 * 上架/下架
	 */
	private String status;

	private Long lastUpdateTime;

	private Long createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Long getLittlePackageId() {
		return littlePackageId;
	}

	public Long getUserIdOfOperator() {
		return userIdOfOperator;
	}

	public void setUserIdOfOperator(Long userIdOfOperator) {
		this.userIdOfOperator = userIdOfOperator;
	}

	public void setLittlePackageId(Long littlePackageId) {
		this.littlePackageId = littlePackageId;
	}

	public Long getBigPackageId() {
		return bigPackageId;
	}

	public void setBigPackageId(Long bigPackageId) {
		this.bigPackageId = bigPackageId;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}
}
