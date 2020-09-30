package com.aihangxunxi.aitalk.restapi.service;

import com.aihangxunxi.aitalk.storage.model.Friend;

public interface FriendService {

	Friend updAlias(Friend friend);

	Friend updMute(Friend friend);

	Friend updStickOnTop(Friend friend);

	Friend updBlocked(Friend friend);

}
