package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

public class Receiver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3963974956556982596L;
	/**
	 * 收货人编号（顺丰海淘）
	 */
	private String code;

	/**
	 * 收货人姓名
	 */
	private String name;
	/**
	 * 收货人手机
	 */
	private String mobile;
	/**
	 * 收货地址国家
	 */
	private String country;
	/**
	 * 收货地址省份
	 */
	private String province;
	/**
	 * 收货地址城市
	 */
	private String city;
	/**
	 * 收货地址区
	 */
	private String district;
	/**
	 * 收货地址明细
	 */
	private String address;
	/**
	 * 邮编
	 */
	private String zipCode;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
