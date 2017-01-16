package com.coe.message.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.coe.message.entity.MessageRequest;
import com.coe.message.entity.MessageRequestWithBLOBs;
import com.coe.message.service.IMessageRequestService;

@RequestMapping("/messageRequest")
@Controller
public class MessageRequstController {
	@Autowired
	private IMessageRequestService msgReqService;

	@RequestMapping("/getMsgReqByExample")
	@ResponseBody
	public Map<String, Object> getMsgReqByExample(MessageRequest msgReq) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> msgReqMapList = msgReqService.selectByExampleBackMapList(msgReq);
		resultMap.put("msgReqMapList",msgReqMapList);
		return resultMap;
	}
}
