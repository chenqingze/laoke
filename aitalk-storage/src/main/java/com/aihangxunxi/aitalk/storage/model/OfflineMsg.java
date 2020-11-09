package com.aihangxunxi.aitalk.storage.model;

import com.aihangxunxi.aitalk.storage.constant.*;
import com.aihangxunxi.aitalk.storage.model.attachment.MsgAttachment;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
public class OfflineMsg extends Msg {

	private ObjectId senderId;// 消息发送方

	private ObjectId receiverId;// 消息接收方

	private Integer msgTypeInt;

	private Integer msgStatusInt;

	private Integer conversationTypeInt;

	@JsonSerialize(using = ToStringSerializer.class)
	public ObjectId getSenderId() {
		return senderId;
	}

	public void setSenderId(ObjectId senderId) {
		this.senderId = senderId;
	}

	@JsonSerialize(using = ToStringSerializer.class)
	public ObjectId getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(ObjectId receiverId) {
		this.receiverId = receiverId;
	}

	public Integer getMsgTypeInt() {
		return msgTypeInt;
	}

	public void setMsgTypeInt(Integer msgTypeInt) {
		this.msgTypeInt = msgTypeInt;
	}

	public Integer getMsgStatusInt() {
		return msgStatusInt;
	}

	public void setMsgStatusInt(Integer msgStatusInt) {
		this.msgStatusInt = msgStatusInt;
	}

	public Integer getConversationTypeInt() {
		return conversationTypeInt;
	}

	public void setConversationTypeInt(Integer conversationTypeInt) {
		this.conversationTypeInt = conversationTypeInt;
	}

}
