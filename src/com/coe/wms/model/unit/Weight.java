package com.coe.wms.model.unit;

import java.io.Serializable;

import com.coe.wms.util.NumberUtil;

public class Weight implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6809591857470906820L;

	public class WeightCode {
		/**
		 * 克
		 */
		public static final String G = "G";

		/**
		 * 千克
		 */
		public static final String KG = "KG";
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

	/**
	 * g 转 kg 并保留3位小数
	 * 
	 * @param g
	 * @return
	 */
	public static double gTurnToKg(double g) {
		return NumberUtil.div(g, 1000.0, 3);
	}
}
