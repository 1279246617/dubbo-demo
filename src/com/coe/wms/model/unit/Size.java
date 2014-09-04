package com.coe.wms.model.unit;

import java.io.Serializable;

/**
 * 大小单位
 * 
 * @author Administrator
 * 
 */
public class Size implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3599105879815231367L;

	public class SizeCode {

	}

	private String code;

	private String en;

	private String cn;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEn() {
		return en;
	}

	public void setEn(String en) {
		this.en = en;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}
}
