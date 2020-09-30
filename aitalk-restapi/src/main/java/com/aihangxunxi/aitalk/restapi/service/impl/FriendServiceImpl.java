package com.aihangxunxi.aitalk.restapi.service.impl;

import com.aihangxunxi.aitalk.restapi.service.FriendService;
import com.aihangxunxi.aitalk.storage.model.Friend;
import com.aihangxunxi.aitalk.storage.repository.FriendRepository;
import com.aihangxunxi.aitalk.storage.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FriendServiceImpl implements FriendService {

	@Resource
	private FriendRepository friendRepository;

	@Override
	public Friend updAlias(Friend friend) {
		Friend updatedFriend = friendRepository.updAlias(friend);
		return updatedFriend;
	}

	@Override
	public Friend updMute(Friend friend) {
		Friend updatedFriend = friendRepository.updMute(friend);
		return updatedFriend;
	}

	@Override
	public Friend updStickOnTop(Friend friend) {
		Friend updatedFriend = friendRepository.updStickOnTop(friend);
		return updatedFriend;
	}

	@Override
	public Friend updBlocked(Friend friend) {
		Friend updatedFriend = friendRepository.updBlocked(friend);
		return updatedFriend;
	}

}
