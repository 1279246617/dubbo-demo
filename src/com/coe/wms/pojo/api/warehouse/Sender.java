package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

public class Sender implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7210049372433344213L;

	private String senderName;

	private String senderAddress;

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
}
