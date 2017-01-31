package com.coe.wms.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

	private static GsonBuilder builder = new GsonBuilder();

	public static String toJson(Object object) {
		Gson gson = builder.create();
		return gson.toJson(object);
	}

	public static <T> T toObject(String json, Class<T> c) {
		Gson gson = builder.create();
		return gson.fromJson(json, c);
	}
}
