package com.aihangxunxi.aitalk.storage.model;

import org.bson.types.ObjectId;

/**
 * 消息历史记录
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public class MsgHist extends Msg {

	private Long senderId;// 消息发送方

	private ObjectId receiverId;// 消息接收方

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public ObjectId getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(ObjectId receiverId) {
		this.receiverId = receiverId;
	}

}
