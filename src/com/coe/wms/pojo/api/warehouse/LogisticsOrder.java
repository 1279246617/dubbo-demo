package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

/**
 * SF文档 7.13. SKU入库指令
 * 
 * @author Administrator
 * 
 */
public class LogisticsOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8116810058074766132L;

	private String logisticsType;
	
	private String logisticsWeight;
	
	private String logisticsFreight;

	private String carrierCode;

	private String mailNo;

	private SkuDetail skuDetail;

	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public String getLogisticsWeight() {
		return logisticsWeight;
	}

	public void setLogisticsWeight(String logisticsWeight) {
		this.logisticsWeight = logisticsWeight;
	}

	public String getLogisticsFreight() {
		return logisticsFreight;
	}

	public void setLogisticsFreight(String logisticsFreight) {
		this.logisticsFreight = logisticsFreight;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public SkuDetail getSkuDetail() {
		return skuDetail;
	}

	public void setSkuDetail(SkuDetail skuDetail) {
		this.skuDetail = skuDetail;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
}
