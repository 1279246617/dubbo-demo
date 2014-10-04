package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

public class Paid implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3162719636962274616L;

	public Double getTradeOrderValue() {
		return tradeOrderValue;
	}

	public void setTradeOrderValue(Double tradeOrderValue) {
		this.tradeOrderValue = tradeOrderValue;
	}

	public String getTradeOrderValueUnit() {
		return tradeOrderValueUnit;
	}

	public void setTradeOrderValueUnit(String tradeOrderValueUnit) {
		this.tradeOrderValueUnit = tradeOrderValueUnit;
	}

	private Double tradeOrderValue;

	private String tradeOrderValueUnit;
}
