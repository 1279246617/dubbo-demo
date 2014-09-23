package com.coe.wms.pojo.api.warehouse;

public class ErrorCode {

	// 系统错误码
	public static final String S01 = "非法的XML/JSON";

	public static final String S02 = "非法的数字签名";

	public static final String S03 = "非法的物流公司/仓库公司";

	public static final String S04 = "非法的通知类型";

	public static final String S05 = "非法的通知内容";

	public static final String S06 = "网络超时，请重试";

	public static final String S07 = "系统异常，请重试";

	public static final String S08 = "HTTP 状态异常（非200）";

	public static final String S09 = "返回报文为空";

	public static final String S10 = "找不到对应网关";

	public static final String S11 = "非法网关信息";

	public static final String S12 = "非法请求参数";

	public static final String S13 = "业务服务异常";

	// 业务错误
	public static final String B0001 = "查询失败";

	public static final String B0002 = "未知业务错误";

	public static final String B0003 = "仓库编码不正确";

	public static final String B0004 = "地址代码不正确";

	public static final String B0005 = "订单不存在";

	public static final String B0006 = "订单已关闭";

	public static final String B0007 = "转运商ID不正确";

	public static final String B0008 = "用户查询失败";

	public static final String B0009 = "用户身份证信息不存在";

	public static final String B0010 = "无法识别eventTarget";

	public static final String B0011 = "无法识别的仓库类型";

	public static final String B0012 = "无法识别的logistics code";

	public static final String B0013 = "当前状态不允许获取运费";

	public static final String B0014 = "当前状态不能付款";

	// 状态错误码
	public static final String B0100 = "当前订单状态不允许变更";

	public static final String B0101 = "订单状态已关闭";

	public static final String B0102 = "订单状态已出库";

	public static final String B0103 = "订单状态已发货";

	public static final String B0104 = "订单状态已收货";

	public static final String UNKNOWN = "未知错误";
}
