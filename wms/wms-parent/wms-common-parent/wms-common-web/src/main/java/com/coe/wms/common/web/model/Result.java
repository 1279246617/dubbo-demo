package com.coe.wms.common.web.model;

public class Result {

	public static final String RESP_IS_DISPLAY_VERIFYCODE = "isDisplayVerifyCode";

	/** 状态码, 正常0, 失败1, 未登录2, 个位数是保留状态码, 其他业务相关状态码10以上 */
	private int code = ResultCodeEnum.SUCCESS.getCode();

	/** 消息 */
	private String msg;

	/** 业务数据 */
	private Object data;

	public Result() {
	}

	/**
	 * status 默认成功 1
	 */
	public Result(String msg) {
		this.msg = msg;
	}

	/**
	 * status 默认成功 1
	 */
	public Result(Object data) {
		this.data = data;
	}

	public Result(int code, String msg) {
		this(msg);
		this.code = code;
	}

	public Result(int code, String msg, Object data) {
		this(msg);
		this.code = code;
		this.data = data;
	}

	/**
	 * 构建成功结果模型
	 * 
	 * @param data
	 * @return
	 */
	public static Result success(Object data) {
		return new Result(data);
	}
    /**
     * 构建成功结果模型,无业务数据返回
     * @param msg
     * @return
     */
    public static Result success(String msg){
    	return new Result(ResultCodeEnum.SUCCESS.getCode(),msg);
    }
	/**
	 * 构建错误结果模型
	 * 
	 * @param msg
	 * @return
	 */
	public static Result error(String msg) {
		return new Result(ResultCodeEnum.FAILURE.getCode(), msg);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
