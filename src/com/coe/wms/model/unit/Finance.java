package com.coe.wms.model.unit;

import java.io.Serializable;

public class Finance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7762634922082461362L;

	public class FinanceCode {
		/**
		 * 元
		 */
		public static final String DOLLAR = "DR";

		/**
		 * 角
		 */
		public static final String CORNER = "CR";
		/**
		 * 分
		 */
		public static final String CENTS = "CS";
	}

	private String code;

	private String en;

	private String cn;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}
}