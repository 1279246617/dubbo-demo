package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

public class LogisticsDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8909591766264313557L;

	private List<LogisticsOrder> logisticsOrders;

	@XmlElementWrapper(name = "logisticsOrders")
	@XmlElement(name = "logisticsOrder")
	public List<LogisticsOrder> getLogisticsOrders() {
		return logisticsOrders;
	}
	
	public void setLogisticsOrders(List<LogisticsOrder> logisticsOrders) {
		this.logisticsOrders = logisticsOrders;
	}
}
