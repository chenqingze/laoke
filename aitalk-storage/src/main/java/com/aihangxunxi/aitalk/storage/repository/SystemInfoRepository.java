package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * @Author: suguodong Date: 2020/11/4 16:45
 * @Version: 3.0
 */
@Repository
public class SystemInfoRepository {

	@Resource
	private MongoDatabase db;

	public boolean saveSystemInfo(SystemInfo info) {
		// 保存至主表
		MongoCollection<SystemInfo> mongoCollection = db.getCollection("systemInfo", SystemInfo.class);
		InsertOneResult result = mongoCollection.insertOne(info);
		return true;
	}

	public boolean saveOfflineSystemInfo(SystemInfo info) {
		// 保存至离线系统通知
		MongoCollection<SystemInfo> mongoCollection = db.getCollection("offlineSystemInfo", SystemInfo.class);
		InsertOneResult result = mongoCollection.insertOne(info);
		return true;
	}

	public boolean deleteOfflineMsgById(ObjectId id) {
		MongoCollection<SystemInfo> mongoCollection = db.getCollection("offlineSystemInfo", SystemInfo.class);
		Bson bson = eq("msgId", id);
		mongoCollection.deleteMany(bson);
		return true;
	}

	public List<SystemInfoDto> getOfflineMsg(String receiverId) {
		MongoCollection<SystemInfo> mongoCollection = db.getCollection("offlineSystemInfo", SystemInfo.class);
		List<SystemInfoDto> list = new ArrayList<>();
		// Bson bson = eq("receiverId", Long.parseLong(receiverId));
		// ArrayList<SystemInfo> into = mongoCollection.find(bson).into(new
		// ArrayList<>());
		ArrayList<SystemInfo> result = mongoCollection.find(and(eq("receiverId", Long.parseLong(receiverId))))
				.into(new ArrayList<>());
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				SystemInfoDto dto = new SystemInfoDto();
				dto.setMsgId(result.get(i).getMsgId().toHexString());
				dto.setOrderId(result.get(i).getOrderId());
				dto.setReceiverId(result.get(i).getReceiverId());
				dto.setUserId(result.get(i).getUserId());
				dto.setTitle(result.get(i).getTitle());
				dto.setContent(result.get(i).getContent());
				dto.setImagePath(result.get(i).getImagePath());
				dto.setType(result.get(i).getType());
				dto.setCreatedAt(result.get(i).getCreatedAt());
				dto.setUpdatedAt(result.get(i).getUpdatedAt());
				dto.setStatus(result.get(i).getStatus());
				list.add(dto);
			}
		}
		Bson bson = eq("receiverId", Long.parseLong(receiverId));
		mongoCollection.deleteMany(bson);
		return list;
		// return into;
	}

}
