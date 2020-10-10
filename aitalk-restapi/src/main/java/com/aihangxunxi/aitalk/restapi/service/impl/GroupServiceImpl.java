package com.aihangxunxi.aitalk.restapi.service.impl;

import com.aihangxunxi.aitalk.restapi.service.GroupService;
import com.aihangxunxi.aitalk.storage.model.Friendship;
import com.aihangxunxi.aitalk.storage.model.GroupInfo;
import com.aihangxunxi.aitalk.storage.model.Groups;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public Groups queryGroupInfoByGroupNo(String groupNo) {
        return groupRepository.queryGroupInfoByNo(groupNo);
    }

    @Override
    public boolean queryUserInGroup(String groupId, Long userId) {
        return groupRepository.checkUserInGroup(groupId, userId);
    }

    @Override
    public boolean queryUserInGroupByNo(String groupNo, Long userId) {
        return groupRepository.queryUserInGroupByNo(groupNo, userId);
    }

    @Override
    public int queryGroupMaxMemberCount() {
        return groupRepository.queryGroupMaxMemberCount();
    }

    @Override
    public List<Friendship> queryUsersFriend(Long userId) {
        return groupRepository.queryUsersFriend(userId);
    }

    @Override
    public boolean checkUserMaxGroupCount(Long userId) {
        return groupRepository.checkUserGroupCount(userId);
    }

    @Override
    public GroupInfo queryGroupSettingInfo(String groupId, Long userId) {
        return groupRepository.queryGroupInfo(groupId, userId);
    }

    @Override
    public List<User> queryGroupMembers(String groupId) {
        return groupRepository.queryGroupMember(groupId);
    }
}
