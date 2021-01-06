package com.aihangxunxi.aitalk.storage.model;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class SysUserConcern {

	private ObjectId id;

	// 用户id
	private Long user_id;

	// 被关注用户id
	private Long concerned_user_id;

	// 关注时间
	private LocalDateTime concern_time;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getConcerned_user_id() {
		return concerned_user_id;
	}

	public void setConcerned_user_id(Long concerned_user_id) {
		this.concerned_user_id = concerned_user_id;
	}

	public LocalDateTime getConcern_time() {
		return concern_time;
	}

	public void setConcern_time(LocalDateTime concern_time) {
		this.concern_time = concern_time;
	}

}
