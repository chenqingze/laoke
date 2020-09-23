package com.aihangxunxi.aitalk.restapi.controller;

import com.aihangxunxi.aitalk.restapi.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("group")
public class GroupController {

	@Resource
	private GroupService groupService;
	// 获取群信息
	@GetMapping("/{id}")
	public ResponseEntity<ModelMap> queryGroupInfo(@PathVariable("id") String id) {
		ModelMap map = new ModelMap();
		map.put("group", groupService.queryGroupInfo(id));
		return ResponseEntity.status(HttpStatus.OK).body(map);

	}
	// 判断用户是否在群里
	@GetMapping("/in}")
	public ResponseEntity<ModelMap> queryUserInGroup(@RequestParam("groupId")String groupId){
		ModelMap map = new ModelMap();
		map.put("in", groupService.queryUserInGroup(groupId,123l));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}
