package com.aihangxunxi.aitalk.restapi.service;

import com.aihangxunxi.aitalk.storage.model.SystemInfo;
import com.aihangxunxi.aitalk.storage.model.SystemInfoDto;

import java.util.List;

/**
 * @Author: suguodong
 * Date:  2020/11/10 15:44
 * @Version: 3.0
 */
public interface SystemInfoService {
    List<SystemInfoDto> getSystemMsg(String id);
}
