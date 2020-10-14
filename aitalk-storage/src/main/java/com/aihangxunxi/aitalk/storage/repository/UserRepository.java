package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.Gender;
import com.aihangxunxi.aitalk.storage.model.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;

import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class UserRepository {

	@Resource
	private MongoDatabase aitalkDb;

	public boolean saveUser(User user) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		user.setId(new ObjectId());
		user.setGender(Gender.MALE);
		mongoCollection.insertOne(user);
		return true;
	}

	public String queryUserUIdByUserId(String userId) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("userId", Long.parseLong(userId));
		User user = mongoCollection.find(bson).first();
		return user.getId().toString();
	}

	// 根据用户id获取用户
	public Map queryUserById(Long userId) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("userId", userId);
		User user = mongoCollection.find(bson).first();
		ModelMap map = new ModelMap();
		map.put("uId", user.getId());
		map.put("header", user.getHeader());
		map.put("nickname", user.getNickname());
		return map;
	}

	public User getUserById(ObjectId id) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		User user = mongoCollection.find(eq("_id", id), User.class).first();
		return user;
	}

	public User getUserByUserId(Long userId) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		User user = mongoCollection.find(eq("userId", userId), User.class).first();
		return user;
	}

}
