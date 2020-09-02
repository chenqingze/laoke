package com.aihangxunxi.aitalk.storage.domain.msg.model;

import com.aihangxunxi.aitalk.storage.domain.msg.constant.MsgType;
import org.bson.types.ObjectId;

public class LastChat {

	private ObjectId _id;

	private String recipientId;// 接收者id

	private Long senderId; // 发送者id

	private String content;// 内容

	private String msgId;// msgid

	private Long timeSend;// 发送时间

	private MsgType msgType;// 消息类型

	private LastChat(Builder builder) {
		set_id(builder._id);
		setRecipientId(builder.recipientId);
		setSenderId(builder.senderId);
		setContent(builder.content);
		setMsgId(builder.msgId);
		setTimeSend(builder.timeSend);
		setMsgType(builder.msgType);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Long getTimeSend() {
		return timeSend;
	}

	public void setTimeSend(Long timeSend) {
		this.timeSend = timeSend;
	}

	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	public static final class Builder {

		private ObjectId _id;

		private String recipientId;

		private Long senderId;

		private String content;

		private String msgId;

		private Long timeSend;

		private MsgType msgType;

		private Builder() {
		}

		public Builder with_id(ObjectId val) {
			_id = val;
			return this;
		}

		public Builder withRecipientId(String val) {
			recipientId = val;
			return this;
		}

		public Builder withSenderId(Long val) {
			senderId = val;
			return this;
		}

		public Builder withContent(String val) {
			content = val;
			return this;
		}

		public Builder withMsgId(String val) {
			msgId = val;
			return this;
		}

		public Builder withTimeSend(Long val) {
			timeSend = val;
			return this;
		}

		public Builder withMsgType(MsgType val) {
			msgType = val;
			return this;
		}

		public LastChat build() {
			return new LastChat(this);
		}

	}

}
