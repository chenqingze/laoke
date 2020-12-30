package com.aihangxunxi.common.entity;

import java.io.Serializable;

/**
 * redis存放用户
 *
 * @author liuzx
 * @version 3.0 2020/4/16
 */

public class UserRedisEntity implements Serializable {

	private Long userId;

	private String headImgPath;

	private String nickname;

	private String phone;

	private String certificationReal;

	private String certificationStore;

	private String certificationBarter;

	private String userStatus;

	private String value;

	private String remark;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getHeadImgPath() {
		return headImgPath;
	}

	public void setHeadImgPath(String headImgPath) {
		this.headImgPath = headImgPath;
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

	public String getCertificationReal() {
		return certificationReal;
	}

	public void setCertificationReal(String certificationReal) {
		this.certificationReal = certificationReal;
	}

	public String getCertificationStore() {
		return certificationStore;
	}

	public void setCertificationStore(String certificationStore) {
		this.certificationStore = certificationStore;
	}

	public String getCertificationBarter() {
		return certificationBarter;
	}

	public void setCertificationBarter(String certificationBarter) {
		this.certificationBarter = certificationBarter;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
