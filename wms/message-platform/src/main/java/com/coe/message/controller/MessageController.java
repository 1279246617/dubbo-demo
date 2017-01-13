package com.coe.message.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.coe.message.entity.LoginUser;
import com.coe.message.entity.Message;
import com.coe.message.entity.QueryParamsEntity;
import com.coe.message.service.IMessageService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/message")
/**报文信息控制类*/
public class MessageController {
	@Autowired
	private IMessageService messageService;

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

	public Map<String, Object> queryDataListPage(String queryParamsJson,int page,int rows) {
		JSONObject paramsJsonObj = JSONObject.fromObject(queryParamsJson);
		String requestId = paramsJsonObj.getString("requestId");
		String keyword1 = paramsJsonObj.getString("keyword1");
		String keyword2 = paramsJsonObj.getString("keyword2");
		String keyword3 = paramsJsonObj.getString("keyword3");
		String startTime = paramsJsonObj.getString("startTime");
		String endTime = paramsJsonObj.getString("endTime");
		Message message = new Message();
		message.setRequestId(requestId);
		message.setKeyword1(keyword1);
		message.setKeyword2(keyword2);
		message.setKeyword3(keyword3);
		QueryParamsEntity paramsEntity = new QueryParamsEntity();
		paramsEntity.setStartTime(startTime);
		paramsEntity.setEndTime(endTime);
		paramsEntity.setPage(page);
		paramsEntity.setRows(rows);
		messageService.queryListPageForVague(message,paramsEntity);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		return resultMap;
	}
}
