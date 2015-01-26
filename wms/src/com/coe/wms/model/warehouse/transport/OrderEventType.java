package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

public class OrderEventType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 791116897324569735L;

	public class OrderEventTypeCode {
		/**
		 * 电子信息预报 Electronic Information
		 */
		public static final String EI = "EI";

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
