package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.InviteStatus;
import com.aihangxunxi.aitalk.storage.constant.InviteType;
import com.aihangxunxi.aitalk.storage.model.Groups;
import com.aihangxunxi.aitalk.storage.model.Invitation;
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

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

@Repository
public class InvitationRepository {

    @Resource
    private MongoDatabase db;

    public boolean save(Invitation invitation) {
        InsertOneResult result = db.getCollection("invitation", Invitation.class).insertOne(invitation);
        return result.wasAcknowledged();
    }

    public Invitation updateInviteStatus(String id, String inviteStatus) {
        Invitation invitation = db.getCollection("invitation", Invitation.class).findOneAndUpdate(
                eq("_id", new ObjectId(id)),
                combine(set("inviteStatus", inviteStatus), set("updatedAt", System.currentTimeMillis())));
        return invitation;
    }

    // 保存群邀请
    public boolean saveGroupInvitation(Invitation invitation) {
        InsertOneResult result = db.getCollection("invitation", Invitation.class).insertOne(invitation);
        return result.wasAcknowledged();
    }

    // 更新邀请状态为已邀请
    public boolean updateGroupInvitationStatus(String groupId, Long userId) {
        MongoCollection<Invitation> invitationMongoCollection = db.getCollection("invitation", Invitation.class);
        Bson bson = and(eq("addresseeId", groupId), eq("requesterId", userId));
        Bson bson1 = set("inviteStatus", InviteStatus.ACCEPTED);
        invitationMongoCollection.updateOne(bson, bson1);
        return true;
    }

    // 更新状态为忽略
    public boolean ignoredGroupInvitation(String groupId, Long userId) {
        MongoCollection<Invitation> invitationMongoCollection = db.getCollection("invitation", Invitation.class);
        Bson bson = and(eq("addresseeId", groupId), eq("requesterId", userId));
        Bson bson1 = set("inviteStatus", InviteStatus.IGNORED);
        invitationMongoCollection.updateOne(bson, bson1);
        return true;
    }

    // 拒绝用户加入群
    public boolean declinedGroupInvitation(String groupId, Long userId) {
        MongoCollection<Invitation> invitationGroupMongoCollection = db.getCollection("invitation", Invitation.class);
        Bson bson = and(eq("requesterId", userId), eq("addresseeId", groupId));
        Bson bson1 = set("inviteStatus", InviteStatus.DECLINED);
        invitationGroupMongoCollection.updateOne(bson, bson1);
        return true;
    }


}
