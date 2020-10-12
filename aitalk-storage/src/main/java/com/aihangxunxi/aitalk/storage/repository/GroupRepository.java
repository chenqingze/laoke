package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.*;
import com.aihangxunxi.aitalk.storage.utils.PinYinUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.set;

@Repository
public class GroupRepository {

    @Resource
    private MongoDatabase aitalkDb;

    public Groups queryGroupInfoByNo(String groupNo) {
        MongoCollection<Groups> groupMongoCollection = aitalkDb.getCollection("group", Groups.class);
        return groupMongoCollection.find(eq("groupNo", groupNo)).first();

    }

    // 获取群信息
    public Groups queryGroupInfo(String groupId) {

        MongoCollection<Groups> groupMongoCollection = aitalkDb.getCollection("group", Groups.class);
        Bson bson = eq(new ObjectId(groupId));
        Groups groups = groupMongoCollection.find(bson).first();
        MongoCollection<GroupMember> groupMemberMongoCollection = aitalkDb.getCollection("groupMember",
                GroupMember.class);
        Bson bson1 = eq("groupId", new ObjectId(groupId));
        long count = groupMemberMongoCollection.countDocuments(bson1);
        groups.setCount((int) count);

        return groups;
    }

    // 根据用户id获取用户群
    public List<Groups> queryUserGroups(Long userId) {
        MongoCollection<GroupMember> usersGroupMongoCollection = aitalkDb.getCollection("groupMember",
                GroupMember.class);
        MongoCollection<Groups> groupMongoCollection = aitalkDb.getCollection("group", Groups.class);
        Bson bson = eq("userId", userId);
        List<GroupMember> list = usersGroupMongoCollection.find(bson).into(new ArrayList<>());

        List<Groups> groups = new ArrayList<>();
        list.stream().forEach((g -> {
            Bson bson1 = eq(g.getGroupId());
            Groups group = groupMongoCollection.find(bson1).first();
            group.setPinyin(PinYinUtil.getPingYin(group.getName()));
            groups.add(group);
        }));
        groups.sort(Comparator.comparing(Groups::getPinyin));

        return groups;
    }

    // 判断用户是否在群中
    public boolean checkUserInGroup(String groupId, Long userId) {
        MongoCollection<UsersGroup> usersGroupMongoCollection = aitalkDb.getCollection("groupMember", UsersGroup.class);
        Bson bson = and(eq("userId", userId), eq("groupId", new ObjectId(groupId)));
        return usersGroupMongoCollection.find(bson).into(new ArrayList<>()).size() > 0;
    }

    // 根据群号获取用户是否在群中
    public boolean queryUserInGroupByNo(String groupNo, Long userId) {
        MongoCollection<GroupMember> groupMemberMongoCollection = aitalkDb.getCollection("groupMember",
                GroupMember.class);
        MongoCollection<Groups> groupsMongoCollection = aitalkDb.getCollection("group", Groups.class);
        Bson bson = eq("groupNo", groupNo);

        Groups groups = groupsMongoCollection.find(bson).first();
        if (groups != null) {

            Bson bson1 = and(eq("userId", userId), eq("groupId", groups.getId()));
            return groupMemberMongoCollection.countDocuments(bson1) > 0;
        } else {
            return false;
        }

    }

    // 获取群最大成员数
    public int queryGroupMaxMemberCount() {
        // todo

        return 500;
    }

    // 查询用户已经创建了几个群 todo 从redis获取最大创建数
    public boolean checkUserOwnGroup(String userId) {

        MongoCollection<Groups> groupMongoCollection = aitalkDb.getCollection("group", Groups.class);
        Bson bson = eq("owner", Long.parseLong(userId));

        return groupMongoCollection.countDocuments(bson) < 10;

    }

