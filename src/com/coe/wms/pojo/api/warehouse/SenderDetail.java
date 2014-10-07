package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

public class SenderDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7799767411516978710L;
	private String name;
	private String phone;
	private String mobile;
	private String email;
	private String province;
	private String city;
	private String district;
	private String streetAddress;
	private String zipCode;
	private String country;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
