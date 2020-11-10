package com.aihangxunxi.aitalk.storage.model;

import com.aihangxunxi.aitalk.storage.constant.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
public class User extends BaseModel {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;

	private String idStr;

	private Long userId;

	private Gender gender;

	private String nickname;

	private String phone;

	private List<UserGroup> userGroups;

	private List<ObjectId> followings;

	private String deviceCode;

	private DeviceIdiom deviceIdiom;

	private DevicePlatform devicePlatform;

	private String header;

	private String pinyin;

	private UserType userType;

	private UserStatus userStatus;

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public User() {
	}

	private class UserGroup {

		private ObjectId id;

		private String alias;

		private ObjectId lastAckMsgId;

		private Long lastAckMsgTime;

	}

	@JsonSerialize(using = ToStringSerializer.class)
	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public List<ObjectId> getFollowings() {
		return followings;
	}

	public void setFollowings(List<ObjectId> followings) {
		this.followings = followings;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public DeviceIdiom getDeviceIdiom() {
		return deviceIdiom;
	}

	public void setDeviceIdiom(DeviceIdiom deviceIdiom) {
		this.deviceIdiom = deviceIdiom;
	}

	public DevicePlatform getDevicePlatform() {
		return devicePlatform;
	}

	public void setDevicePlatform(DevicePlatform devicePlatform) {
		this.devicePlatform = devicePlatform;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

}
