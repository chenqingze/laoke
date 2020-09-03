package com.aihangxunxi.aitalk.storage.model;

import com.aihangxunxi.aitalk.storage.constant.*;
import com.aihangxunxi.aitalk.storage.model.attachment.MsgAttachment;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * 消息历史记录
 *
 * @author chenqingze107@163.com
 * @version 1.0
 */
public class MsgHist{
    @BsonId
    private ObjectId msgId;// 消息id
    private MsgType msgType;//消息类型
    private MsgDirection msgDirection;//消息方向
    private AttachStatus attachStatus;// 消息附件状态
    private MsgAttachment msgAttachment;// 消息附件
    private String attachStr;// 消息附件文本内容
    private String content; //消息文本内容
    private MsgStatus msgStatus;// 消息状态
    private ConversationType conversationType; // 会话类型
    private ObjectId senderId;// 消息发送方
    private ObjectId receiverId;// 消息接收方
    private Long createdAt;//消息发送时间
    private Long updatedAt;//消息送达时间
    private Long revokeAt;//消息撤回时间
    private ConsultDirection consultDirection;// 咨询方向

}
