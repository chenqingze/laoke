package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.Gender;
import com.aihangxunxi.aitalk.storage.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class UserRepository {

	@Resource
	private MongoDatabase aitalkDb;

	public boolean saveUser(User user) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		user.setUid(new ObjectId());
		user.setGender(Gender.UNKNOWN);
		mongoCollection.insertOne(user);
		return true;
	}

	public String queryUserUIdByUserId(String userId) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("userId", Long.parseLong(userId));
		User user = mongoCollection.find(bson).first();
		return user.getUid().toString();
	}

}
