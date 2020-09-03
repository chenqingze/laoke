package com.aihangxunxi.aitalk.storage.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * 消息历史记录
 *
 * @author chenqingze107@163.com
 * @version 1.0
 */
public class MsgHist {
    @BsonId
    private ObjectId msgId;
    private ObjectId conversationId;
    private conversationType;

}
