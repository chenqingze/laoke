package com.aihangxunxi.aitalk.restapi.service;

import com.aihangxunxi.aitalk.storage.model.Friendship;
import com.aihangxunxi.aitalk.storage.model.Groups;

import java.util.List;

public interface GroupService {

    // 判断用户是否还在群中
    boolean checkUserInGroup(String groupId, Long userId);

    // 根据id获取群信息
    Groups queryGroupInfo(String groupId);

    // 根据群号获取群信息
    Groups queryGroupInfoByGroupNo(String groupNo);

    // 判断用户是否在群中
    boolean queryUserInGroup(String groupId, Long userId);

    // 根据群号判断用户是否在群中
    boolean queryUserInGroupByNo(String groupNo, Long userId);

    // 获取设置中的最大成员数量
    int queryGroupMaxMemberCount();

    // 获取自己的好友
    List<Friendship> queryUsersFriend(Long userId);

}
