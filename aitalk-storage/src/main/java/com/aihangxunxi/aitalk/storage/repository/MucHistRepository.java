package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.MucHist;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

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

}