    // 创建群聊
    public String createMuc(String owner, String name, String header) {
        MongoCollection<Groups> groupsMongoCollection = aitalkDb.getCollection("group", Groups.class);
        MongoCollection<GroupMember> groupMemberMongoCollection = aitalkDb.getCollection("groupMember",
                GroupMember.class);
        Groups group = groupsMongoCollection.find().sort(eq("groupNo", -1)).first();
        long max;
        if (group == null) {
            max = 1000000000;
        } else {
            max = Long.parseLong(group.getGroupNo());
        }
        // 最大数量
        max += 1;
        Groups groups = new Groups();
        groups.setGroupNo(String.valueOf(max));
        groups.setHeader(header);
        groups.setName(name);
        groups.setOwner(Long.parseLong(owner));
        groups.setNotice("暂无群公告");
        GroupSetting groupSetting = new GroupSetting();
        groupSetting.setIsMute(false);
        groupSetting.setIsConfirmJoin(false);
        groups.setGroupSetting(groupSetting);
        InsertOneResult insertOneResult = groupsMongoCollection.insertOne(groups);
        return insertOneResult.getInsertedId().asObjectId().getValue().toString();
    }

    // 获取用户的好友列表
    public List<Friendship> queryUsersFriend(Long userId) {

        MongoCollection<Friend> friendMongoCollection = aitalkDb.getCollection("friend", Friend.class);
        Bson bson = and(eq("userId", userId), eq("isBlocked", 0), eq("status", "effective"));
        List<Friend> list = friendMongoCollection.find(bson).into(new ArrayList<>());
        List<Friendship> friendships = new ArrayList<>();
        list.stream().forEach(friend -> {
            Friendship friendship = new Friendship();
            friendship.setAlias(friend.getAlias().isEmpty() ? friend.getFriendName() : friend.getAlias());
            friendship.setBlocked(false);
            friendship.setUserId(userId);
            friendship.setHeader(friend.getFriendProfile());
            friendship.setFriendId(friend.getFriendId());
            friendship.setPinyin(PinYinUtil.getPingYin(friendship.getAlias()));
            friendships.add(friendship);
        });
        friendships.sort(Comparator.comparing(Friendship::getPinyin));

        return friendships;
    }

    // 获取用户创建的最大群数量是否合法 todo 从redis获取最大数量
    public boolean checkUserGroupCount(Long userId) {
        MongoCollection<Groups> groupsMongoCollection = aitalkDb.getCollection("group", Groups.class);
        Bson bson = eq("owner", userId);
        return groupsMongoCollection.countDocuments(bson) < 500;
    }

    // 获取群设置信息
    public GroupInfo queryGroupInfo(String groupId, Long userId) {
        MongoCollection<Groups> groupsMongoCollection = aitalkDb.getCollection("group", Groups.class);
        MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);
        Bson bson = eq(new ObjectId(groupId));
        Groups groups = groupsMongoCollection.find(bson).first();
        // 群资料
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupNo(groups.getGroupNo());
        groupInfo.setHeader(groups.getHeader());
        groupInfo.setId(groups.getId());
        groupInfo.setNotice(groups.getNotice());
        groupInfo.setOwner(groups.getOwner());
        groupInfo.setGroupOwner(userId.equals(groups.getOwner()));
        groupInfo.setName(groups.getName());
        groupInfo.setConfirmJoin(groups.getGroupSetting().getIsConfirmJoin());

        MongoCollection<GroupMember> groupMemberMongoCollection = aitalkDb.getCollection("groupMember",
                GroupMember.class);
        Bson bson1 = eq("groupId", new ObjectId(groupId));
        long count = groupMemberMongoCollection.countDocuments(bson1);
        groupInfo.setMemberCount((int) count);
        Bson bson2 = eq("userId", userId);
        GroupMember groupMember = groupMemberMongoCollection.find(and(bson1, bson2)).first();

        groupInfo.setUserNickname(groupMember.getAlias());
        groupInfo.setTop(groupMember.isTop());
        groupInfo.setMute(groups.getGroupSetting().getIsMute());
        List<GroupMember> groupMembers = groupMemberMongoCollection.find(bson1).into(new ArrayList<>());

