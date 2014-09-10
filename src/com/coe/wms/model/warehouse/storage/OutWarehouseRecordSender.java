package com.coe.wms.model.warehouse.storage;

import java.io.Serializable;

/**
 * 
 * 出库发件人
 * 
 * @author Administrator
 * 
 */
public class OutWarehouseRecordSender implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4245802122495822276L;
	private Long id;
	/**
	 * orderId
	 */
	private Long orderId;

	/**
	 * 发件人名
	 */
	private String name;

	/**
	 * 发件人公司
	 */
	private String company;

	/**
	 * 发件人名
	 */
	private String firstName;

	/**
	 * 发件人姓
	 */
	private String lastName;

	/**
	 * 街道1
	 */
	private String addressLine1;

	/**
	 * 街道2
	 */
	private String addressLine2;

	/**
	 * 省/州
	 */
	private String stateOrProvince;

	/**
	 * 城市
	 */
	private String city;
	/**
	 * 县
	 */
	private String county;

	/**
	 * 邮编
	 */
	private String postalCode;

	/**
	 * 国家,此处本应设计为 国家库表的id
	 * 
	 * 但为了防止出现 国家库无该国家名称,编码不一致,或者其他原因无法在国家库找到记录,导致订单无法发货
	 * 
	 * 所以这里直接保存 国家2字码和国家英文名称
	 */
	private String countryCode;

	/**
	 * 国家名称, 不限制语言
	 */
	private String countryName;

	/**
	 * 发件人电话号码
	 */
	private String phoneNumber;
	/**
	 * 发件人邮箱
	 */
	private String email;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getStateOrProvince() {
		return stateOrProvince;
	}

	public void setStateOrProvince(String stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
