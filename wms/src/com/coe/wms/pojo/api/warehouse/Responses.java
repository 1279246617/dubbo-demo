package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

public class Responses implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2100301746079308394L;
	private ResponseItems responseItems;

	public ResponseItems getResponseItems() {
		return responseItems;
	}

	public void setResponseItems(ResponseItems responseItems) {
		this.responseItems = responseItems;
	}
}
