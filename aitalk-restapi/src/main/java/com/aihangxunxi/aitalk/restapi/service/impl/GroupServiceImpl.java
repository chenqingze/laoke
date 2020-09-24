package com.aihangxunxi.aitalk.restapi.service.impl;

import com.aihangxunxi.aitalk.restapi.service.GroupService;
import com.aihangxunxi.aitalk.storage.model.Groups;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GroupServiceImpl implements GroupService {

	@Resource
	private GroupRepository groupRepository;

	@Override
	public boolean checkUserInGroup(String groupId, Long userId) {
		return false;
	}

	@Override
	public Groups queryGroupInfo(String groupId) {
		return groupRepository.queryGroupInfo(groupId);
	}

	@Override
	public boolean queryUserInGroup(String groupId, Long userId) {
		return groupRepository.checkUserInGroup(groupId, userId);
	}

}
