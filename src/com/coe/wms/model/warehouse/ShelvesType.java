package com.coe.wms.model.warehouse;

import java.io.Serializable;

public class ShelvesType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5266286196108854637L;
	/**
	 * 类型代码
	 */
	private Long code;
	/**
	 * 货架类型名称
	 */
	private String name;

	/**
	 * 货架类型备注
	 */
	private String remark;

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
