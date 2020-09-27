package com.aihangxunxi.aitalk.storage.model;

public class Friend {

	private String id;

	private Long userId;

	private Long friendId;

	private String friendName;

	private String alias;

	private Integer isBlocked;

	private Integer isMute;

	private Integer isStickOnTop;

	private Long createdAt;

	private Long updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Integer isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Integer getIsMute() {
		return isMute;
	}

	public void setIsMute(Integer isMute) {
		this.isMute = isMute;
	}

	public Integer getIsStickOnTop() {
		return isStickOnTop;
	}

	public void setIsStickOnTop(Integer isStickOnTop) {
		this.isStickOnTop = isStickOnTop;
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

}
