package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.InviteStatus;
import com.aihangxunxi.aitalk.storage.model.Invitation;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

@Repository
public class InvitationRepository {

	@Resource
	private MongoDatabase db;

	public boolean save(Invitation invitation) {
		InsertOneResult result = db.getCollection("invitation", Invitation.class).insertOne(invitation);
		return result.wasAcknowledged();
	}

	public boolean updateInviteStatus(String id) {
		UpdateResult updateResult = db.getCollection("invitation", Invitation.class)
				.updateOne(eq("_id", new ObjectId(id)), set("inviteStatus", InviteStatus.ACCEPTED));
		return updateResult.wasAcknowledged();
	}

}
