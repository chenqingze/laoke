package com.aihangxunxi.aitalk.restapi.service;

import com.aihangxunxi.aitalk.storage.model.Disturb;
import com.aihangxunxi.aitalk.storage.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

	boolean saveUser(User user);

	Map getUserById(Long userId);

	boolean updateDeviceInfo(Long userId, String deviceCode, String deviceType);

	User getUserByUserId(Long userId);

	boolean regUser(Long userId, String nickname, String header);

	boolean regStoreUser(Long userId, String nickname, String header);

	boolean cancelUser(Long userId);

	boolean cancelBindJPush(Long userId);

	boolean freezeUser(Long userId, String userType);

	boolean effective(Long userId, String userType);

	User queryUserByType(String userType, Long userId);

	Object getUserByObjectId(String id);

	boolean getUserIsFreeze(Long userId, String userType);

	boolean importUsers();

	boolean disturb(Long userId, Long currentUser);

	boolean cancelDisturb(Long userId, Long currentUser);

	boolean getDisturb(Long userId, Long currentUser);

	List<Disturb> getDisturbs(Long userId);

	boolean background(Long userId, boolean background);

}
