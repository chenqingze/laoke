package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.constant.MsgStatus;
import com.aihangxunxi.aitalk.storage.model.GroupMember;
import com.aihangxunxi.aitalk.storage.model.MucHist;
import com.aihangxunxi.aitalk.storage.utils.PushUtils;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;

@Repository
public class MucHistRepository {

	private static final Logger logger = LoggerFactory.getLogger(PushUtils.class);

	@Resource
	private MongoDatabase aitalkDb;

	@Resource
	private PushUtils pushUtils;

	// 保存群聊天记录
	public boolean saveMucHist(MucHist mucHist) throws Exception {
		// 保存至主表
		MongoCollection<MucHist> mongoCollection = aitalkDb.getCollection("mucHist", MucHist.class);
		mongoCollection.insertOne(mucHist);
		MongoCollection<GroupMember> groupMemberMongoCollection = aitalkDb.getCollection("groupMember",
				GroupMember.class);

		Bson bson = eq("groupId", mucHist.getReceiverId());
		List<GroupMember> list = groupMemberMongoCollection.find(bson).into(new ArrayList<>());
		list.stream().forEach(groupMember -> {
			try {
				pushUtils.pushMsg(groupMember.getUserId(), mucHist.getMsgId().toHexString(), mucHist.getContent(),
						String.valueOf(mucHist.getMsgType().ordinal()), mucHist.getSenderId(),
						mucHist.getReceiverId().toHexString());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
		return true;

	}

	// 撤回消息
	public boolean withdrawMucMsg(String msgId) {
		MongoCollection<MucHist> mongoCollection = aitalkDb.getCollection("mucHist", MucHist.class);

		Bson bson = eq(new ObjectId(msgId));
		Bson bson1 = set("msgStatus", MsgStatus.WITHDRAW);
		mongoCollection.updateOne(bson, bson1);

		return true;
	}

	public List<MucHist> queryMucHist(Long lastChatTime, ObjectId groupId) {
		MongoCollection<MucHist> mongoCollection = aitalkDb.getCollection("mucHist", MucHist.class);
		Bson bson = eq("receiverId", groupId);
		if (lastChatTime != null) {
			bson = and(gt("createdAt", lastChatTime), eq("receiverId", groupId));
		}
		return mongoCollection.find(bson).into(new ArrayList<>());
	}

}
