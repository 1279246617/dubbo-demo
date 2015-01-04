package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

import com.google.code.ssm.api.CacheKeyMethod;

/**
 * 转运订单状态
 * 
 * @author Administrator
 * 
 */
public class FirstWaybillStatus implements Serializable {

	public class FirstWaybillStatusCode {
		/**
		 * 待收货
		 */
		public static final String WWR = "WWR";

		/**
		 * 已收货,待发送入库给顺丰
		 */
		public static final String WSR = "WSR";

		/**
		 * 待上架 wait on shelf
		 */
		public static final String WOS = "WOS";

		/**
		 * 已上架,待下架
		 */
		public static final String W_OUT_S = "W_OUT_S";

		/**
		 * 已下架合包 Out Warehouse Success
		 */
		public static final String SUCCESS = "SUCCESS";
		
		public static final String END = "END";

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
