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

	private String userHeadPortrait;

	private String userNickname;

	private String phone;

	private String certificationReal;

	private String certificationStore;

	private String certificationBarter;

	private String userStatus;

	private String autograph;

	private String barterQualificationType;

	private String barterQualificationTypeIcon;

	private String barterQualificationTypeName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserHeadPortrait() {
		return userHeadPortrait;
	}

	public void setUserHeadPortrait(String userHeadPortrait) {
		this.userHeadPortrait = userHeadPortrait;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
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

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public String getBarterQualificationType() {
		return barterQualificationType;
	}

	public void setBarterQualificationType(String barterQualificationType) {
		this.barterQualificationType = barterQualificationType;
	}

	public String getBarterQualificationTypeIcon() {
		return barterQualificationTypeIcon;
	}

	public void setBarterQualificationTypeIcon(String barterQualificationTypeIcon) {
		this.barterQualificationTypeIcon = barterQualificationTypeIcon;
	}

	public String getBarterQualificationTypeName() {
		return barterQualificationTypeName;
	}

	public void setBarterQualificationTypeName(String barterQualificationTypeName) {
		this.barterQualificationTypeName = barterQualificationTypeName;
	}

}
