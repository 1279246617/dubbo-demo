package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class SkuDetail implements Serializable {

	private static final long serialVersionUID = 7265005593959517177L;

	private List<Sku> skus;

	@XmlElementWrapper(name = "skus")
	@XmlElement(name = "sku")
	public List<Sku> getSkus() {
		return skus;
	}

	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}
}
