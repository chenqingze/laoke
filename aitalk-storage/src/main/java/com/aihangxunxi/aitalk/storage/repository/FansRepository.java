package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.Fans;
import com.aihangxunxi.aitalk.storage.model.Subscription;
import com.aihangxunxi.aitalk.storage.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
			Bson bson1 = eq("userId", fansId);
			User user = userMongoCollection.find(bson1).first();
			fans.setNickname(user.getNickname());
			fans.setProfile_photo(user.getHeader());
			fansList.add(fans);
		});
		return fansList;
	}

}
