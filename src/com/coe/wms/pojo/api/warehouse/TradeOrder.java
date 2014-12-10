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
}
