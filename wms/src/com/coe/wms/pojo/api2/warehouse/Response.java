package com.coe.wms.pojo.api2.warehouse;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = 2350853401465402907L;

	private String success;

	private String reason;

	private String errorInfo;

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

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
}
