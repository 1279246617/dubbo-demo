package com.coe.wms.pojo.api2.warehouse;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responses")
public class Responses implements Serializable {

	private static final long serialVersionUID = 2100301746079308394L;

	private List<Response> responseItems;

	@XmlElementWrapper(name = "responseItems")
	@XmlElement(name = "response")
	public List<Response> getResponseItems() {
		return responseItems;
	}

	public void setResponseItems(List<Response> responseItems) {
		this.responseItems = responseItems;
	}
}
