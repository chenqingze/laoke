package com.aihangxunxi.aitalk.restapi.service;

import com.aihangxunxi.aitalk.storage.model.*;

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

	// 判断是否已达上限
	boolean checkUserMaxGroupCount(Long userId);

	// 获取群设置
	GroupInfo queryGroupSettingInfo(String groupId, Long userId);

	// 获取群成员
	List<User> queryGroupMembers(String groupId);

	// 获取不在群里的好友
	List<User> queryFriendNinGroup(String groupId, Long userId);

	// 判断群是否满了
	boolean checkGroupIsFull(String groupId, int count);

	// 更新我在群中的昵称
	boolean updateGroupMemberName(String name, Long userId, String groupId);

	// 获取我在群中的昵称
	String queryGroupMemberName(String groupId, Long userId);

	// 更新置顶
	boolean updateGroupMemberTop(String groupId, Long userId, boolean top);

	// 获取群成员是否置顶
	boolean queryGroupMemberTop(String groupId, Long userId);

	// 获取我管理群的申请记录
	List<Invitation> queryManageGroupInvitation(Long userId);

	// 获取未读申请记录条数
	int getUnreadGroupInvitationCount(Long userId);

	// 更新未读群记录为已读
	boolean updateUnreadGroupInvitation(Long userId);

}
