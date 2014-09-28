package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

/**
 * 仓配入库 eventBody
 * 
 * @author Administrator
 * 
 */
public class EventBody implements Serializable {

	private static final long serialVersionUID = 5322710746438843491L;
	
	@XmlElement
	private LogisticsDetail logisticsDetail;
	
	public LogisticsDetail getLogisticsDetail() {
		return logisticsDetail;
	}

	public void setLogisticsDetail(LogisticsDetail logisticsDetail) {
		this.logisticsDetail = logisticsDetail;
	}
}
