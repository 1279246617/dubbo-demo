package com.coe.wms.common.web.interceptor;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.coe.wms.common.constants.Charsets;
import com.coe.wms.common.model.Session;
import com.coe.wms.common.utils.CookieUtil;
import com.coe.wms.common.utils.DateUtil;
import com.coe.wms.common.utils.StringUtil;
import com.coe.wms.common.web.session.CacheSession;
import com.coe.wms.common.web.session.CookieHolder;

public class SessionInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(SessionInterceptor.class);

	/**
	 * 不拦截的url 正则
	 */
	private String unInterceptUrls;

	private CacheSession session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setCharacterEncoding(Charsets.UTF_8.name());
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		// ------------------------------------以下设置用于跨域------------------------------
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		// 允许请求的类型
		response.setHeader("Access-Control-Allow-Methods", "*");
		// 允许请求头
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");

		response.setHeader("Access-Control-Allow-Credentials", "true");
		// withCredentials: true
		// Access-Control-Allow-Credentials: true
		// ------------------------------------设置跨域结束

		// 访问路径
		String url = request.getServletPath();
		// 不拦截的url
		if (url.matches(unInterceptUrls)) {
			return true;
		}

		// ---------------------------------cookie
		// 获取cookie
		String sessionId = CookieUtil.getValue(request, Session.SESSION_ID_KEY);

		// 获取cookie失败
		if (StringUtil.isEmpty(sessionId)) {
			// 重新设置cookie
			sessionId = UUID.randomUUID().toString().replace("-", "");
		}
		CookieHolder.setSessionId(sessionId);

		CookieUtil.addCookie(response, Session.SESSION_ID_KEY, sessionId, session.getSessionTimeOut() * 60);
		// 线上使用 用于共享cookie
		CookieUtil.addCookie(response, Session.SESSION_ID_KEY, CookieHolder.getSessionId(), "coewms.com", false, session.getSessionTimeOut() * 60,
				"/", false);

		// --------------------------------cookie结束

		String isLogin = session.getAttr(Session.USER_IDENTIFICATION);
		// 登录校验
		if (isLogin == null) {
			response.getWriter().print("{\"code\":2,\"msg\":\"会话已失效,请重新登录\"}");
			return false;
		}

		// 刷新session 在redis的有效时间
		String refreshTimeStr = session.getAttr(Session.REFRESH_TIME);
		if (refreshTimeStr == null)
			refreshTimeStr = "0";
		Long refreshTime = Long.parseLong(refreshTimeStr);
		// 上次刷新时间和当前时间相差秒数
		Long diffSeconds = DateUtil.getDiffSeconds(new Date(), new Date(refreshTime));
		if (diffSeconds > Session.SESSION_REFRESH_SECONDS) {
			session.setAttr(Session.REFRESH_TIME, String.valueOf(System.currentTimeMillis()));
			session.refresh();
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO 线上使用 CookieUtil.addCookie(response, Session.SESSION_ID_KEY, CookieHolder.getSessionId(), "coewms.com", true,
		// session.getSessionTimeOut()*60, "/", false);
	}

	public void setUnInterceptUrls(String unInterceptUrls) {
		this.unInterceptUrls = unInterceptUrls;
	}

	public CacheSession getSession() {
		return session;
	}

	public void setSession(CacheSession session) {
		this.session = session;
	}
}