package com.aihangxunxi.aitalk.restapi.service;

import com.aihangxunxi.aitalk.storage.model.Groups;

public interface GroupService {

	// 判断用户是否还在群中
	boolean checkUserInGroup(String groupId, Long userId);

	Groups queryGroupInfo(String groupId);

	boolean queryUserInGroup(String groupId, Long userId);

}
