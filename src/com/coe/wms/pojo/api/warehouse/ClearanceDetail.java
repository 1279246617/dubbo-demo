package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

public class ClearanceDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7769929815846807840L;
	private String carrierCode;

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
}
