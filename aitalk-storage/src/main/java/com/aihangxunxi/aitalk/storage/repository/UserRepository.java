package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.*;
import com.aihangxunxi.aitalk.storage.model.ConcernStore;
import com.aihangxunxi.aitalk.storage.model.Subscription;
import com.aihangxunxi.aitalk.storage.model.User;
import com.aihangxunxi.aitalk.storage.model.UserDTO;
import com.aihangxunxi.common.entity.UserRedisEntity;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

	// public User getUserByUserId(Long userId) {
	// MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
	// User user = mongoCollection.find(eq("userId", userId), User.class).first();
	// return user;
	// }
	public User getUserByUserId(Long userId) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		User user = new User();
		if (userId.equals(Long.parseLong("4330998946162008243"))) {
			Bson bson = and(eq("userId", userId), eq("userType", "STORE"));
			user = mongoCollection.find(bson, User.class).first();
			// UserRedisEntity u = (UserRedisEntity)
			// authRedisTemplate.opsForValue().get("user:" + userId);
			// user.setPhone(u.getPhone());
		}
		else {
			Bson bson = and(eq("userId", userId), eq("userType", "PERSONAL"));
			user = mongoCollection.find(bson, User.class).first();
			// UserRedisEntity u = (UserRedisEntity)
			// authRedisTemplate.opsForValue().get("user:" + userId);
			// user.setPhone(u.getPhone());
		}

		return user;
	}

	// 更新用户设备信息
	public boolean updateDeviceInfo(Long userId, String deviceCode, String deviceType) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("userId", userId);

		List<Bson> list = new ArrayList<>();
		list.add(set("deviceCode", deviceCode));
		list.add(set("devicePlatform", deviceType.toUpperCase()));

		mongoCollection.updateMany(bson, list);
		return true;
	}

	public boolean updateDeviceCodeByCode(String deviceCode) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("deviceCode", deviceCode);
		Bson bson1 = set("deviceCode", "");
		mongoCollection.findOneAndUpdate(bson, bson1);
		return true;
	}

	// 更新用户资料
	public boolean updateUserProfile(String userId, String profilePhoto, String nickname, String userType) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);

		Bson bson = eq(new ObjectId(userId));
		User user = mongoCollection.find(bson).first();
		Bson bson1 = and(eq("userId", user.getUserId()), eq("userType", UserType.valueOf(userType.toUpperCase())));

		Bson bson2 = set("nickname", nickname);
		Bson bson3 = set("header", profilePhoto);
		List<Bson> list = new ArrayList<>();
		list.add(bson2);
		list.add(bson3);

		mongoCollection.updateOne(bson1, list);

		return true;
	}

	// 注册用户
	public boolean regUser(Long userId, String nickname, String header) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);

		Bson queryBson = eq("userId", userId);
		long count = mongoCollection.countDocuments(queryBson);
		if (count == 0) {
			User user = new User();
			user.setHeader(header);
			user.setNickname(nickname);
			user.setUserId(userId);
			user.setGender(Gender.MALE);
			user.setDeviceCode("");
			user.setUserType(UserType.PERSONAL);
			user.setUserStatus(UserStatus.EFFECTIVE);
			user.setDevicePlatform(DevicePlatform.UNKNOWN);
			user.setDeviceIdiom(DeviceIdiom.PHONE);
			mongoCollection.insertOne(user);
			return true;
		}
		else {
			return false;
		}

	}

	// 注册店铺用户
	public boolean regStoreUser(Long userId, String nickname, String header) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("userId", userId);
		User personalUser = mongoCollection.find(bson).first();

		User user = new User();
		user.setHeader(header);
		user.setNickname(nickname);
		user.setUserId(userId);
		user.setGender(Gender.MALE);
		user.setDeviceCode(personalUser.getDeviceCode());
		user.setUserType(UserType.STORE);
		user.setUserStatus(UserStatus.EFFECTIVE);
		user.setDevicePlatform(personalUser.getDevicePlatform());
		user.setDeviceIdiom(DeviceIdiom.PHONE);
		mongoCollection.insertOne(user);
		return true;
	}

	// 注销
	public boolean cancelUser(Long userId) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("userId", userId);

		List<Bson> list = new ArrayList<>();
		Bson bson1 = set("header", "assets/msg_default.png");
		Bson bson2 = set("nickname", "该用户已注销");
		Bson bson3 = set("userStatus", UserStatus.CANCEL);
		list.add(bson1);
		list.add(bson2);
		list.add(bson3);
		mongoCollection.updateMany(bson, list);
		return true;
	}

	// 取消绑定
	public boolean cancelBindJPush(Long userId) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("userId", userId);
		Bson bson1 = set("deviceCode", "");
		mongoCollection.findOneAndUpdate(bson, bson1);
		return true;
	}

	// 根据用户类型获取用户
	public User queryUserByType(String userType, Long userId) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = and(eq("userId", userId), eq("userType", UserType.valueOf(userType.toUpperCase())));
		return mongoCollection.find(bson).first();
	}

	/**
	 * 禁用用户
	 * @return
	 */
	public boolean freezeUser(Long userId, String userType) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);

		if (userType.equals("PERSONAL")) {
			Bson bson = eq("userId", userId);
			Bson bson1 = set("userStatus", UserStatus.FREEZE);
			mongoCollection.updateMany(bson, bson1);
			return true;
		}
		else {
			Bson bson = and(eq("userId", userId), eq("userType", UserType.STORE));
			Bson bson1 = set("userStatus", UserStatus.FREEZE);
			mongoCollection.updateMany(bson, bson1);
			return true;
		}
	}

	/**
	 * 解禁用户
	 * @return
	 */
	public boolean effective(Long userId, String userType) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);

		if (userType.equals("PERSONAL")) {
			Bson bson = eq("userId", userId);
			Bson bson1 = set("userStatus", UserStatus.EFFECTIVE);
			mongoCollection.updateMany(bson, bson1);
			return true;
		}
		else {
			Bson bson = and(eq("userId", userId), eq("userType", UserType.STORE));
			Bson bson1 = set("userStatus", UserStatus.EFFECTIVE);
			mongoCollection.updateMany(bson, bson1);
			return true;
		}
	}

	/**
	 * 查询用户是否有效
	 * @return
	 */
	public boolean checkoutUserIsFreeze(Long userId, String userType) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = and(eq("userId", userId), eq("userType", userType));
		long count = mongoCollection.countDocuments(bson);
		if (count > 0) {
			User user = mongoCollection.find(bson).first();
			return user.getUserStatus() == UserStatus.EFFECTIVE;
		}
		else {
			return false;
		}

	}

	@Resource
	private RedisTemplate<String, Object> authRedisTemplate;

	@Resource
	private MongoClient mongoClient;

	public boolean importUsers() {
		MongoCollection<UserDTO> mongoCollection = mongoClient.getDatabase("aitalk-prod").getCollection("user",
				UserDTO.class);
		List<UserDTO> users = mongoCollection.find().into(new ArrayList());
		for (int i = 0; i < users.size(); i++) {
			importUser(users.get(i).getUserId(), users.get(i).getNickname(), users.get(i).getProfile());
			UserRedisEntity u = (UserRedisEntity) authRedisTemplate.opsForValue()
					.get("user:" + users.get(i).getUserId());
			System.out.println(i);
			if (u != null) {
				System.out.println(u.toString());
				// if (u.getStoreRedisEntity() != null) {
				// importStoreUser(users.get(i).getUserId(),
				// u.getStoreRedisEntity().getStoreName(),
				// u.getStoreRedisEntity().getStoreHeadPortrait());
				// }
			}
		}
		MongoCollection<ConcernStore> mongoCollection1 = mongoClient.getDatabase("aihang3")
				.getCollection("concern_store_v330", ConcernStore.class);
		List<ConcernStore> list = mongoCollection1.find().into(new ArrayList<>());
		list.stream().forEach(concernStore -> {
			System.out.println(concernStore.getUser_id() + "::::" + concernStore.getStore_id());
			follow(concernStore.getUser_id(), concernStore.getStore_id());
		});
		return true;
	}

	// 注册用户
	public boolean importUser(Long userId, String nickname, String header) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);

		Bson queryBson = eq("userId", userId);
		long count = mongoCollection.countDocuments(queryBson);
		if (count == 0) {
			User user = new User();
			user.setHeader(header);
			user.setNickname(nickname);
			user.setUserId(userId);
			user.setGender(Gender.MALE);
			user.setDeviceCode("");
			user.setUserType(UserType.PERSONAL);
			user.setUserStatus(UserStatus.EFFECTIVE);
			user.setDevicePlatform(DevicePlatform.UNKNOWN);
			user.setDeviceIdiom(DeviceIdiom.PHONE);
			mongoCollection.insertOne(user);
			return true;
		}
		else {
			return false;
		}

	}

	// 注册店铺用户
	public boolean importStoreUser(Long userId, String nickname, String header) {
		MongoCollection<User> mongoCollection = aitalkDb.getCollection("user", User.class);
		Bson bson = eq("userId", userId);
		User personalUser = mongoCollection.find(bson).first();

		User user = new User();
		user.setHeader(header);
		user.setNickname(nickname);
		user.setUserId(userId);
		user.setGender(Gender.MALE);
		user.setDeviceCode(personalUser.getDeviceCode());
		user.setUserType(UserType.STORE);
		user.setUserStatus(UserStatus.EFFECTIVE);
		user.setDevicePlatform(personalUser.getDevicePlatform());
		user.setDeviceIdiom(DeviceIdiom.PHONE);
		mongoCollection.insertOne(user);
		return true;
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

}
