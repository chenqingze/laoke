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

    // 更具用户Id 获取用户详情
    // 根据用户查询
    @GetMapping("getUser/{userId}")
    public ResponseEntity<ModelMap> getUserByUserId(@PathVariable("userId") Long userId) {
        ModelMap map = new ModelMap();
        map.put("user", userService.getUserByUserId(userId));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    /**
     * 注册
     *
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
     *
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
     *
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
     *
     * @param userId
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<ModelMap> queryUser(@RequestParam("userId") Long userId) {

        ModelMap map = new ModelMap();

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

}
