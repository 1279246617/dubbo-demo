package com.coe.message.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coe.message.entity.LoginUser;
import com.coe.message.entity.Message;
import com.coe.message.entity.QueryParamsEntity;
import com.coe.message.service.IMessageService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/message")
/**报文信息控制类*/
public class MessageController {
	@Autowired
	private IMessageService messageService;

	/**页面跳转*/
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

	/***
	 * 分页模糊查询
	 * @param queryParamsJson 参数，JSON格式字符串
	 * @param page 页面
	 * @param rows 每页显示记录数
	 * @return Map
	 */
	@RequestMapping("/queryForPage")
	@ResponseBody
	public Map<String, Object> queryDataListPage(String queryParamsJson, int page, int rows) {
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
		List<Map<String, Object>> msgMapList = messageService.queryListPageForVague(message, paramsEntity);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", msgMapList);
		resultMap.put("total", messageService.countForVague(message, paramsEntity));
		return resultMap;
	}

	/**
	 * 批量停止或继续发送报文设置
	 * @param idArray  多个id，逗号隔开
	 * @param action  操作，1:停止，2:继续
	 * @return Map
	 */
	@RequestMapping("/stopOrContinueToSend")
	@ResponseBody
	public Map<String, Object> stopOrContinueToSend(String idArray, int action) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] idArrayStr = idArray.split(",");
		List<Long> idList = new ArrayList<Long>();
		for (String idStr : idArrayStr) {
			Long id = Long.parseLong(idStr);
			idList.add(id);
		}
		int records  = 0;
		if(action==1){
			//isValid==1,设置无效
			records = messageService.stopToSend(idList);
		}
		if(action==2){
			//isValid==0,设置有效
			records = messageService.continueToSend(idList);
		}
		resultMap.put("records",records);
		return resultMap;
	}
}
