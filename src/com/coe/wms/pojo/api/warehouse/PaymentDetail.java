package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

public class PaymentDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3545370707998887691L;

	private Paid paid;

	private Double totalShippingFee;

	private String totalShippingUnit;

	private Double payableWeight;

	public Paid getPaid() {
		return paid;
	}

	public void setPaid(Paid paid) {
		this.paid = paid;
	}

	public Double getTotalShippingFee() {
		return totalShippingFee;
	}

	public void setTotalShippingFee(Double totalShippingFee) {
		this.totalShippingFee = totalShippingFee;
	}

	public String getTotalShippingUnit() {
		return totalShippingUnit;
	}

	public void setTotalShippingUnit(String totalShippingUnit) {
		this.totalShippingUnit = totalShippingUnit;
	}

	public Double getPayableWeight() {
		return payableWeight;
	}

	public void setPayableWeight(Double payableWeight) {
		this.payableWeight = payableWeight;
	}
}
