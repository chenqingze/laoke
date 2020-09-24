package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.GroupMember;
import com.aihangxunxi.aitalk.storage.model.Groups;
import com.aihangxunxi.aitalk.storage.model.UsersGroup;
import com.aihangxunxi.aitalk.storage.utils.PinYinUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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

}
