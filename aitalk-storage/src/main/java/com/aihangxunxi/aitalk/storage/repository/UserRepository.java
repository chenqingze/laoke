package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.Gender;
import com.aihangxunxi.aitalk.storage.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

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

	// 更新用户设备信息
	public boolean updateDeviceInfo(Long userId, String deviceCode, String deviceType) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("userId", userId);
		Bson bson1 = and(set("deviceCode", deviceCode), set("devicePlatform", deviceType));
		mongoCollection.updateOne(bson, bson1);
		return false;
	}

	// 更新用户资料
	public boolean updateUserProfile(String userId, String profilePhoto, String nickname) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);

		Bson bson = eq(new ObjectId(userId));
		Bson bson1 = set("nickname", nickname);
		Bson bson2 = set("header", profilePhoto);
		List<Bson> list = new ArrayList<>();
		list.add(bson1);
		list.add(bson2);

		mongoCollection.updateOne(bson, list);

		return true;
	}

}
