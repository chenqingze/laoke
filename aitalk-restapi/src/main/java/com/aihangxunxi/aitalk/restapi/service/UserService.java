package com.aihangxunxi.aitalk.restapi.service;

import com.aihangxunxi.aitalk.storage.model.User;

import java.util.Map;

public interface UserService {

	boolean saveUser(User user);

	Map getUserById(Long userId);

	boolean updateDeviceInfo(Long userId, String deviceCode, String deviceType);

	User getUserByUserId(Long userId);
}
