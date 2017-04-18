package com.coe.wms.common.web;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 控制器父类
 * 
 * @ClassName: AbstractController
 * @author yechao
 * @date 2017年2月28日 下午5:58:27
 * @Description: TODO
 */
public abstract class AbstractController {

	protected HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	protected HttpServletResponse getHttpServletResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	protected Session getSession() {
		HttpServletRequest request = getHttpServletRequest();
		HttpSession session = request.getSession();
		return (Session) session;
	}
	
	/**
	 * 读取请求体内容返回string
	 * 
	 * @return
	 * @throws IOException
	 */
	protected String readRequest() throws IOException {
		HttpServletRequest request = getHttpServletRequest();

		BufferedReader br = request.getReader();
		try {
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}
}
