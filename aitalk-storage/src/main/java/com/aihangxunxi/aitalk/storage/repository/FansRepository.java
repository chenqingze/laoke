package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.Fans;
import com.aihangxunxi.aitalk.storage.model.Subscription;
import com.aihangxunxi.aitalk.storage.model.SysUserConcern;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.utils.PinYinUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Repository
public class FansRepository {

	@Resource
	private MongoDatabase aitalkDb;

	@Resource
	private MongoClient mongoClient;

	// 查询粉丝
	public List<Fans> queryFans(Long userId, int offset, int limit) {

		MongoCollection<SysUserConcern> mongoCollection = mongoClient.getDatabase("aihang4")
				.getCollection("sys_user_concern", SysUserConcern.class);
		MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("concerned_user_id", userId);
		List<SysUserConcern> list = mongoCollection.find(bson).limit(limit + 1).skip(offset).into(new ArrayList<>());
		List<Fans> fansList = new ArrayList<>();

		list.stream().forEach((f) -> {
			Long fansId = f.getUser_id();
			Bson bson1 = and(eq("userId", fansId), eq("userType", "PERSONAL"));
			User user = userMongoCollection.find(bson1).first();
			Fans fans = new Fans();
			fans.setUserId(fansId);
			if (user != null) {
				fans.setNickname(user.getNickname());
				fans.setId(user.getId().toHexString());
				fans.setProfile_photo(user.getHeader());
				fansList.add(fans);
			}

		});
		return fansList;
	}

	// 查询粉丝
	// 查询粉丝
	public List<Fans> queryFansList(Long userId) {

		MongoCollection<SysUserConcern> mongoCollection = mongoClient.getDatabase("aihang4")
				.getCollection("sys_user_concern", SysUserConcern.class);
		MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("concerned_user_id", userId);
		List<SysUserConcern> list = mongoCollection.find(bson).into(new ArrayList<>());
		List<Fans> fansList = new ArrayList<>();

		list.stream().forEach((f) -> {
			Long fansId = f.getUser_id();
			Bson bson1 = and(eq("userId", fansId), eq("userType", "PERSONAL"));
			User user = userMongoCollection.find(bson1).first();
			Fans fans = new Fans();
			fans.setUserId(fansId);
			if (user != null) {
				String pinyin = PinYinUtil.getPingYin(user.getNickname()).substring(0, 1);
				fans.setNickname(user.getNickname());
				fans.setPinyin(pinyin);
				fans.setId(user.getId().toHexString());
				fans.setProfile_photo(user.getHeader());
				fansList.add(fans);
			}

		});

		fansList.sort(Comparator.comparing(Fans::getPinyin));
		return fansList;
	}

	// 关注
	public boolean follow(Long storeId, Long userId) {
		MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = and(eq("userId", storeId), eq("userType", "STORE"));
		User store = userMongoCollection.find(bson).first();
		Bson bson1 = and(eq("userId", storeId), eq("userType", "PERSONAL"));
		User user = userMongoCollection.find(bson1).first();
		MongoCollection<Subscription> mongoCollection = aitalkDb.getCollection("subscription", Subscription.class);
		Subscription subscription = new Subscription();
		subscription.setCreatedAt(new Date().getTime());
		subscription.setPublisher(userId);
		subscription.setSubscriber(storeId);
		subscription.setUpdatedAt(new Date().getTime());
		mongoCollection.insertOne(subscription);
		return true;
	}

	public boolean followed(Long storeId, Long userId) {
		MongoCollection<Subscription> mongoCollection = aitalkDb.getCollection("subscription", Subscription.class);
		Bson bson = and(eq("subscriber", storeId), eq("publisher", userId));
		return mongoCollection.countDocuments(bson) > 0;

	}

	public boolean cancelFollow(Long storeId, Long userId) {
		MongoCollection<Subscription> mongoCollection = aitalkDb.getCollection("subscription", Subscription.class);
		Bson bson = and(eq("subscriber", storeId), eq("publisher", userId));
		mongoCollection.deleteMany(bson);
		return true;
	}

}
