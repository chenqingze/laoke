package com.aihangxunxi.aitalk.storage.domain.msg.model;

import com.aihangxunxi.aitalk.storage.constant.*;
import com.aihangxunxi.aitalk.storage.domain.msg.attachment.MsgAttachment;
import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 */
public class Msg implements Serializable {

	private ObjectId id;// 服务端消息id,用于服务端存储，全局唯一

	private String msgId;// 由客户端生成，用于本地存储。如消息不需要存储msgId 为0

	private long fromId;// 消息发送方的帐号

	private ConversationType conversationType;// 会话类型

	private String sessionId;// 会话id

	private MsgDirection msgDirection;// 消息方向

	private MsgAttachment msgAttachment;// 消息附件

	private MsgType msgType;// 消息类型

	private String attachStr;// 消息附件文本内容

	private AttachStatus attachStatus;// 消息附件状态

	private MsgStatus msgStatus;// 消息状态

	private String content;// 消息内容

	private String fromNick; // 发送者的昵称

	private String toId; // 聊天对象的Id（好友帐号，群ID等)

	private int fromClientType; // 客户端类型

	private int time; // 消息时间,精确到秒

	private int teamMsgAckCount;// 群消息已读回执的已读数

	private int teamMsgUnAckCount;// 返回群消息已读回执的未读数

	private boolean isRemoteRead;// 自己发送的消息对方是否已读

	private boolean hasSendAck;// 是否已经发送过已读回执

	private boolean isInBlackList;// 是不是被对方拉黑了

	private boolean needMsgAck;// 自己发送的消息对方是否已读

	public Msg() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public long getFromId() {
		return fromId;
	}

	public void setFromId(long fromId) {
		this.fromId = fromId;
	}

	public ConversationType getSessionType() {
		return conversationType;
	}

	public void setSessionType(ConversationType conversationType) {
		this.conversationType = conversationType;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public MsgDirection getMsgDirection() {
		return msgDirection;
	}

	public void setMsgDirection(MsgDirection msgDirection) {
		this.msgDirection = msgDirection;
	}

	public MsgAttachment getMsgAttachment() {
		return msgAttachment;
	}

	public void setMsgAttachment(MsgAttachment msgAttachment) {
		this.msgAttachment = msgAttachment;
	}

	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	public String getAttachStr() {
		return attachStr;
	}

	public void setAttachStr(String attachStr) {

		this.attachStr = attachStr;
	}

	public AttachStatus getAttachStatus() {
		return attachStatus;
	}

	public void setAttachStatus(AttachStatus attachStatus) {
		this.attachStatus = attachStatus;
	}

	public MsgStatus getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(MsgStatus msgStatus) {
		this.msgStatus = msgStatus;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFromNick() {
		return fromNick;
	}

	public void setFromNick(String fromNick) {
		this.fromNick = fromNick;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public int getFromClientType() {
		return fromClientType;
	}

	public void setFromClientType(int fromClientType) {
		this.fromClientType = fromClientType;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTeamMsgAckCount() {
		return teamMsgAckCount;
	}

	public void setTeamMsgAckCount(int teamMsgAckCount) {
		this.teamMsgAckCount = teamMsgAckCount;
	}

	public int getTeamMsgUnAckCount() {
		return teamMsgUnAckCount;
	}

	public void setTeamMsgUnAckCount(int teamMsgUnAckCount) {
		this.teamMsgUnAckCount = teamMsgUnAckCount;
	}

	public boolean isRemoteRead() {
		return isRemoteRead;
	}

	public void setRemoteRead(boolean remoteRead) {
		isRemoteRead = remoteRead;
	}

	public boolean isHasSendAck() {
		return hasSendAck;
	}

	public void setHasSendAck(boolean hasSendAck) {
		this.hasSendAck = hasSendAck;
	}

	public boolean isInBlackList() {
		return isInBlackList;
	}

	public void setInBlackList(boolean inBlackList) {
		isInBlackList = inBlackList;
	}

	public boolean isNeedMsgAck() {
		return needMsgAck;
	}

	public void setNeedMsgAck(boolean needMsgAck) {
		this.needMsgAck = needMsgAck;
	}

	public void setNeedMsgAck() {
		this.setNeedMsgAck(true);
	}

	/**
	 * todo: 完善
	 * @param msg 消息对象
	 * @return
	 */

	public boolean isTheSame(Msg msg) {
		return false;
	}

}
