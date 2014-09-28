package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class SkuDetail implements Serializable {

	private static final long serialVersionUID = 7265005593959517177L;

	@XmlElement
	private List<Sku> skus;

	public List<Sku> getSkus() {
		return skus;
	}

	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}
}
