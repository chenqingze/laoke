package com.aihangxunxi.aitalk.storage.model;

public class Disturb {

	private Long userId;

	private Long currentUser;

	private Long createdAt;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Long currentUser) {
		this.currentUser = currentUser;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

}
