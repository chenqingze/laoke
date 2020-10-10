package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.GroupMember;
import com.aihangxunxi.aitalk.storage.model.User;
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

    // 用户进群
    public boolean saveUserJoinGroup(String groupId, Long userId, String memberId, String header, String alias) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(new ObjectId(groupId));
        groupMember.setMemberId(new ObjectId(memberId));
        groupMember.setUserId(userId);
        groupMember.setAlias(alias);
        groupMember.setBlocked(false);
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

}
