package com.aihangxunxi.aitalk.restapi.controller;

import com.aihangxunxi.aitalk.restapi.context.AihangPrincipal;
import com.aihangxunxi.aitalk.restapi.service.FriendService;
import com.aihangxunxi.aitalk.restapi.service.UserService;
import com.aihangxunxi.aitalk.storage.model.Friend;
import com.aihangxunxi.aitalk.storage.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

	@Resource
	private FriendService friendService;

	@GetMapping("/friends")
	public ResponseEntity<ModelMap> getFrientList() {
		ModelMap map = new ModelMap();
		map.put("friendList", friendService.getFrientList());
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	@PutMapping("/alias")
	public ResponseEntity<ModelMap> updAlias(@RequestBody Friend friend) {
		ModelMap map = new ModelMap();
		map.put("friend", friendService.updAlias(friend));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	@PutMapping("/mute")
	public ResponseEntity<ModelMap> updMute(@RequestBody Friend friend) {
		ModelMap map = new ModelMap();
		map.put("friend", friendService.updMute(friend));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	@PutMapping("/top")
	public ResponseEntity<ModelMap> updStickOnTop(@RequestBody Friend friend) {
		ModelMap map = new ModelMap();
		map.put("friend", friendService.updStickOnTop(friend));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 获取黑名单
	 * @param aihangPrincipal
	 * @return
	 */
	@GetMapping("/blocked")
	public ResponseEntity<ModelMap> getBlocked(AihangPrincipal aihangPrincipal) {
		ModelMap map = new ModelMap();
		map.put("blocks", friendService.getBlocked(aihangPrincipal.getUserId()));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 添加/删除 黑名单
	 * @param friend
	 * @return
	 */
	@PutMapping("/blocked")
	public ResponseEntity<ModelMap> updBlocked(@RequestBody Friend friend) {
		ModelMap map = new ModelMap();
		map.put("friend", friendService.updBlocked(friend));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 删除好友
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<ModelMap> delFriend(@PathVariable String id) {
		ModelMap map = new ModelMap();
		map.put("res", friendService.delFriend(id));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}
