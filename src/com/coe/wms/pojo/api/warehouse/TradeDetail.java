package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;
import java.util.List;

public class TradeDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6739241949417198734L;
	
	private List<TradeOrder> tradeOrders;

	public List<TradeOrder> getTradeOrders() {
		return tradeOrders;
	}

	public void setTradeOrders(List<TradeOrder> tradeOrders) {
		this.tradeOrders = tradeOrders;
	}
}
