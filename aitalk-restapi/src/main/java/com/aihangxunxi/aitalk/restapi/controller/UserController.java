package com.aihangxunxi.aitalk.restapi.controller;

import com.aihangxunxi.aitalk.restapi.context.AihangPrincipal;
import com.aihangxunxi.aitalk.restapi.service.UserService;
import com.aihangxunxi.aitalk.storage.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("user")
public class UserController {

	@Resource
	private UserService userService;

	// 新用户注册
	@PostMapping("save")
	public ResponseEntity<ModelMap> saveUser(@RequestBody User user) {
		ModelMap map = new ModelMap();
		map.put("success", userService.saveUser(user));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 更新昵称
	@PutMapping("update/nickname")
	public ResponseEntity<ModelMap> updateUserNickname(@RequestParam("userId") Long userId,
			@RequestParam("nickname") String nickname) {
		ModelMap map = new ModelMap();
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 根据用户查询
	@GetMapping("/{userId}")
	public ResponseEntity<ModelMap> getUserById(@PathVariable("userId") Long userId) {
		ModelMap map = new ModelMap();
		map.put("user", userService.getUserById(userId));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 绑定用户设备
	@PutMapping("/bind")
	public ResponseEntity<ModelMap> updateUserDevice(@RequestParam("deviceCode") String deviceCode,
			AihangPrincipal aihangPrincipal, @RequestParam("deviceType") String deviceType) {
		ModelMap map = new ModelMap();
		map.put("user", userService.updateDeviceInfo(aihangPrincipal.getUserId(), deviceCode, deviceType));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 根据用户查询
	@GetMapping("getUser/{userId}")
	public ResponseEntity<ModelMap> getUserByUserId(@PathVariable("userId") Long userId) {
		ModelMap map = new ModelMap();
		map.put("user", userService.getUserByUserId(userId));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// 更具id 获取用户信息
	@GetMapping("getUserById/{id}")
	public ResponseEntity<ModelMap> getUserById(@PathVariable("id") String id) {
		ModelMap map = new ModelMap();
		map.put("user", userService.getUserByObjectId(id));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 注册
	 * @return
	 */
	@PostMapping("reg")
	public ResponseEntity<ModelMap> regUser(@RequestParam("userId") Long userId,
			@RequestParam("nickname") String nickname, @RequestParam("header") String header) {
		ModelMap map = new ModelMap();
		map.put("success", userService.regUser(userId, nickname, header));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 注册店铺
	 * @return
	 */
	@PostMapping("store-reg")
	public ResponseEntity<ModelMap> regStoreUser(@RequestParam("userId") Long userId,
			@RequestParam("nickname") String nickname, @RequestParam("header") String header) {
		ModelMap map = new ModelMap();
		map.put("success", userService.regStoreUser(userId, nickname, header));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 注销
	 * @param userId
	 * @return
	 */
	@DeleteMapping("cancel")
	public ResponseEntity<ModelMap> cancelUser(@RequestParam("userId") Long userId) {
		ModelMap map = new ModelMap();
		map.put("success", userService.cancelUser(userId));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
	@GetMapping("query/{userType}/{userId}")
	public ResponseEntity<ModelMap> queryUser(@PathVariable("userType") String userType,
			@PathVariable("userId") Long userId) {

		ModelMap map = new ModelMap();
		map.put("user", userService.queryUserByType(userType, userId));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 清空用户jpush code
	 * @param userId
	 * @return
	 */
	@PutMapping("cancel-bind/{userId}")
	public ResponseEntity<ModelMap> cancelBindJPush(@PathVariable("userId") Long userId) {
		ModelMap map = new ModelMap();
		map.put("success", userService.cancelBindJPush(userId));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 冻结用户
	 * @param userId
	 * @return
	 */
	@PutMapping("freeze")
	public ResponseEntity<ModelMap> freezeUser(@RequestParam("userId") Long userId,
			@RequestParam("userType") String userType) {
		ModelMap map = new ModelMap();
		map.put("success", userService.freezeUser(userId, userType));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 解冻用户
	 * @param userId
	 * @return
	 */
	@PutMapping("effective")
	public ResponseEntity<ModelMap> effectiveUser(@RequestParam("userId") Long userId,
			@RequestParam("userType") String userType) {
		ModelMap map = new ModelMap();
		map.put("success", userService.effective(userId, userType));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 查询用户是否被冻结 UserIsFreeze
	 */
	@GetMapping("getUserIsFreeze")
	public ResponseEntity<ModelMap> getUserIsFreeze(@RequestParam("userId") Long userId,
			@RequestParam("userType") String userType) {
		ModelMap map = new ModelMap();
		map.put("success", userService.getUserIsFreeze(userId, userType));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 导入数据
	 * @return
	 */
	@GetMapping("import")
	public ResponseEntity<ModelMap> importUsers() {
		ModelMap map = new ModelMap();
		userService.importUsers();
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 开启免打扰
	 * @param userId
	 * @param currentUser
	 * @return
	 */
	@PostMapping("disturb/{userId}/{currentUser}")
	public ResponseEntity<ModelMap> disturb(@PathVariable("userId") String userId,
			@PathVariable("currentUser") String currentUser) {
		ModelMap map = new ModelMap();
		map.put("success", userService.disturb(userId, currentUser));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 取消免打扰
	 * @param userId
	 * @param currentUser
	 * @return
	 */
	@DeleteMapping("cancel-disturb/{userId}/{currentUser}")
	public ResponseEntity<ModelMap> cancelDisturb(@PathVariable("userId") String userId,
			@PathVariable("currentUser") String currentUser) {
		ModelMap map = new ModelMap();
		map.put("success", userService.cancelDisturb(userId, currentUser));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 获取是否免打扰
	 * @param userId
	 * @param currentUser
	 * @return
	 */
	@GetMapping("/disturb/{userId}/{currentUser}")
	public ResponseEntity<ModelMap> getDisturb(@PathVariable("userId") String userId,
			@PathVariable("currentUser") String currentUser) {
		ModelMap map = new ModelMap();
		map.put("disturb", userService.getDisturb(userId, currentUser));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 获取我自己的免打扰列表
	 * @param aihangPrincipal
	 * @return
	 */
	@GetMapping("/disturbs")
	public ResponseEntity<ModelMap> getDisturbs(AihangPrincipal aihangPrincipal) {
		ModelMap map = new ModelMap();

		map.put("disturbs", userService.getDisturbs(aihangPrincipal.getUserId()));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/**
	 * 是否后台运行
	 * @param background
	 * @param aihangPrincipal
	 * @return
	 */
	@GetMapping("/background")
	public ResponseEntity<ModelMap> background(@RequestParam("background") boolean background,
			AihangPrincipal aihangPrincipal) {
		ModelMap map = new ModelMap();
		map.put("success", userService.background(aihangPrincipal.getUserId(), background));
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}
