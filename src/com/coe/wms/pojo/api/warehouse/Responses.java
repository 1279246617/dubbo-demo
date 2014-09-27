package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;
import java.util.List;

public class Responses implements Serializable {

	private static final long serialVersionUID = 2100301746079308394L;
	private List<Response> responseItems;

	public List<Response> getResponseItems() {
		return responseItems;
	}

	public void setResponseItems(List<Response> responseItems) {
		this.responseItems = responseItems;
	}
}
