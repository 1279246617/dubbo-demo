package com.coe.wms.model.warehouse;

import java.io.Serializable;

/**
 * 货架
 * 
 * @author Administrator
 * 
 */
public class Shelf implements Serializable {

	/**
	 * 地面货架
	 */
	public static final String TYPE_GROUND = "G";

	/**
	 * 立体货架
	 */
	public static final String TYPE_BUILD = "B";

	/**
	 * 
	 */
	private static final long serialVersionUID = 5963221596346743409L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 货位个数(当类型是地面货架类型时,直接设置货位个数,按照货架号+123456..生成货位
	 */
	private Integer seatQuantity;

	/**
	 * 行 当货架类型是:立体货架时
	 */
	private Integer rows;

	/**
	 * 列 当货架类型是:立体货架时
	 */
	private Integer cols;

	/**
	 * 类型
	 */
	private String shelfType;

	/**
	 * 货架号
	 */
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

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getShelfType() {
		return shelfType;
	}

	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
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

	public Integer getSeatQuantity() {
		return seatQuantity;
	}

	public void setSeatQuantity(Integer seatQuantity) {
		this.seatQuantity = seatQuantity;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getCols() {
		return cols;
	}

	public void setCols(Integer cols) {
		this.cols = cols;
	}
}
