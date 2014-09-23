package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

public class ResponseItems implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7864575388861169155L;

	private Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}
