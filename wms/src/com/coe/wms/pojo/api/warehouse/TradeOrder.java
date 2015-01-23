package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class TradeOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5326987733467003190L;

	private String tradeOrderId;

	private String occurTime;

	private String tardeOrderValue;

	private String tardeOrderValueUnit;

	private String tardeOrderTaxValue;

	private String tardeOrderTaxValueUnit;
	private String tradeRemark;

	private Buyer buyer;

	private List<Item> items;

	public String getTradeOrderId() {
		return tradeOrderId;
	}

	public void setTradeOrderId(String tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}

	public String getTradeRemark() {
		return tradeRemark;
	}

	public void setTradeRemark(String tradeRemark) {
		this.tradeRemark = tradeRemark;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	@XmlElementWrapper(name = "items")
	@XmlElement(name = "item")
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public String getTardeOrderValue() {
		return tardeOrderValue;
	}

	public void setTardeOrderValue(String tardeOrderValue) {
		this.tardeOrderValue = tardeOrderValue;
	}

	public String getTardeOrderValueUnit() {
		return tardeOrderValueUnit;
	}

	public void setTardeOrderValueUnit(String tardeOrderValueUnit) {
		this.tardeOrderValueUnit = tardeOrderValueUnit;
	}

	public String getTardeOrderTaxValue() {
		return tardeOrderTaxValue;
	}

	public void setTardeOrderTaxValue(String tardeOrderTaxValue) {
		this.tardeOrderTaxValue = tardeOrderTaxValue;
	}

	public String getTardeOrderTaxValueUnit() {
		return tardeOrderTaxValueUnit;
	}

	public void setTardeOrderTaxValueUnit(String tardeOrderTaxValueUnit) {
		this.tardeOrderTaxValueUnit = tardeOrderTaxValueUnit;
	}
}
