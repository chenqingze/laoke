package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.MsgHist;
import com.aihangxunxi.aitalk.storage.model.SystemInfo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author: suguodong
 * Date:  2020/11/4 16:45
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
}
