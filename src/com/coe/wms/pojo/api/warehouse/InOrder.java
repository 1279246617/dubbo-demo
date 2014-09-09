package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;
import java.util.List;

/**
 * 顺丰入库订单 xml 对应pojo
 * 
 * @author Administrator
 * 
 */
public class InOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1448505801843124626L;

	/**
	 * 运单号/提货单号
	 */
	private String handoverNumber;

	/**
	 * 签名
	 */
	private String signature;

	/**
	 * 时间戳
	 */
	private String timestamp;

	/**
	 * 商品清单（以下为itemList子内容）
	 */
	private List<InOrderItem> itemList;

	public String getHandoverNumber() {
		return handoverNumber;
	}

	public void setHandoverNumber(String handoverNumber) {
		this.handoverNumber = handoverNumber;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public List<InOrderItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<InOrderItem> itemList) {
		this.itemList = itemList;
	}
}
