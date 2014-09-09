package com.coe.wms.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 通用工具类，包括了签名工具、url拼装
 */
public final class CommonUtil {
	
	/**
	 * SF API签名方法 按照参数字母从小到大排序加上
	 * 
	 * @param params
	 *            参数
	 * @param appSecretKey
	 *            秘银
	 * @return
	 */
	public static String signature(Map<String, String> params, String postConstant, String appSecretKey) {
		List<String> paramValueList = new ArrayList<String>();
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				paramValueList.add(entry.getKey() + "=" + entry.getValue());
			}
		}
		// 按参数名字母小-大排序
		Collections.sort(paramValueList);
		String waitSignatureString = "";
		for (String str : paramValueList) {
			waitSignatureString += str;
		}
		if (StringUtil.isNotNull(postConstant)) {
			waitSignatureString += postConstant;
		}
		if (StringUtil.isNotNull(appSecretKey)) {
			waitSignatureString += appSecretKey;
		}
		return StringUtil.md5(waitSignatureString);
	}

	/**
	 * 拼接获取完整的url
	 * 
	 * @param url
	 *            请求uri
	 * @param params
	 *            请求参数
	 * @return
	 */
	public static String getWholeUrl(String url, Map<String, String> params) {
		if (url == null) {
			return null;
		}
		if (params == null) {
			return url;
		}
		Set<Map.Entry<String, String>> set = params.entrySet();
		if (set.size() <= 0) {
			return url;
		}
		url += "?";
		Iterator<Map.Entry<String, String>> it = set.iterator();
		if (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String param = entry.getKey() + "=" + entry.getValue();
			url += param;
		}
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String param = entry.getKey() + "=" + entry.getValue();
			url += "&" + param;
		}
		return url;
	}
}
