package com.coe.wms.common.web.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.coe.wms.common.constants.Charsets;
import com.coe.wms.common.core.cache.redis.RedisClient;
import com.coe.wms.common.model.Session;
import com.coe.wms.common.utils.CookieUtil;
import com.coe.wms.common.utils.DateUtil;
import com.coe.wms.common.utils.StringUtil;

public class SessionInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(SessionInterceptor.class);

	/**
	 * 不拦截的url 正则
	 */
	private String unInterceptUrls;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setCharacterEncoding(Charsets.UTF_8.name());
		response.setHeader("Content-type", "application/json;charset=UTF-8");

		// 访问路径
		String url = request.getServletPath();
		// 不拦截的url
		if (url.matches(unInterceptUrls)) {
			return true;
		}

		// 获取cookie
		String sessionId = CookieUtil.getValue(request, Session.SESSION_ID_KEY);
		String sessionToken = CookieUtil.getValue(request, Session.SESSION_TOKEN_KEY);

		// 获取cookie失败
		if (StringUtil.isEmpty(sessionId) || StringUtil.isEmpty(sessionToken)) {
			response.getWriter().print("{\"code\":2,\"msg\":\"获取cookie失败\"}");
			// 注意servlet的输出流不需要手工关闭
			return false;
		}

		// 从redis 获取session
		Session session = (Session) RedisClient.getInstance().getObject(Session.getIdRedisKey(sessionId));
		if (session == null) {
			response.getWriter().print("{\"code\":2,\"msg\":\"会话已失效,请重新登录\"}");
			// 注意servlet的输出流不需要手工关闭
			return false;
		}

		// 刷新session 在redis的有效时间

		// 上次刷新时间和当前时间相差秒数
		Long diffSeconds = DateUtil.getDiffSeconds(new Date(), session.getRefreshTime());
		if (diffSeconds > Session.SESSION_REFRESH_SECONDS) {
			session.setRefreshTime(new Date());
			RedisClient.getInstance().setObject(Session.getIdRedisKey(sessionId), session, Session.SESSION_MAX_AGE);
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	public void setUnInterceptUrls(String unInterceptUrls) {
		this.unInterceptUrls = unInterceptUrls;
	}
}