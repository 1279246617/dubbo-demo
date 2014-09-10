package com.coe.wms.model.warehouse.storage;

import java.io.Serializable;

import com.google.code.ssm.api.CacheKeyMethod;

/**
 * 出库订单状态
 * 
 * @author Administrator
 * 
 */
public class OurWarehouseStatus implements Serializable {

	public class OurWareHouseStatusCode {
		/**
		 * 收到出库指令, 但未处理 (草稿)
		 */
		public static final String DRAFT = "DRAFT";
		/**
		 * 出库指令已经确认(不能再修改)
		 */
		public static final String CONFIRM = "CONFIRM";
		/**
		 * 完成出库
		 */
		public static final String COMPLETE = "COMPLETE";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2939757046560151951L;

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
}
