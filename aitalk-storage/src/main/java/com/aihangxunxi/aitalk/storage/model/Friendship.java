package com.aihangxunxi.aitalk.storage.model;

import org.bson.types.ObjectId;

/**
 * 好友关系
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public class Friendship extends BaseModel {

	private ObjectId id;

	private Long userId;

	private Long friendId;

	private String alias;

	private Boolean isBlocked;

	private Boolean isMute;

	private Long createdAt;

	private Long updatedAt;

	private String header;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Boolean getBlocked() {
		return isBlocked;
	}

	public void setBlocked(Boolean blocked) {
		isBlocked = blocked;
	}

	public Boolean getMute() {
		return isMute;
	}

	public void setMute(Boolean mute) {
		isMute = mute;
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

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
}
