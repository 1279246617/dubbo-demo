package com.coe.message.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**json和map互转工具类(所需包jackson-core，jackson-databind，jackson-annotations)*/
public class JsonMapUtil {
	/**
	 * json格式字符串转Map集合
	 * @param jsonStr json格式字符串
	 * @return Map集合
	 */
	public static Map<String, Object> jsonToMap(String jsonStr) {
		Map<String, Object> resultMap = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			resultMap = mapper.readValue(jsonStr, new TypeReference<HashMap<String, Object>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * Map转Json
	 * @param paramsMap参数集合
	 * @return json格式字符串
	 */
	public static String mapToJson(Map<String, Object> paramsMap) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(paramsMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

}
