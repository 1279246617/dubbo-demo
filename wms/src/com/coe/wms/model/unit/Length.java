package com.coe.wms.model.unit;

import java.io.Serializable;

/**
 * 长度单位
 * 
 * @author Administrator
 * 
 */
public class Length implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6655260324134381275L;

	public class LengthCode {
		/**
		 * 千米
		 */
		public static final String KM = "KM";

		/**
		 * 米
		 */
		public static final String M = "M";

		/**
		 * 分米
		 */
		public static final String DM = "DM";

		/**
		 * 厘米
		 */
		public static final String CM = "CM";

		/**
		 * 毫米
		 */
		public static final String MM = "MM";

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
