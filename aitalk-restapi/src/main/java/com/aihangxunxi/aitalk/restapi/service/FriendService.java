package com.aihangxunxi.aitalk.restapi.service;

import com.aihangxunxi.aitalk.storage.model.Friend;

import java.util.List;

public interface FriendService {

	Friend updAlias(Friend friend);

	Friend updMute(Friend friend);

	Friend updStickOnTop(Friend friend);

	Friend updBlocked(Friend friend);

	List<Friend> getBlocked(Long userId);

	List<Friend> getFrientList();

	boolean delFriend(String id);

}
