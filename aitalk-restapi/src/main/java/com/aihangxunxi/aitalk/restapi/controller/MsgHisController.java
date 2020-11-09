package com.aihangxunxi.aitalk.restapi.controller;

import com.aihangxunxi.aitalk.restapi.service.MsgHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author guoyongsheng Data: 2020/11/6
 * @Version 3.0
 */
@RestController
@RequestMapping("/msgHis")
public class MsgHisController {

	@Autowired
	private MsgHisService msgHisService;

	// 登录后获取未读取的离线消息
	@GetMapping("getOfflineMsg/{id}")
	public ResponseEntity<ModelMap> getOfflineMsg(@PathVariable("id") String id) {
		ModelMap map = new ModelMap();
		map.put("msgList", msgHisService.getOfflineMsg(id));
		return ResponseEntity.status(HttpStatus.OK).body(map);

	}

	// 登录成功后获取此人离线消息的最后一条聊天记录（未使用）
	@GetMapping("getOfflineLastMsg/{id}")
	public ResponseEntity<ModelMap> getOfflineLastMsg(@PathVariable("id") String id) {
		ModelMap map = new ModelMap();
		map.put("lastMsgList", msgHisService.getOfflineLastMsg(id));
		return ResponseEntity.status(HttpStatus.OK).body(map);

	}

}
