package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.Invitation;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class InvitationRepository {

	@Resource
	private MongoDatabase db;

	public boolean save(Invitation invitation) {
		InsertOneResult result = db.getCollection("invitation", Invitation.class).insertOne(invitation);
		return result.wasAcknowledged();
	}

	// 保存群邀请
	public boolean saveGroupInvitation(Invitation invitation) {
		InsertOneResult result = db.getCollection("invitation", Invitation.class).insertOne(invitation);
		return result.wasAcknowledged();
	}

}
