package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.UserType;
import com.aihangxunxi.aitalk.storage.model.Fans;
import com.aihangxunxi.aitalk.storage.model.Subscription;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.utils.PinYinUtil;
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

	// 查询粉丝
	public List<Fans> queryFans(Long userId) {
		MongoCollection<Subscription> mongoCollection = aitalkDb.getCollection("subscription", Subscription.class);
		MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("subscriber", userId);
		List<Subscription> list = mongoCollection.find(bson).into(new ArrayList<>());
		List<Fans> fansList = new ArrayList<>();
		list.stream().forEach(subscription -> {
			Fans fans = new Fans();
			Long fansId = subscription.getPublisher();
			fans.setUserId(fansId);
			Bson bson1 = and(eq("userId", fansId), eq("userType", UserType.PERSONAL));
			User user = userMongoCollection.find(bson1).first();
			if (user != null) {
				String pinyin = PinYinUtil.getPingYin(user.getNickname()).substring(0, 1);
				fans.setNickname(user.getNickname());
				fans.setPinyin(pinyin);
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
