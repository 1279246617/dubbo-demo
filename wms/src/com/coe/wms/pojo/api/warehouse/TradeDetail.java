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
	
	@XmlElementWrapper(name = "tradeOrders")
	@XmlElement(name = "tradeOrder")
	public List<TradeOrder> getTradeOrders() {
		return tradeOrders;
	}

	public void setTradeOrders(List<TradeOrder> tradeOrders) {
		this.tradeOrders = tradeOrders;
	}
}
