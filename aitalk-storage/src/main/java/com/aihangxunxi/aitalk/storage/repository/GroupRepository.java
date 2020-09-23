package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.Groups;
import com.aihangxunxi.aitalk.storage.model.UsersGroup;
import com.aihangxunxi.aitalk.storage.utils.PinYinUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class GroupRepository {

	@Resource
	private MongoDatabase aitalkDb;

	public List<Groups> queryUserGroups(Long userId) {
		MongoCollection<UsersGroup> usersGroupMongoCollection = aitalkDb.getCollection("usersGroup", UsersGroup.class);
		MongoCollection<Groups> groupMongoCollection = aitalkDb.getCollection("group", Groups.class);
		Bson bson = eq("userId", userId);
		List<UsersGroup> list = usersGroupMongoCollection.find(bson).into(new ArrayList<>());

		List<Groups> groups = new ArrayList<>();
		list.stream().forEach((g -> {
			Bson bson1 = eq(g.getGroupId());
			Groups group = groupMongoCollection.find(bson1).first();

			group.setPinyin(PinYinUtil.getPingYin(group.getName()));
			groups.add(group);
		}));

		return groups;
	}

}