        List<User> list = new ArrayList<>();
        for (int i = 0; i < groupMembers.size(); i++) {
            User user = userMongoCollection.find(eq("userId", groupMembers.get(i).getUserId())).first();
            list.add(user);
        }
        groupInfo.setUsers(list);
        return groupInfo;
    }

    // 设置群头像
    public boolean updateGroupHeader(String groupId, String header) {
        MongoCollection<Groups> groupsMongoCollection = aitalkDb.getCollection("group", Groups.class);
        Bson bson = eq(new ObjectId(groupId));
        Bson bson1 = set("header", header);
        groupsMongoCollection.updateOne(bson, bson1);
        return true;
    }

    // 设置群名
    public boolean updateGroupName(String groupId, String name) {
        MongoCollection<Groups> groupsMongoCollection = aitalkDb.getCollection("group", Groups.class);
        Bson bson = eq(new ObjectId(groupId));
        Bson bson1 = set("name", name);
        groupsMongoCollection.updateOne(bson, bson1);
        return true;
    }

    // 设置群公告
    public boolean updateGroupNotice(String groupId, String notice) {
        MongoCollection<Groups> groupsMongoCollection = aitalkDb.getCollection("group", Groups.class);
        Bson bson = eq(new ObjectId(groupId));
        Bson bson1 = set("notice", notice);
        groupsMongoCollection.updateOne(bson, bson1);

        return true;
    }

    // 更新群是否可直接加入
    public boolean updateGroupConfirmJoin(String groupId, boolean isConfirm) {
        MongoCollection<Groups> groupsMongoCollection = aitalkDb.getCollection("group", Groups.class);
        Bson bson = eq(new ObjectId(groupId));
        Bson bson1 = set("groupSetting.isConfirmJoin", isConfirm);
        groupsMongoCollection.updateOne(bson, bson1);
        return true;
    }

    // 设置群是否禁言
    public boolean updateGroupMute(String groupId, boolean mute) {
        MongoCollection<Groups> groupsMongoCollection = aitalkDb.getCollection("group", Groups.class);
        Bson bson = eq(new ObjectId(groupId));
        Bson bson1 = set("groupSetting.isMute", mute);
        groupsMongoCollection.updateOne(bson, bson1);
        return true;
    }

    // 设置用户群昵称
    public boolean updateUserGroupNickname(String groupId, Long userId, String nickname) {
        MongoCollection<GroupMember> groupsMongoCollection = aitalkDb.getCollection("groupMember", GroupMember.class);

        Bson bson = and(eq("userId", userId), eq("groupId", new ObjectId(groupId)));
        Bson bson1 = set("alias", nickname);
        groupsMongoCollection.updateOne(bson, bson1);
        return true;
    }

    // 设置用户群是否置顶
    public boolean updateUserGroupTop(String groupId, boolean top) {
        MongoCollection<GroupMember> groupsMongoCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
        Bson bson = eq(new ObjectId(groupId));
        Bson bson1 = set("top", top);
        groupsMongoCollection.updateOne(bson, bson1);
        return true;
    }

    // 设置用户群是否免打扰
    public boolean updateUserGroupMute(String groupId, boolean mute) {
        MongoCollection<GroupMember> groupsMongoCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
        Bson bson = eq(new ObjectId(groupId));
        Bson bson1 = set("mute", mute);
        groupsMongoCollection.updateOne(bson, bson1);
        return true;
    }

    // 根据群id获取群成员
    public List<User> queryGroupMember(String groupId) {
        MongoCollection<GroupMember> groupsMongoCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
        MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);
        Bson bson = eq("groupId", new ObjectId(groupId));
        List<GroupMember> list = groupsMongoCollection.find(bson).into(new ArrayList<>());
        List<User> users = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Bson bson1 = eq("userId", list.get(i).getUserId());
            User user = userMongoCollection.find(bson1).first();
            user.setPinyin(PinYinUtil.getPingYin(user.getNickname()));
            users.add(user);
        }
        users.sort(Comparator.comparing(User::getPinyin));
        return users;
    }

    // 获取不在当前群里的好友
    public List<User> queryFriendNinGroup(String groupId, Long userId) {
        MongoCollection<GroupMember> groupsMongoCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
        MongoCollection<Friend> friendMongoCollection = aitalkDb.getCollection("friend", Friend.class);
        Bson bson = and(eq("userId", userId), eq("isBlocked", 0), eq("status", "effective"));
        List<Friend> friends = friendMongoCollection.find(bson).into(new ArrayList<>());
        Bson bson1 = eq("groupId", new ObjectId(groupId));
        List<GroupMember> groupMembers = groupsMongoCollection.find(bson1).into(new ArrayList<>());
        List<Friend> friendList = new ArrayList<>();

        friendList = friends.stream().filter(friend -> {
            for (GroupMember g : groupMembers) {
                if (friend.getFriendId().equals(g.getUserId())) {
                    return false;
                }
            }
            return true;

        }).collect(Collectors.toList());

        List<User> users = new ArrayList<>();
        MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);

        for (int i = 0; i < friendList.size(); i++) {
            Bson bson2 = eq("userId", friendList.get(i).getFriendId());
            User user = userMongoCollection.find(bson2).first();
            user.setPinyin(PinYinUtil.getPingYin(user.getNickname()));
            users.add(user);
        }
        users.sort(Comparator.comparing(User::getPinyin));
        return users;
    }

    // 判断群是否满了 todo 从redis获取最大数量
    public boolean checkGroupIsFull(String groupId, int count) {
        MongoCollection<GroupMember> groupsMongoCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
        Bson bson1 = eq("groupId", new ObjectId(groupId));
        long groupCount = groupsMongoCollection.countDocuments(bson1);
        return (groupCount + count) > 500;
    }

    // 获取我在群中的昵称
    public String queryGroupMemberName(String groupId, Long userId) {
        MongoCollection<GroupMember> groupsMongoCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
        Bson bson = and(eq("groupId", new ObjectId(groupId)), eq("userId", userId));
        return groupsMongoCollection.find(bson).first().getAlias();
    }

    // 更新群置顶
    public boolean updateGroupMemberTop(String groupId, Long userId, boolean top) {
        MongoCollection<GroupMember> groupsMongoCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
        Bson bson = and(eq("groupId", new ObjectId(groupId)), eq("userId", userId));
        Bson bson1 = set("top", top);
        return groupsMongoCollection.updateOne(bson, bson1).getModifiedCount() > 0;
    }

    // 获取群是否置顶
    public boolean queryGroupMemberTop(String groupId, Long userId) {
        MongoCollection<GroupMember> groupsMongoCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
        Bson bson = and(eq("groupId", new ObjectId(groupId)), eq("userId", userId));
        return groupsMongoCollection.find(bson).first().isTop();
    }

    // 退出群
    public boolean exitGroup(String groupId, Long userId) {
        MongoCollection<Groups> groupsMongoCollection = aitalkDb.getCollection("group", Groups.class);
        MongoCollection<GroupMember> groupMemberMongoCollection = aitalkDb.getCollection("groupMember", GroupMember.class);

        Bson bson = eq(new ObjectId(groupId));
        Groups groups = groupsMongoCollection.find(bson).first();
        if (userId.equals(groups.getOwner())) {
            // 当前人为群主 退出群 并选取新的群主
            Bson bson1 = and(eq("groupId", new ObjectId(groupId)), eq("userId", userId));
            groupMemberMongoCollection.deleteOne(bson1);

            Bson bson2 = eq("groupId", new ObjectId(groupId));
            GroupMember groupMember = groupMemberMongoCollection.find(bson2).sort(ascending("joinGroupTime")).first();
            if (groupMember != null) {
                // 更新群管理
                Bson bson3 = set("owner", groupMember.getUserId());
                groupsMongoCollection.updateOne(bson, bson3);
            }
        } else {
            Bson bson1 = and(eq("groupId", new ObjectId(groupId)), eq("userId", userId));
            groupMemberMongoCollection.deleteOne(bson1);
        }
        return true;
    }
}
