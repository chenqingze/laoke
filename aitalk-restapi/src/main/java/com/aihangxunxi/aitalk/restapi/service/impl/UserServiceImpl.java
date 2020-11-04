package com.aihangxunxi.aitalk.restapi.service.impl;

import com.aihangxunxi.aitalk.restapi.service.UserService;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
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

}
