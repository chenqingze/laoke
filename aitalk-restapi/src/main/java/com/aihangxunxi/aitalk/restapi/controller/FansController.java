package com.aihangxunxi.aitalk.restapi.controller;

import com.aihangxunxi.aitalk.restapi.context.AihangPrincipal;
import com.aihangxunxi.aitalk.restapi.service.FansService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/fans")
public class FansController {

	@Resource
	private FansService fansService;

	/**
	 * 获取粉丝列表
	 * @param aihangPrincipal
	 * @return
	 */
	@GetMapping("query")
	public ResponseEntity<ModelMap> queryFansList(AihangPrincipal aihangPrincipal) {
		ModelMap map = new ModelMap();
		map.put("list", fansService.queryFans(aihangPrincipal.getUserId()));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 关注
	 * @param storeId
	 * @param userId
	 * @return
	 */
	@PostMapping("follow/{storeId}/{userId}")
	public ResponseEntity<ModelMap> followStore(@PathVariable("storeId") Long storeId,
			@PathVariable("userId") Long userId) {
		ModelMap map = new ModelMap();
		map.put("success", fansService.followStore(storeId, userId));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 取消关注
	 * @param storeId
	 * @param userId
	 * @return
	 */
	@PostMapping("cancelFollow/{storeId}/{userId}")
	public ResponseEntity<ModelMap> cancelFollow(@PathVariable("storeId") Long storeId,
			@PathVariable("userId") Long userId) {
		ModelMap map = new ModelMap();
		map.put("success", fansService.cancelFollow(storeId, userId));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 获取是否已经关注
	 * @param storeId
	 * @param userId
	 * @return
	 */
	@GetMapping("followed/{storeId}/{userId}")
	public ResponseEntity<ModelMap> followed(@PathVariable("storeId") Long storeId,
			@PathVariable("userId") Long userId) {
		ModelMap map = new ModelMap();
		map.put("follow", fansService.followed(storeId, userId));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}
