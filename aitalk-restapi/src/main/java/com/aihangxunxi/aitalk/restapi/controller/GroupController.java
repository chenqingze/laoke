package com.aihangxunxi.aitalk.restapi.controller;

import com.aihangxunxi.aitalk.restapi.context.AihangPrincipal;
import com.aihangxunxi.aitalk.restapi.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
	@GetMapping("/in")
	public ResponseEntity<ModelMap> queryUserInGroup(@RequestParam("groupId") String groupId,
			AihangPrincipal aihangPrincipal) {
		ModelMap map = new ModelMap();
		map.put("in", groupService.queryUserInGroup(groupId, aihangPrincipal.getUserId()));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 根据群号判断用户是否在群中
	@GetMapping("/in/{groupNo}")
	public ResponseEntity<ModelMap> queryUserInGroupByNo(@PathVariable("groupNo") String groupNo,
			AihangPrincipal aihangPrincipal) {
		ModelMap map = new ModelMap();
		boolean in = groupService.queryUserInGroupByNo(groupNo, aihangPrincipal.getUserId());
		map.put("in", in);
		if (in) {
			map.put("groupId", groupService.queryGroupInfoByGroupNo(groupNo).getId());
			map.put("groupName", groupService.queryGroupInfoByGroupNo(groupNo).getName());
		}
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 搜索群
	@GetMapping("/no/{groupNo}")
	public ResponseEntity<ModelMap> queryGroupByNo(@PathVariable("groupNo") String groupNo) {
		ModelMap map = new ModelMap();
		map.put("group", groupService.queryGroupInfoByGroupNo(groupNo));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 获取最大成员数量
	@GetMapping("/max-count")
	public ResponseEntity<ModelMap> queryGroupMaxMemberCount() {
		ModelMap map = new ModelMap();
		map.put("max", groupService.queryGroupMaxMemberCount());
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 获取好友列表
	@GetMapping("/friends")
	public ResponseEntity<ModelMap> queryUsersFriend(AihangPrincipal aihangPrincipal) {
		ModelMap map = new ModelMap();
		map.put("friends", groupService.queryUsersFriend(aihangPrincipal.getUserId()));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 获取用户已创建的群数量是否合法
	@GetMapping("/check/max")
	public ResponseEntity<ModelMap> checkUsersMaxGroupCount(AihangPrincipal aihangPrincipal) {
		ModelMap map = new ModelMap();
		map.put("ok", groupService.checkUserMaxGroupCount(aihangPrincipal.getUserId()));
		return ResponseEntity.status(HttpStatus.OK).body(map);

	}

	// 获取群设置信息
	@GetMapping("/group-setting")
	public ResponseEntity<ModelMap> queryGroupSetting(@RequestParam("groupId") String groupId,
			AihangPrincipal aihangPrincipal) {
		ModelMap map = new ModelMap();
		map.put("info", groupService.queryGroupSettingInfo(groupId, aihangPrincipal.getUserId()));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 获取群成员列表
	@GetMapping("/members")
	public ResponseEntity<ModelMap> queryGroupMembers(@RequestParam("id") String id) {
		ModelMap map = new ModelMap();
		map.put("list", groupService.queryGroupMembers(id));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 修改群内用户昵称
	@PutMapping("/nickname")
	public ResponseEntity<ModelMap> updateUserGroupNickname(@RequestParam("userId") Long userId,
			@RequestParam("nickname") String nickname) {
		ModelMap map = new ModelMap();
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}
