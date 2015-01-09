package com.coe.wms.pojo.api2.warehouse;

import java.io.Serializable;


public class LogisticsEvent implements Serializable {

	private static final long serialVersionUID = -2366387117121513855L;
	private EventHeader eventHeader;
	private EventBody eventBody;

	
	public EventHeader getEventHeader() {
		return eventHeader;
	}

	public void setEventHeader(EventHeader eventHeader) {
		this.eventHeader = eventHeader;
	}

	
	public EventBody getEventBody() {
		return eventBody;
	}

	public void setEventBody(EventBody eventBody) {
		this.eventBody = eventBody;
	}
}
