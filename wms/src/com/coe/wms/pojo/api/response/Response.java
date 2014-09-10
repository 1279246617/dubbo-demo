package com.coe.wms.pojo.api.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response", namespace = "http://wms.coe.com.hk")
@XmlAccessorType(XmlAccessType.FIELD)
public class Response implements Serializable {

	public static final String DESCRIPTION_EMPTY = "内容不能为空";

	/**
	 * 
	 */
	private static final long serialVersionUID = 5138207975468974019L;
	/**
	 * 调用是否成功
	 */
	private String succeeded;
	/**
	 * 调用错误说明
	 */
	private String description;

	/**
	 * 调用返回码，0表示正常，大于0通常表示内部运行时发现异常或不理想情况，但不影响结果，小于0表示错误；此变量仅用来调试，
	 * 调用结果是否正确仅参考succeeded参数，参数code的值无意义
	 */
	private String code;
	/**
	 * 返回结果版本，默认为1
	 */
	private String version;

	/**
	 * 其他返回结果，见后文描述
	 */
	private Result result;

	public String getSucceeded() {
		return succeeded;
	}

	public void setSucceeded(String succeeded) {
		this.succeeded = succeeded;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
}
