package com.aihangxunxi.aitalk.storage.model;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class ConcernStore {

	private ObjectId id;

	private Long user_id;

	private Long store_id;

	private LocalDateTime concern_time;

	private Long store_user_id;

	private String user_nickname;

	private String user_header;

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

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public LocalDateTime getConcern_time() {
		return concern_time;
	}

	public void setConcern_time(LocalDateTime concern_time) {
		this.concern_time = concern_time;
	}

	public Long getStore_user_id() {
		return store_user_id;
	}

	public void setStore_user_id(Long store_user_id) {
		this.store_user_id = store_user_id;
	}

	public String getUser_nickname() {
		return user_nickname;
	}

	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}

	public String getUser_header() {
		return user_header;
	}

	public void setUser_header(String user_header) {
		this.user_header = user_header;
	}

}
