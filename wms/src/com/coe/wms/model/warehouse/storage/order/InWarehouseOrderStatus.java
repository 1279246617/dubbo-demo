package com.coe.wms.model.warehouse.storage.order;

import java.io.Serializable;

import com.google.code.ssm.api.CacheKeyMethod;

/**
 * 大包状态
 * 
 * @author Administrator
 * 
 */
public class InWarehouseOrderStatus implements Serializable {

	public class InWarehouseOrderStatusCode {
		/**
		 * 全未入库
		 */
		public static final String NONE = "NONE";
		/**
		 * 部分入库
		 */
		public static final String PART = "PART";
		/**
		 * 完成入库
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
