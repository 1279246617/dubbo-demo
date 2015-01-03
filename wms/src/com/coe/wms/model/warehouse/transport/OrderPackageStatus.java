package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

import com.google.code.ssm.api.CacheKeyMethod;

/**
 * 转运订单大包状态
 * 
 * @author Administrator
 * 
 */
public class OrderPackageStatus implements Serializable {

	public class OrderPackageStatusCode {

		/**
		 * 发送审核通过给顺丰, 待顺丰发实际货物到仓库, 待收货 Wait Receiver Goods
		 */
		public static final String WRG = "WRG";

		/**
		 * 完成收货 ,待完成上架
		 */
		public static final String WOS = "WOS";
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
