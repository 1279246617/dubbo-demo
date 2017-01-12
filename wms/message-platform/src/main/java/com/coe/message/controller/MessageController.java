package com.coe.message.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coe.message.entity.LoginUser;

@Controller
@RequestMapping("/message")
/**报文信息控制类*/
public class MessageController {

	@RequestMapping("/toMsgList")
	public String toMsgList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
		if (loginUser != null) {
			return "/page/msgList.jsp";
		} else {
			return "/page/login.jsp";
		}
	}
}
