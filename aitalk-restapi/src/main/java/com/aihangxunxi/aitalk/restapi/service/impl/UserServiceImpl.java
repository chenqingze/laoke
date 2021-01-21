package com.aihangxunxi.aitalk.restapi.service.impl;

import com.aihangxunxi.aitalk.restapi.service.UserService;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean saveUser(User user) {
        return userRepository.saveUser(user);
    }

    @Override
    public Map getUserById(Long userId) {
        return userRepository.queryUserById(userId);
    }

    @Override
    public boolean updateDeviceInfo(Long userId, String deviceCode, String deviceType) {
        return userRepository.updateDeviceInfo(userId, deviceCode, deviceType);
    }

    @Override
    public User getUserByUserId(Long userId) {
        User user = userRepository.getUserByUserId(userId);
        user.setIdStr(user.getId().toString());
        return user;
    }

    @Override
    public boolean regUser(Long userId, String nickname, String header) {
        return userRepository.regUser(userId, nickname, header);
    }

    @Override
    public boolean regStoreUser(Long userId, String nickname, String header) {
        return userRepository.regStoreUser(userId, nickname, header);
    }

    @Override
    public boolean cancelUser(Long userId) {
        return userRepository.cancelUser(userId);
    }

    @Override
    public boolean cancelBindJPush(Long userId) {
        System.out.println("开始取消绑定啦");
        System.out.println("userId：" + userId);
        return userRepository.cancelBindJPush(userId);
    }

    @Override
    public User queryUserByType(String userType, Long userId) {
        return userRepository.queryUserByType(userType, userId);
    }

    @Override
    public Object getUserByObjectId(String id) {
        return userRepository.getUserById(new ObjectId(id));
    }

    @Override
    public boolean getUserIsFreeze(Long userId, String userType) {
        return userRepository.checkoutUserIsFreeze(userId, userType);
    }

    @Override
    public boolean freezeUser(Long userId, String userType) {
        return userRepository.freezeUser(userId, userType);
    }

    @Override
    public boolean effective(Long userId, String userType) {
        return userRepository.effective(userId, userType);
    }

    @Override
    public boolean importUsers() {
        return userRepository.importUsers();
    }

    @Override
    public boolean disturb(Long userId, Long currentUser) {
        return userRepository.disturb(userId, currentUser);
    }

    @Override
    public boolean cancelDisturb(Long userId, Long currentUser) {
        return userRepository.cancelDisturb(userId, currentUser);
    }

    @Override
    public boolean getDisturb(Long userId, Long currentUser) {
        return userRepository.getDisturb(userId, currentUser);
    }

    @Override
    public boolean background(Long userId, boolean background) {
        return userRepository.background(userId, background);
    }

}
