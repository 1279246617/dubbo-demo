package com.coe.message.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.coe.message.entity.MessageResponseNewest;
import com.coe.message.entity.MessageResponseNewestWithBLOBs;
import com.coe.message.service.IMsgRespNewService;

/**最新一次响应信息控制类*/
@RequestMapping("/msgRespNewest")
@Controller
public class MsgRespNewestController {
	@Autowired
	private IMsgRespNewService msgRespNewService;

	@RequestMapping("/getMsgRespNewest")
	@ResponseBody
	public Map<String, Object> getMsgRespNewest(MessageResponseNewest msgReqNewest) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String,Object>> msgRespNewestList = msgRespNewService.selectByExampleBackMap(msgReqNewest);
		resultMap.put("msgRespNewestList",msgRespNewestList);
		return resultMap;
	}
}
