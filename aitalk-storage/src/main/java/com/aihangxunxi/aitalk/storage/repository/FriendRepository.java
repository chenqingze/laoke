package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.Friend;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.result.InsertOneResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

@Component
public class FriendRepository {

	@Resource
	private MongoDatabase db;

	public boolean save(Friend friend) {
		InsertOneResult result = db.getCollection("friend", Friend.class).insertOne(friend);
		return result.wasAcknowledged();
	}

	public Friend updAlias(Friend friend) {
		Friend updatedFriend = db.getCollection("friend", Friend.class).findOneAndUpdate(eq("_id", friend.getId()),
				set("alias", friend.getAlias()), new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
		return updatedFriend;
	}

	public Friend updMute(Friend friend) {
		Friend updatedFriend = db.getCollection("friend", Friend.class).findOneAndUpdate(eq("_id", friend.getId()),
				set("isMute", friend.getIsMute()), new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
		return updatedFriend;
	}

	public Friend updStickOnTop(Friend friend) {
		Friend updatedFriend = db.getCollection("friend", Friend.class).findOneAndUpdate(eq("_id", friend.getId()),
				set("isStickOnTop", friend.getIsStickOnTop()),
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
		return updatedFriend;
	}

	public Friend updBlocked(Friend friend) {
		Friend updatedFriend = db.getCollection("friend", Friend.class).findOneAndUpdate(eq("_id", friend.getId()),
				set("isBlocked", friend.getIsBlocked()),
				new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
		return updatedFriend;
	}

}
