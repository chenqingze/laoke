package com.aihangxunxi.aitalk.storage.domain.team.model;

import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 */
public class Team implements Serializable {

	// mongoId
	private ObjectId _id;

	// teamId
	private String teamId;

	// 群主id
	private Long teamLeader;

	// 群名
	private String name;

	// 公告
	private String notice;

	// 加入群聊用
	private Long joinUser;

	public Long getJoinUser() {
		return joinUser;
	}

	public void setJoinUser(Long joinUser) {
		this.joinUser = joinUser;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Long getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(Long teamLeader) {
		this.teamLeader = teamLeader;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

}
