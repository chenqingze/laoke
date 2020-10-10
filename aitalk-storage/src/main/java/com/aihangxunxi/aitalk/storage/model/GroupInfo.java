package com.aihangxunxi.aitalk.storage.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.List;

public class GroupInfo {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;

	private String name;

	private String header;

	private Long owner;

	private String groupNo;

	private String notice;

	private boolean isGroupOwner;

	private String userNickname;

	private boolean top;

	private boolean mute;

	private List<User> users;

	private int memberCount;

	private boolean confirmJoin;

	public boolean isConfirmJoin() {
		return confirmJoin;
	}

	public void setConfirmJoin(boolean confirmJoin) {
		this.confirmJoin = confirmJoin;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public boolean isGroupOwner() {
		return isGroupOwner;
	}

	public void setGroupOwner(boolean groupOwner) {
		isGroupOwner = groupOwner;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

}
