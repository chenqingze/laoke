package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.MsgStatus;
import com.aihangxunxi.aitalk.storage.model.MsgHist;
import com.aihangxunxi.aitalk.storage.model.MucHist;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;
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

}
