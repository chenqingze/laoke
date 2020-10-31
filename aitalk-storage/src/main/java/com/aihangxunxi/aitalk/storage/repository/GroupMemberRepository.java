package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.Friend;
import com.aihangxunxi.aitalk.storage.model.GroupMember;
import com.aihangxunxi.aitalk.storage.model.Groups;
import com.aihangxunxi.aitalk.storage.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

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
		return groupMemberMongoCollection.find(eq("memberId", user.getId())).into(new ArrayList<>());
	}

	// 用户进群
	public boolean saveUserJoinGroup(String groupId, Long userId, String memberId, String header, String alias) {
		GroupMember groupMember = new GroupMember();
		groupMember.setGroupId(new ObjectId(groupId));
		groupMember.setMemberId(new ObjectId(memberId));
		groupMember.setUserId(userId);
		groupMember.setAlias(alias);
		groupMember.setBlocked(false);
		groupMember.setJoinGroupTime(new Date().getTime());

		groupMember.setMute(false);
		MongoCollection<GroupMember> groupMemberMongoCollection = aitalkDb.getCollection("groupMember",
				GroupMember.class);
		groupMemberMongoCollection.insertOne(groupMember);

		return true;
	}

	public boolean checkGroupIsFull(String groupId) {
		MongoCollection<GroupMember> groupMemberCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
		Bson bson = eq("groupId", new ObjectId(groupId));
		long memberCount = groupMemberCollection.countDocuments(bson);
		return memberCount < 500;

	}

	public boolean removeGroupMember(String groupId, Long userId) {
		MongoCollection<GroupMember> groupMemberCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
		Bson bson = and(eq("groupId", new ObjectId(groupId)), eq("userId", userId));
		return groupMemberCollection.deleteOne(bson).getDeletedCount() > 0;
	}

	// 更新最后的阅读时间
	public boolean updateLastChat(String groupId, String userId, String msgId) {
		MongoCollection<GroupMember> groupMemberCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
		Bson bson = and(eq("groupId", new ObjectId(groupId)), eq("memberId", new ObjectId(userId)));
		Bson bson1 = set("lastAckMsgId", new ObjectId(msgId));
		Bson bson2 = set("lastAckMsgTime", new Date().getTime());
		List<Bson> list = new ArrayList<>();
		list.add(bson1);
		list.add(bson2);
		groupMemberCollection.updateOne(bson, list);
		return true;
	}

	// 获取最后的聊天id
	public List<GroupMember> queryLastChatId(Long userId) {
		MongoCollection<GroupMember> groupMemberCollection = aitalkDb.getCollection("groupMember", GroupMember.class);
		Bson bson = eq("userId", userId);

		List<GroupMember> groupMembers = groupMemberCollection.find(bson).into(new ArrayList<>());
		return groupMembers;
	}

	// 获取群成员列表
	public List<GroupMember> queryGroupMembersList(Long userId) {
		MongoCollection<Groups> groupsMongoCollection = aitalkDb.getCollection("group", Groups.class);
		MongoCollection<GroupMember> groupMemberMongoCollection = aitalkDb.getCollection("groupMember",
				GroupMember.class);
		Bson bson = eq("userId", userId);
		List<GroupMember> list = groupMemberMongoCollection.find(bson).into(new ArrayList<>());

		List<GroupMember> returnList = new ArrayList<>();
		list.stream().forEach(groupMember -> {
			ObjectId groupId = groupMember.getGroupId();
			returnList.addAll(groupMemberMongoCollection.find(eq("groupId", groupId)).into(new ArrayList<>()));
		});

		return returnList;
	}

	// 判断群成员是否开启群免打扰
	public boolean checkMucMemberIsMute(Long userId, String groupId) {

		MongoCollection<GroupMember> groupMemberMongoCollection = aitalkDb.getCollection("groupMember",
				GroupMember.class);
		Bson bson = and(eq("userId", userId), eq("groupId", new ObjectId(groupId)));
		return groupMemberMongoCollection.find(bson).first().isMute();
	}

	// 获取群成员昵称或好友备注
	public String queryGroupMemberDisplayName(Long receiverId, Long senderId, String groupId) {
		MongoCollection<Friend> friendMongoCollection = aitalkDb.getCollection("friend", Friend.class);
		Bson bson = and(eq("userId", receiverId), eq("friendId", senderId));
		Friend friend = friendMongoCollection.find(bson).first();
		if (friend != null) {
			return friend.getFriendName();
		}
		else {
			MongoCollection<GroupMember> groupMemberMongoCollection = aitalkDb.getCollection("groupMember",
					GroupMember.class);
			Bson bson1 = and(eq("userId", senderId), eq("groupId", new ObjectId(groupId)));
			GroupMember groupMember = groupMemberMongoCollection.find(bson1).first();
			return groupMember.getAlias();
		}

	}

}
