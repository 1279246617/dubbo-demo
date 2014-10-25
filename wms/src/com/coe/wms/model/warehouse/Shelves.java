package com.coe.wms.model.warehouse;

import java.io.Serializable;

public class Shelves implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1289101354442689843L;

	/**
	 * 所属仓库
	 */
	private Long warehoseId;
	/**
	 * 货架类型
	 */
	private Long shelvesTypeCode;
	/**
	 * 货架代码
	 */
	private String code;
	/**
	 * 货架备注
	 */
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getWarehoseId() {
		return warehoseId;
	}

	public void setWarehoseId(Long warehoseId) {
		this.warehoseId = warehoseId;
	}

	public Long getShelvesTypeCode() {
		return shelvesTypeCode;
	}

	public void setShelvesTypeCode(Long shelvesTypeCode) {
		this.shelvesTypeCode = shelvesTypeCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
