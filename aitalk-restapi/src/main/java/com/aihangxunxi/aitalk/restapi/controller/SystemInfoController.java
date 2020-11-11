package com.aihangxunxi.aitalk.restapi.controller;

import com.aihangxunxi.aitalk.restapi.service.SystemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author: suguodong Date: 2020/11/10 15:42
 * @Version: 3.0
 */
@Controller
@RequestMapping("systemInfo")
public class SystemInfoController {

	@Resource
	private SystemInfoService systeminfoService;

	// 登录后获取未读取的离线消息
	@GetMapping("getOfflineMsg/{id}")
	public ResponseEntity<ModelMap> getOfflineMsg(@PathVariable("id") String id) {
		ModelMap map = new ModelMap();
		map.put("msgList", systeminfoService.getSystemMsg(id));
		return ResponseEntity.status(HttpStatus.OK).body(map);

	}

}
