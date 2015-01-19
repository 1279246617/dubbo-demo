package com.coe.wms.pojo.api2.warehouse;

public class ErrorCode {

	// 系统错误码
	public static final String S01 = "非法的XML/JSON";
	public static final String S01_CODE = "S01";

	public static final String S02 = "非法的数字签名";
	public static final String S02_CODE = "S02";

	public static final String S03 = "非法的物流公司/仓库公司";
	public static final String S03_CODE = "S03";

	public static final String S04 = "非法的通知类型";
	public static final String S04_CODE = "S04";

	public static final String S05 = "非法的通知内容";
	public static final String S05_CODE = "S05";

	public static final String S06 = "网络超时，请重试";
	public static final String S06_CODE = "S06";

	public static final String S07 = "系统异常，请重试";
	public static final String S07_CODE = "S07";

	public static final String S08 = "HTTP 状态异常（非200）";
	public static final String S08_CODE = "S08";

	public static final String S09 = "返回报文为空";
	public static final String S09_CODE = "S09";

	public static final String S10 = "找不到对应网关";
	public static final String S10_CODE = "S10";

	public static final String S11 = "非法网关信息";
	public static final String S11_CODE = "S11";

	public static final String S12 = "非法请求参数";
	public static final String S12_CODE = "S12";

	public static final String S13 = "业务服务异常";
	public static final String S13_CODE = "S13";

	// 业务错误
	public static final String B0001 = "查询失败";
	public static final String B0001_CODE = "B0001";

	public static final String B0002 = "未知业务错误";
	public static final String B0002_CODE = "B0002";

	public static final String B0003 = "仓库编码不正确";
	public static final String B0003_CODE = "B0003";

	public static final String B0004 = "地址代码不正确";
	public static final String B0004_CODE = "B0004";

	public static final String B0005 = "订单不存在";
	public static final String B0005_CODE = "B005";

	public static final String B0006 = "订单已关闭";
	public static final String B0006_CODE = "B0006";

	public static final String B0007 = "转运商ID不正确";
	public static final String B0007_CODE = "B0007";

	public static final String B0008 = "用户查询失败";
	public static final String B0008_CODE = "B0008";

	public static final String B0009 = "用户身份证信息不存在";
	public static final String B0009_CODE = "B0009";

	public static final String B0010 = "无法识别eventTarget";
	public static final String B0010_CODE = "B0010";

	public static final String B0011 = "无法识别的仓库类型";
	public static final String B0011_CODE = "B0011";

	public static final String B0012 = "无法识别的logistics code";
	public static final String B0012_CODE = "B0012";

	public static final String B0013 = "当前状态不允许获取运费";
	public static final String B0013_CODE = "B0013";

	public static final String B0014 = "当前状态不能付款";
	public static final String B0014_CODE = "B0014";

	// 状态错误码
	public static final String B0100 = "当前订单状态不允许变更";
	public static final String B0100_CODE = "B0100";

	public static final String B0101 = "订单状态已关闭";
	public static final String B0101_CODE = "B0101";

	public static final String B0102 = "订单状态已出库";
	public static final String B0102_CODE = "B0102";

	public static final String B0103 = "订单状态已发货";
	public static final String B0103_CODE = "B0103";

	public static final String B0104 = "订单状态已收货";
	public static final String B0104_CODE = "B0104";

	public static final String B0200 = "订单已存在";
	public static final String B0200_CODE = "B0200";

	public static final String UNKNOWN = "未知错误";
	public static final String UNKNOWN_CODE = "UNKNOWN";

}
