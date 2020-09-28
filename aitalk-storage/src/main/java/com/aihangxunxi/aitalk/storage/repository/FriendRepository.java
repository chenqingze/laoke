package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.Friend;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FriendRepository {

	@Resource
	private MongoDatabase db;

	public boolean save(Friend friend) {
		InsertOneResult result = db.getCollection("friend", Friend.class).insertOne(friend);
		return result.wasAcknowledged();
	}

}
