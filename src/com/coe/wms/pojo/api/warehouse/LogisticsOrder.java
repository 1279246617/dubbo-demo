package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

/**
 * SF文档 7.13. SKU入库指令
 * 
 * @author Administrator
 * 
 */
public class LogisticsOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8116810058074766132L;

	private String logisticsType;

	private Double logisticsWeight;

	private String logisticsFreight;

	private String occurTime;

	private String carrierCode;

	private String mailNo;

	/**
	 * Sku入库唯一ID 等于客户订单号
	 */
	private String skuStockInId;

	/**
	 * 物流订单中包含的商品，id列表用,分割，或者是仓配物品
	 */
	private String itemsIncluded;

	private String segmentCode;

	private String poNo;

	private String routeId;

	private String logisticsRemark;

	private String logisticsCode;

	private SenderDetail senderDetail;

	private ReceiverDetail receiverDetail;

	public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}

	public ReceiverDetail getReceiverDetail() {
		return receiverDetail;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public void setReceiverDetail(ReceiverDetail receiverDetail) {
		this.receiverDetail = receiverDetail;
	}

	public String getLogisticsRemark() {
		return logisticsRemark;
	}

	public String getItemsIncluded() {
		return itemsIncluded;
	}

	public void setItemsIncluded(String itemsIncluded) {
		this.itemsIncluded = itemsIncluded;
	}

	public void setLogisticsRemark(String logisticsRemark) {
		this.logisticsRemark = logisticsRemark;
	}

	private SkuDetail skuDetail;

	public String getLogisticsType() {
		return logisticsType;
	}

	public String getSkuStockInId() {
		return skuStockInId;
	}

	public void setSkuStockInId(String skuStockInId) {
		this.skuStockInId = skuStockInId;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getSegmentCode() {
		return segmentCode;
	}

	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public SenderDetail getSenderDetail() {
		return senderDetail;
	}

	public void setSenderDetail(SenderDetail senderDetail) {
		this.senderDetail = senderDetail;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public Double getLogisticsWeight() {
		return logisticsWeight;
	}

	public void setLogisticsWeight(Double logisticsWeight) {
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
