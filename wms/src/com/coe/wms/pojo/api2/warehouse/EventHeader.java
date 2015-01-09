package com.coe.wms.pojo.api2.warehouse;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class EventHeader implements Serializable {
	private static final long serialVersionUID = 7237402518355471977L;

	private String eventType;

	private String eventMessageId;

	private String eventTime;

	private String eventSource;

	private String eventTarget;

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventSource() {
		return eventSource;
	}

	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}

	public String getEventTarget() {
		return eventTarget;
	}

	public void setEventTarget(String eventTarget) {
		this.eventTarget = eventTarget;
	}

	public String getEventMessageId() {
		return eventMessageId;
	}

	public void setEventMessageId(String eventMessageId) {
		this.eventMessageId = eventMessageId;
	}
}
