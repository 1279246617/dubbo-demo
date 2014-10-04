package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

/**
 * 仓配入库 eventBody
 * 
 * @author Administrator
 * 
 */
public class EventBody implements Serializable {

	private static final long serialVersionUID = 5322710746438843491L;

	private LogisticsDetail logisticsDetail;

	private ClearanceDetail clearanceDetail;

	private PaymentDetail paymentDetail;

	private TradeDetail tradeDetail;

	public PaymentDetail getPaymentDetail() {
		return paymentDetail;
	}

	public void setPaymentDetail(PaymentDetail paymentDetail) {
		this.paymentDetail = paymentDetail;
	}

	public LogisticsDetail getLogisticsDetail() {
		return logisticsDetail;
	}

	public TradeDetail getTradeDetail() {
		return tradeDetail;
	}

	public void setTradeDetail(TradeDetail tradeDetail) {
		this.tradeDetail = tradeDetail;
	}

	public void setLogisticsDetail(LogisticsDetail logisticsDetail) {
		this.logisticsDetail = logisticsDetail;
	}

	public ClearanceDetail getClearanceDetail() {
		return clearanceDetail;
	}

	public void setClearanceDetail(ClearanceDetail clearanceDetail) {
		this.clearanceDetail = clearanceDetail;
	}
}
