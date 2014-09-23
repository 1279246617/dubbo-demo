package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

/**
 * SKU入库请求内容封装类
 */
public class LogisticsEventsRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4731883715949095086L;

	private LogisticsEvent logisticsEvent;

	public LogisticsEvent getLogisticsEvent() {
		return logisticsEvent;
	}

	public void setLogisticsEvent(LogisticsEvent logisticsEvent) {
		this.logisticsEvent = logisticsEvent;
	}
}
