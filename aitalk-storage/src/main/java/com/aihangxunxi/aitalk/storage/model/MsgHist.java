package com.aihangxunxi.aitalk.storage.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

/**
 * 消息历史记录
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public class MsgHist extends Msg {

	private ObjectId senderId;// 消息发送方

	private ObjectId receiverId;// 消息接收方

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

}
