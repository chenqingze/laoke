package com.aihangxunxi.aitalk.storage.repository;

import com.aihangxunxi.aitalk.storage.model.Group;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupRepository {

    @Resource
    private MongoDatabase aitalkDb;

    public List<Group> queryUserGroups(Long userId) {
        MongoCollection<Group> groupMongoCollection = aitalkDb.getCollection("group", Group.class);
        return groupMongoCollection.find().into(new ArrayList<>());

    }

}
