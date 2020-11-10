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
     *
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
     *
     * @param storeId
     * @param aihangPrincipal
     * @return
     */
    @PostMapping("follow/{storeId}")
    public ResponseEntity<ModelMap> followStore(@PathVariable("storeId") Long storeId, AihangPrincipal aihangPrincipal) {
        ModelMap map = new ModelMap();
        map.put("success", fansService.followStore(storeId, aihangPrincipal.getUserId()));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    /**
     * 取消关注
     *
     * @param storeId
     * @param aihangPrincipal
     * @return
     */
    @PostMapping("cancelFollow/{storeId}")
    public ResponseEntity<ModelMap> cancelFollow(@PathVariable("storeId") Long storeId, AihangPrincipal aihangPrincipal) {
        ModelMap map = new ModelMap();
        map.put("success", fansService.cancelFollow(storeId, aihangPrincipal.getUserId()));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    /**
     * 获取是否已经关注
     *
     * @param storeId
     * @param aihangPrincipal
     * @return
     */
    @PostMapping("followed/{storeId}")
    public ResponseEntity<ModelMap> followed(@PathVariable("storeId") Long storeId, AihangPrincipal aihangPrincipal) {
        ModelMap map = new ModelMap();
        map.put("follow", fansService.followed(storeId, aihangPrincipal.getUserId()));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
