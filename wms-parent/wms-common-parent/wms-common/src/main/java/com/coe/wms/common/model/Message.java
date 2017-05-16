package com.coe.wms.common.model;

import java.io.Serializable;

/**
 * 通用消息封装
 * 
 * @ClassName: Message
 * @author yechao
 * @date 2017年5月3日 下午2:03:14
 * @Description: TODO
 */
public class Message implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 4081619286503989887L;
	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 消息代码 , 由使用者自由发挥
	 */
	private String code;
	/**
	 * 消息内容体 , 由使用者自由发挥
	 */
	private String msg;
	/**
	 * 扩展
	 */
	private Object extend;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getExtend() {
		return extend;
	}

	public void setExtend(Object extend) {
		this.extend = extend;
	}
}
