package com.coe.wms.util;

import org.apache.poi.hssf.record.formula.functions.T;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

	private static GsonBuilder builder = new GsonBuilder();

	public static String toJson(Object object) {
		Gson gson = builder.create();
		return gson.toJson(object);
	}

	public static Object toObject(String json, Class c) {
		Gson gson = builder.create();
		return gson.fromJson(json, c);
	}
}
