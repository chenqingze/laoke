package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.MsgStatus;
import com.aihangxunxi.aitalk.storage.model.MucHist;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;

@Repository
public class MucHistRepository {

	@Resource
	private MongoDatabase aitalkDb;

	// 保存群聊天记录
	public boolean saveMucHist(MucHist mucHist) {
		// 保存至主表
		MongoCollection<MucHist> mongoCollection = aitalkDb.getCollection("mucHist", MucHist.class);
		mongoCollection.insertOne(mucHist);
		return true;

	}

	// 撤回消息
	public boolean withdrawMucMsg(String msgId) {
		MongoCollection<MucHist> mongoCollection = aitalkDb.getCollection("muchist", MucHist.class);

		Bson bson = eq(new ObjectId(msgId));
		Bson bson1 = set("msgStatus", MsgStatus.WITHDRAW);
		mongoCollection.updateOne(bson, bson1);

		return true;
	}

	public List<MucHist> queryMucHist(Long lastChatTime, ObjectId groupId) {
		MongoCollection<MucHist> mongoCollection = aitalkDb.getCollection("muchist", MucHist.class);
		Bson bson = and(gt("createdAt", lastChatTime), eq("receiverId", groupId));

		return mongoCollection.find(bson).into(new ArrayList<>());
	}

}
