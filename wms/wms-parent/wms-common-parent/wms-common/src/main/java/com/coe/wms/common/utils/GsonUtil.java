package com.coe.wms.common.utils;

import java.lang.reflect.Type;

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
	public static <T> T toObject(String json, Type typeOfT){
		Gson gson = builder.create();
	    return 	gson.fromJson(json, typeOfT);
	}
	
	
	
}
