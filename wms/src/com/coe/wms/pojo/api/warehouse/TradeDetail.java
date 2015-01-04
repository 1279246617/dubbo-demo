package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class TradeDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6739241949417198734L;

	private List<TradeOrder> tradeOrders;
	/**
	 * 交易类型:顺丰海淘 ;流连
	 */
	private String tradeType;

	@XmlElementWrapper(name = "tradeOrders")
	@XmlElement(name = "tradeOrder")
	public List<TradeOrder> getTradeOrders() {
		return tradeOrders;
	}

	public void setTradeOrders(List<TradeOrder> tradeOrders) {
		this.tradeOrders = tradeOrders;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
}
