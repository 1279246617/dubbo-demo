package com.coe.message.api.entity;

import java.io.Serializable;

/**回应信息实体(回应给仓配系统等)*/
@SuppressWarnings("serial")
public class ResultEntity implements Serializable{
	
	/**返回编码(0是正确，其余均错误)*/
	public int code;
	/**返回信息*/
	public String resultMsg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}
