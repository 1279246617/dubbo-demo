package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;
import java.util.List;

public class OutOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7750566291547691074L;

	/**
	 * 出库订单中 才有此字段, 非本系统的大包号 顺丰海淘订单号（系统判定如果订单号是否存在，如存在则进行更新。如订单已确认则拒绝更新）
	 */
	private String orderId;

	/**
	 * 发送时间
	 */
	private String orderTime;
	/**
	 * 渠道（顺丰全球顺）
	 */
	private String channel;
	/**
	 * 商品清单（以下为itemList子内容）
	 */
	private List<Item> itemList;

	/**
	 * 收件人详情
	 */
	private Receiver receiverDetail;

	/**
	 * 发件方详情
	 */
	private Sender senderDetail;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public Receiver getReceiverDetail() {
		return receiverDetail;
	}

	public void setReceiverDetail(Receiver receiverDetail) {
		this.receiverDetail = receiverDetail;
	}

	public Sender getSenderDetail() {
		return senderDetail;
	}

	public void setSenderDetail(Sender senderDetail) {
		this.senderDetail = senderDetail;
	}
}
