package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

public class Volume implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9141609427686575359L;
	private Double length;
	private Double width;
	private Double height;

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}
}
