package com.coe.message.common.servlet;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.coe.message.common.LoginCodeImageUtil;

/**登录验图片证码servlet*/
public class LoginCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginCodeImageUtil codeUtil = new LoginCodeImageUtil();
		codeUtil.generatorCodeImg();
		String code = codeUtil.getCode();
		HttpSession session = req.getSession();
		session.setAttribute("loginCode", code);
		resp.setContentType("image/jpeg");
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		ImageIO.write(codeUtil.getBufferedImg(), "jpg", resp.getOutputStream());
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
