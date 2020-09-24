package com.aihangxunxi.aitalk.storage.model;

import org.bson.types.ObjectId;

public class GroupMember extends BaseModel {

	private ObjectId id;

	private ObjectId groupId;

	private ObjectId memberId;

	private Long userId;

	private String alias;

	private boolean isBlocked;

	private boolean isMute;

	private ObjectId lastAckMsgId;

	private Long lastAckMsgTime;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getGroupId() {
		return groupId;
	}

	public void setGroupId(ObjectId groupId) {
		this.groupId = groupId;
	}

	public ObjectId getMemberId() {
		return memberId;
	}

	public void setMemberId(ObjectId memberId) {
		this.memberId = memberId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean blocked) {
		isBlocked = blocked;
	}

	public boolean isMute() {
		return isMute;
	}

	public void setMute(boolean mute) {
		isMute = mute;
	}

	public ObjectId getLastAckMsgId() {
		return lastAckMsgId;
	}

	public void setLastAckMsgId(ObjectId lastAckMsgId) {
		this.lastAckMsgId = lastAckMsgId;
	}

	public Long getLastAckMsgTime() {
		return lastAckMsgTime;
	}

	public void setLastAckMsgTime(Long lastAckMsgTime) {
		this.lastAckMsgTime = lastAckMsgTime;
	}

}
