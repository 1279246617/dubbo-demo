package com.coe.wms.pojo.api.warehouse.storage;

import java.io.Serializable;
import java.util.List;

public class SkuDetail implements Serializable {

	private static final long serialVersionUID = 7265005593959517177L;
	private List<Sku> skus;

	public List<Sku> getSkus() {
		return skus;
	}

	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}

}
