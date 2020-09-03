package com.aihangxunxi.aitalk.storage.domain.user.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 */

public class User {

	private ObjectId _id;

	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;

	private String status;

	private String nickname;

	private String profile;

	private String phone;

//	private List<UserGroups> groups;

	private List<Long> followings;

	private List<Long> blocklist;

	private String initial;

	private String deviceId;

	private String device;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

//	public List<UserGroups> getGroups() {
//		return groups;
//	}
//
//	public void setGroups(List<UserGroups> groups) {
//		this.groups = groups;
//	}

	public List<Long> getFollowings() {
		return followings;
	}

	public void setFollowings(List<Long> followings) {
		this.followings = followings;
	}

	public List<Long> getBlocklist() {
		return blocklist;
	}

	public void setBlocklist(List<Long> blocklist) {
		this.blocklist = blocklist;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}

}
