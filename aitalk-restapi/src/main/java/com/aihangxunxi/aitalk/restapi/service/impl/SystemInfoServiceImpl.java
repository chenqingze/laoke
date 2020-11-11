package com.aihangxunxi.aitalk.restapi.service.impl;

import com.aihangxunxi.aitalk.restapi.service.SystemInfoService;
import com.aihangxunxi.aitalk.storage.model.SystemInfo;
import com.aihangxunxi.aitalk.storage.model.SystemInfoDto;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.SystemInfoRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: suguodong
 * Date:  2020/11/10 15:45
 * @Version: 3.0
 */
@Service
public class SystemInfoServiceImpl implements SystemInfoService {

    @Autowired
    private SystemInfoRepository systemInfoRepository;

    @Resource
    private UserRepository userRepository;


    @Override
    public List<SystemInfoDto> getSystemMsg(String id) {
        List<SystemInfoDto> offlineMsg = systemInfoRepository.getOfflineMsg(id);
        return  offlineMsg;
    }
}
