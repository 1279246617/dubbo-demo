package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

import com.google.code.ssm.api.CacheKeyMethod;

/**
 * 转运订单状态
 * 
 * @author Administrator
 * 
 */
public class BigPackageStatus implements Serializable {

	public class BigPackageStatusCode {
		/**
		 * 下单:LOGISTICS_TRADE_PAID 顺丰新建转运出库订单,等待COE审核 Wait Warehouse Check
		 */
		public static final String WWC = "WWC";

		/**
		 * 发送审核通过给顺丰, 待顺丰发实际货物到仓库, 待收货 Wait Receiver Goods
		 */
		public static final String WRG = "WRG";

		/**
		 * 部分 入库:收到部分logisticsOrder
		 * 
		 */
		public static final String WRP = "WRP";
		
		/**
		 * ,等待出库称重 Wait Warehouse Weighing
		 */
		public static final String WWW = "WWW";

		/**
		 * COE已称重,等待回传称重给客户 Wait Send Weighing
		 */
		public static final String WSW = "WSW";

		/**
		 * COE称重,并已经回传出库重量
		 * 
		 * 等待客户确认 Wait Customer Check 发货：LOGISTICS_SEND_GOODS
		 */
		public static final String WCC = "WCC";
		/**
		 * 顺丰确认出库,等待COE操作出库 Wait Out Warehouse Operation
		 */
		public static final String WWO = "WWO";

		/**
		 * 出库成功 Out Warehouse Success
		 */
		public static final String SUCCESS = "SUCCESS";

		/**
		 * 出库失败 Out Warehouse Fail
		 */
		public static final String FAIL = "FAIL";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2939757046560151951L;

	private Long id;

	/**
	 * 
	 */
	private String code;

	/**
	 * 状态
	 */
	private String cn;

	/**
	 * 状态
	 */
	private String en;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@CacheKeyMethod
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}
}
