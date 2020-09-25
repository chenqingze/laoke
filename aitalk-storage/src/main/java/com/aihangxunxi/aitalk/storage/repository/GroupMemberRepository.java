package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.GroupMember;
import com.aihangxunxi.aitalk.storage.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class GroupMemberRepository {

	@Resource
	private MongoDatabase aitalkDb;

	// 根据用户id获取用户群列表
	public List<GroupMember> queryUsersGroup(Long userId) {

		MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);
		User user = userMongoCollection.find(eq("userId", userId)).first();

		MongoCollection<GroupMember> groupMemberMongoCollection = aitalkDb.getCollection("groupMember",
				GroupMember.class);
		return groupMemberMongoCollection.find(eq("memberId", user.getUid())).into(new ArrayList<>());
	}

}
