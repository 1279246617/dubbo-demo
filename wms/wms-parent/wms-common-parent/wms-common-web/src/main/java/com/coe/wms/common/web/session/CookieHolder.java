package com.coe.wms.common.web.session;

public class CookieHolder {

   private static final ThreadLocal<String> sessionId = new ThreadLocal<String>();
	
	public static void setSessionId (String sessionIdStr){
		sessionId.set(sessionIdStr);
	}
	
	public static String getSessionId(){
		return sessionId.get();
	}
}
