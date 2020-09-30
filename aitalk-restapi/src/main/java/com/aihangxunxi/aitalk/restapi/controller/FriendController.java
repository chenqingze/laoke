package com.aihangxunxi.aitalk.restapi.controller;

import com.aihangxunxi.aitalk.restapi.service.FriendService;
import com.aihangxunxi.aitalk.restapi.service.UserService;
import com.aihangxunxi.aitalk.storage.model.Friend;
import com.aihangxunxi.aitalk.storage.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/friend")
public class FriendController {

	@Resource
	private FriendService friendService;

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

	@PutMapping("/blocked")
	public ResponseEntity<ModelMap> updBlocked(@RequestBody Friend friend) {
		ModelMap map = new ModelMap();
		map.put("friend", friendService.updBlocked(friend));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}
