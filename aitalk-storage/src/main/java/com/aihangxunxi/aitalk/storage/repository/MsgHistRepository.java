package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.ConsultDirection;
import com.aihangxunxi.aitalk.storage.constant.MsgStatus;
import com.aihangxunxi.aitalk.storage.model.MsgHist;
import com.aihangxunxi.aitalk.storage.model.MucHist;
import com.aihangxunxi.aitalk.storage.model.OfflineMsg;
import com.aihangxunxi.aitalk.storage.model.User;
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

import static com.mongodb.client.model.Filters.eq;
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

	// 根据消息id 获取昵称
	public String querySenderNicknameByMsgId(String msgId) {
		if (getOfflentMsgById(new ObjectId(msgId))) {
			// 当前为离线消息
			MongoCollection<OfflineMsg> mongoCollection = aitalkDb.getCollection("offlineMsg", OfflineMsg.class);
			Bson bson = eq(new ObjectId(msgId));
			OfflineMsg offlineMsg = mongoCollection.find(bson).first();
			MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);
			User user = userMongoCollection.find(eq(offlineMsg.getSenderId())).first();
			return user.getNickname();
		}
		else {
			// 当前为在线消息
			MongoCollection<MsgHist> mongoCollection = aitalkDb.getCollection("msgHist", MsgHist.class);
			Bson bson = eq(new ObjectId(msgId));
			MsgHist msgHist = mongoCollection.find(bson).first();
			MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);
			User user = userMongoCollection.find(eq(msgHist.getSenderId())).first();
			return user.getNickname();
		}
	}

	// 根据消息id 获取昵称
	public User querySenderDeviceByMsgId(String msgId) {
		if (getOfflentMsgById(new ObjectId(msgId))) {
			// 当前为离线消息
			MongoCollection<OfflineMsg> mongoCollection = aitalkDb.getCollection("offlineMsg", OfflineMsg.class);
			Bson bson = eq(new ObjectId(msgId));
			OfflineMsg offlineMsg = mongoCollection.find(bson).first();
			MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);
			User user = userMongoCollection.find(eq(offlineMsg.getReceiverId())).first();
			return user;
		}
		else {
			// 当前为在线消息
			MongoCollection<MsgHist> mongoCollection = aitalkDb.getCollection("msgHist", MsgHist.class);
			Bson bson = eq(new ObjectId(msgId));
			MsgHist msgHist = mongoCollection.find(bson).first();
			MongoCollection<User> userMongoCollection = aitalkDb.getCollection("user", User.class);
			User user = userMongoCollection.find(eq(msgHist.getReceiverId())).first();
			return user;
		}
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

	public boolean deleteOfflineMsgById(ObjectId id) {
		MongoCollection<OfflineMsg> mongoCollection = aitalkDb.getCollection("offlineMsg", OfflineMsg.class);
		Bson bson = eq("_id", id);
		mongoCollection.deleteMany(bson);
		return true;
	}

	public MsgHist getMsgHistById(ObjectId id) {
		MongoCollection<MsgHist> mongoCollection = aitalkDb.getCollection("msgHist", MsgHist.class);
		Bson bson = eq("_id", id);
		return mongoCollection.find(bson).first();
	}

	// 根据msgId 查询离线消息
	public boolean getOfflentMsgById(ObjectId id) {
		MongoCollection<OfflineMsg> mongoCollection = aitalkDb.getCollection("offlineMsg", OfflineMsg.class);
		Bson bson = eq("_id", id);
		return mongoCollection.countDocuments(bson) > 0;
	}

	// 编辑离线消息
	public void editOfflienMsg(String msgId) {
		if (getOfflentMsgById(new ObjectId(msgId))) {
			// 消息列表存在直接修改
			withdrawConsultOfflienMsg(msgId);
		}
		else {
			MsgHist msgHist = getMsgHistById(new ObjectId(msgId));
			// 将方向进行发转
			msgHist.setConsultDirection(getConsultDirection(msgHist.getConsultDirection()));
			saveOfflineMsgHist(msgHist);
		}

	}

	// 反转咨询方向
	private ConsultDirection getConsultDirection(ConsultDirection consultDirection) {
		ConsultDirection consultDirectionR;
		switch (consultDirection) {
		case PSO:
			consultDirectionR = ConsultDirection.SPI;
			break;
		case PPO:
			consultDirectionR = ConsultDirection.PPI;
			break;
		case SPO:
			consultDirectionR = ConsultDirection.PSI;
			break;
		default:
			throw new IllegalStateException("Unexpected value: " + consultDirection);
		}
		return consultDirectionR;
	}

}
