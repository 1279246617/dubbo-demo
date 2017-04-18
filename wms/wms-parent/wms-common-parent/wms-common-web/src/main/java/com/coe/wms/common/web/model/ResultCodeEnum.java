package com.coe.wms.common.web.model;

public enum ResultCodeEnum {
	/** 成功 */
	SUCCESS(0, ""),
	/** 失败 */
	FAILURE(1, "system.ex.msg.prefix"),
	/** 未登录/登录超时 */
	SESSION_TIMEOUT(2, "system.session.timeout");

	/** 状态码 */
	private int code;

	/** 信息 */
	private String key;

	private ResultCodeEnum(int code, String key){
        this.code = code;
        this.key = key;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
