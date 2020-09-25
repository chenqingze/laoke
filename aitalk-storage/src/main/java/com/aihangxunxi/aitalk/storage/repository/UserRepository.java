package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.Gender;
import com.aihangxunxi.aitalk.storage.model.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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

	public User getUserById(Long userId) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		User user = mongoCollection.find(eq("userId", userId), User.class).first();
		return user;
	}

}
