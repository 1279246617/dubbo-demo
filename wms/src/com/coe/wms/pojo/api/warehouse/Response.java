package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class Response implements Serializable {

	private static final long serialVersionUID = 2350853401465402907L;

	@XmlElement
	private String success;

	@XmlElement
	private String reason;

	@XmlElement
	private String reasonDesc;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
}
