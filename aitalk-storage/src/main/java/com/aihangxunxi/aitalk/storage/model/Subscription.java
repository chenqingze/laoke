package com.aihangxunxi.aitalk.storage.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class Subscription {

	// 店铺id
	@JsonSerialize(using = ToStringSerializer.class)
	private Long subscriber;

	// 粉丝id
	@JsonSerialize(using = ToStringSerializer.class)
	private Long publisher;

	// 创建时间
	private Long createdAt;

	// 更新时间
	private Long updatedAt;

	public Long getPublisher() {
		return publisher;
	}

	public void setPublisher(Long publisher) {
		this.publisher = publisher;
	}

	public Long getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Long subscriber) {
		this.subscriber = subscriber;
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
