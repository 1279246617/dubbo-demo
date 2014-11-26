package com.coe.wms.model.test;

import java.io.Serializable;

/**
 * test
 * @author wangliang
 *
 */
public class Test implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6954682917702466145L;
	private Long id;

	private String name;

	private char sex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

}
