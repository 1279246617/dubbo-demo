package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class LogisticsDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8909591766264313557L;
	
	@XmlElement
	private List<LogisticsOrder> logisticsOrders;

	public List<LogisticsOrder> getLogisticsOrders() {
		return logisticsOrders;
	}

	public void setLogisticsOrders(List<LogisticsOrder> logisticsOrders) {
		this.logisticsOrders = logisticsOrders;
	}

}
