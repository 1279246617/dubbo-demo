package com.coe.wms.model.unit;

import java.io.Serializable;

public class Currency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5514935745087534920L;

	public class CurrencyCode {
		/**
		 * 人民币
		 */
		public static final String CNY = "CNY";

		/**
		 * 人民币
		 */
		public static final String RMB = "RMB";

		/**
		 * 美元
		 */
		public static final String USD = "USD";

		/**
		 * 港币
		 */
		public static final String HKD = "HKD";

		/**
		 * 欧元
		 */
		public static final String EUR = "EUR";

		/**
		 * 英镑
		 */
		public static final String GBP = "GBP";

		/**
		 * 日元
		 */
		public static final String JPY = "JPY";
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
