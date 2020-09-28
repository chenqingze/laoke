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
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

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
		}
		else {
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
		}
		else {
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
		List<Friend> list = friendMongoCollection.find().into(new ArrayList<>());
		List<Friendship> friendships = new ArrayList<>();
		list.stream().forEach(friend -> {
			Friendship friendship = new Friendship();
			friendship.setAlias(friend.getAlias().isEmpty() ? friend.getFriendName() : friend.getAlias());
			friendship.setBlocked(false);
			friendship.setUserId(userId);
			friendship.setFriendId(friend.getFriendId());

			friendships.add(friendship);
		});
		return friendships;
	}

}
