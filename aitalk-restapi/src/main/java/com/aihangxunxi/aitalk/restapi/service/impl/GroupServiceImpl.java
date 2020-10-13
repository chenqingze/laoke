package com.aihangxunxi.aitalk.restapi.service.impl;

import com.aihangxunxi.aitalk.restapi.service.GroupService;
import com.aihangxunxi.aitalk.storage.model.*;
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

    @Override
    public List<User> queryFriendNinGroup(String groupId, Long userId) {
        return groupRepository.queryFriendNinGroup(groupId, userId);

    }

    @Override
    public boolean checkGroupIsFull(String groupId, int count) {
        return groupRepository.checkGroupIsFull(groupId, count);

    }

    @Override
    public boolean updateGroupMemberName(String name, Long userId, String groupId) {
        return groupRepository.updateUserGroupNickname(groupId, userId, name);
    }

    @Override
    public String queryGroupMemberName(String groupId, Long userId) {
        return groupRepository.queryGroupMemberName(groupId, userId);
    }

    @Override
    public boolean updateGroupMemberTop(String groupId, Long userId, boolean top) {
        return groupRepository.updateGroupMemberTop(groupId, userId, top);

    }

    @Override
    public boolean queryGroupMemberTop(String groupId, Long userId) {
        return groupRepository.queryGroupMemberTop(groupId, userId);
    }

    @Override
    public List<Invitation> queryManageGroupInvitation(Long userId) {
        return groupRepository.queryManageGroupInvitation(userId);
    }

    @Override
    public int getUnreadGroupInvitationCount(Long userId) {
        return groupRepository.getUnreadGroupInvitationCount(userId);
    }

    @Override
    public boolean updateUnreadGroupInvitation(Long userId){
        return groupRepository.updateUnreadGroupInvitation(userId);
    }
}
