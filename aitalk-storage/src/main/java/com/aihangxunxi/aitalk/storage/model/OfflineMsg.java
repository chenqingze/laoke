package com.aihangxunxi.aitalk.storage.model;

import com.aihangxunxi.aitalk.storage.constant.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 */
public class OfflineMsg {
    @BsonId
    private ObjectId msgId;
    private ObjectId conversationId;
    private ConversationType conversationType;
    private ObjectId senderId;
    private ObjectId receiverId;
    private MsgDirection msgDirection;
    private MsgType msgType;
    private MsgStatus msgStatus;
    private String content;
    private Long createdAt;
    private Long updatedAt;
    private Long revokeAt;
    private ConsultDirection consultDirection;
}
