package com.coe.wms.controller.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 仓配API
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/api/storage")
public class Storage {

	/**
	 * 创建仓配API
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
	public String createOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		InputStream is = request.getInputStream();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		System.out.println(request.getCharacterEncoding());
		
		String requestBody = "";
		String line = null;
		while ((line = br.readLine()) != null) {
			requestBody += line;
		}
		
		System.out.println("createOrder 收到:" + requestBody);
		
		return "干"+requestBody;
	}
}
