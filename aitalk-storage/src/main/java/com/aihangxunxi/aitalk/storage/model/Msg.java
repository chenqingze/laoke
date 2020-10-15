package com.aihangxunxi.aitalk.storage.model;

import com.aihangxunxi.aitalk.storage.constant.*;
import com.aihangxunxi.aitalk.storage.model.attachment.MsgAttachment;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * 消息基类
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public abstract class Msg extends BaseModel {

	@BsonId
	private ObjectId msgId;// 消息id

	private MsgType msgType;// 消息类型

	private MsgDirection msgDirection;// 消息方向

	private AttachStatus attachStatus;// 消息附件状态

	private MsgAttachment msgAttachment;// 消息附件

	private String attachStr;// 消息附件文本内容

	private String content; // 消息文本内容

	private MsgStatus msgStatus;// 消息状态

	private ConversationType conversationType; // 会话类型

	private Long senderId;// 消息发送方

	private ObjectId receiverId;// 消息接收方

	private Long createdAt;// 消息发送时间

	private Long updatedAt;// 消息送达时间

	private Long revokeAt;// 消息撤回时间

	private ConsultDirection consultDirection;// 咨询方向

	public ObjectId getMsgId() {
		return msgId;
	}

	public void setMsgId(ObjectId msgId) {
		this.msgId = msgId;
	}

	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	public MsgDirection getMsgDirection() {
		return msgDirection;
	}

	public void setMsgDirection(MsgDirection msgDirection) {
		this.msgDirection = msgDirection;
	}

	public AttachStatus getAttachStatus() {
		return attachStatus;
	}

	public void setAttachStatus(AttachStatus attachStatus) {
		this.attachStatus = attachStatus;
	}

	public MsgAttachment getMsgAttachment() {
		return msgAttachment;
	}

	public void setMsgAttachment(MsgAttachment msgAttachment) {
		this.msgAttachment = msgAttachment;
	}

	public String getAttachStr() {
		return attachStr;
	}

	public void setAttachStr(String attachStr) {
		this.attachStr = attachStr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MsgStatus getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(MsgStatus msgStatus) {
		this.msgStatus = msgStatus;
	}

	public ConversationType getConversationType() {
		return conversationType;
	}

	public void setConversationType(ConversationType conversationType) {
		this.conversationType = conversationType;
	}

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

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getRevokeAt() {
		return revokeAt;
	}

	public void setRevokeAt(Long revokeAt) {
		this.revokeAt = revokeAt;
	}

	public ConsultDirection getConsultDirection() {
		return consultDirection;
	}

	public void setConsultDirection(ConsultDirection consultDirection) {
		this.consultDirection = consultDirection;
	}

}
