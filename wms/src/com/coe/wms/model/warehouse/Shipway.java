package com.coe.wms.model.warehouse;

import java.io.Serializable;

import com.google.code.ssm.api.CacheKeyMethod;

/**
 * 出货渠道
 * 
 * @author Administrator
 * 
 */
public class Shipway implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2813364290521655728L;

	public class ShipwayCode {

		public static final String ETK = "ETK";

		public static final String B2C = "B2C";

		public static final String SF = "SF";
	}

	private Long id;

	/**
	 * 
	 */
	private String code;

	/**
	 * 状态
	 */
	private String cn;

	/**
	 * 状态
	 */
	private String en;

	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@CacheKeyMethod
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
