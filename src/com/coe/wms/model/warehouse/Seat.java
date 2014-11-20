package com.coe.wms.model.warehouse;

import java.io.Serializable;

/**
 * 货位
 * 
 * @author Administrator
 * 
 */
public class Seat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2216058659884286333L;

	public static final String SEAT_STATUS_IDLE = "空闲";
	
	public static final String SEAT_STATUS_USED = "有货";

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 货位编号
	 */
	private String seatCode;

	private String shelfCode;

	/**
	 * 所属仓库
	 */
	private Long warehouseId;
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

	public String getSeatCode() {
		return seatCode;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShelfCode() {
		return shelfCode;
	}

	public void setShelfCode(String shelfCode) {
		this.shelfCode = shelfCode;
	}
}
