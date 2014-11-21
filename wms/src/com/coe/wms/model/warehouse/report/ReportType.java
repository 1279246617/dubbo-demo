package com.coe.wms.model.warehouse.report;

import java.io.Serializable;

import com.google.code.ssm.api.CacheKeyMethod;

/**
 * 报表
 * 
 * @author Administrator
 * 
 */
public class ReportType implements Serializable {

	public class ReportTypeCode {
		/**
		 * 入库报表
		 */
		public static final String IN_WAREHOUSE_REPORT = "IN_WAREHOUSE_REPORT";

		/**
		 * 出库报表
		 */
		public static final String OUT_WAREHOUSE_REPORT = "OUT_WAREHOUSE_REPORT";

		/**
		 * 库存报表
		 */
		public static final String INVENTORY_REPORT = "INVENTORY_REPORT";

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
