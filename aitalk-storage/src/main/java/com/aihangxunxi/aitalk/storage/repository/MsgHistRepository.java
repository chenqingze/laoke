package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.MsgStatus;
import com.aihangxunxi.aitalk.storage.model.MsgHist;
import com.aihangxunxi.aitalk.storage.model.MucHist;
import com.aihangxunxi.aitalk.storage.model.OfflineMsg;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;

@Repository
public class MsgHistRepository {

	@Resource
	private MongoDatabase aitalkDb;

	public boolean saveMsgHist(MsgHist msgHist) {
		// 保存至主表
		MongoCollection<MsgHist> mongoCollection = aitalkDb.getCollection("msgHist", MsgHist.class);
		InsertOneResult result = mongoCollection.insertOne(msgHist);
		return true;
	}

	public void updateMsgStatusByMsgId(ObjectId msgId, MsgStatus msgStatus) {
		msgId = Optional.of(msgId).get();
		msgStatus = Optional.of(msgStatus).get();

		MongoCollection<MsgHist> mongoCollection = aitalkDb.getCollection("msgHist", MsgHist.class);
		mongoCollection.updateOne(eq("msgId", msgId), set("msgStatus", msgStatus));

	}

	public boolean saveOfflineMsgHist(MsgHist msgHist) {
		MongoCollection<MsgHist> mongoCollection = aitalkDb.getCollection("offlineMsg", MsgHist.class);
		InsertOneResult result = mongoCollection.insertOne(msgHist);
		return true;
	}

	// 撤回咨询消息
	public boolean withdrawConsultMsg(String msgId) {
		MongoCollection<MucHist> mongoCollection = aitalkDb.getCollection("msgHist", MucHist.class);
		Bson bson = eq(new ObjectId(msgId));

		List<Bson> list = new ArrayList<>();
		list.add(set("msgStatus", MsgStatus.WITHDRAW));
		list.add(set("content", "对方撤回了一条消息"));
		list.add(set("revokeAt", new Date().getTime()));
		mongoCollection.updateOne(bson, list);
		return true;
	}

	// //
	// public List<MsgHist> getOfflineLastMsg(ObjectId receiverId, String
	// consultDirection) {
	// MongoCollection<MsgHist> mongoCollection = aitalkDb.getCollection("offlineMsg",
	// MsgHist.class);
	// Bson bson = and(eq("receiverId", receiverId), eq("consultDirection", "PSI"));
	// return mongoCollection.find(bson).into(new ArrayList<>());
	// }

	// 根据receiverId获取离线消息并将其删除
	public List<OfflineMsg> getOfflineMsg(ObjectId receiverId) {
		MongoCollection<OfflineMsg> mongoCollection = aitalkDb.getCollection("offlineMsg", OfflineMsg.class);
		Bson bson = eq("receiverId", receiverId);
		return mongoCollection.find(bson).into(new ArrayList<>());
	}

	// 根据receiverId 删除离线消息
	public boolean deleteOfflineMsg(ObjectId receiverId) {
		MongoCollection<MsgHist> mongoCollection = aitalkDb.getCollection("offlineMsg", MsgHist.class);
		Bson bson = eq("receiverId", receiverId);
		mongoCollection.deleteMany(bson);
		return true;
	}

	// 撤回离线消息中数据
    public boolean withdrawConsultOfflienMsg(String msgId) {
		MongoCollection<OfflineMsg> mongoCollection = aitalkDb.getCollection("offlineMsg", OfflineMsg.class);
		Bson bson = eq(new ObjectId(msgId));

		List<Bson> list = new ArrayList<>();
		list.add(set("msgStatus", MsgStatus.WITHDRAW));
		list.add(set("content", "对方撤回了一条消息"));
		list.add(set("revokeAt", new Date().getTime()));
		mongoCollection.updateOne(bson, list);
		return true;
    }
}
