package com.aihangxunxi.aitalk.storage.domain.team.model;

import org.bson.types.ObjectId;

import java.io.Serializable;

public class TeamUserCollection implements Serializable {

	// 数据ID
	private ObjectId _id;

	// 用户id
	private Long userId;

	// 群聊id
	private String teamId;

	// 我在本群的昵称
	private String userName;

	// 是否保存通讯录
	private boolean saveContact;

	// 背景图
	private String background;

	public ObjectId get_id() {
		return _id;
	}

	public String getUserName() {
		return userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isSaveContact() {
		return saveContact;
	}

	public void setSaveContact(boolean saveContact) {
		this.saveContact = saveContact;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

}
