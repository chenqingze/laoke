package com.aihangxunxi.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * redis存放的用户头像、昵称、店铺相关信息
 *
 * @author wangchaochao
 * @version 3.0 2020/4/11
 */
public class UserRedisEntity implements Serializable {

	/**
	 * userId
	 */
	private Long userId;

	/**
	 * 用户头像
	 */
	private String userHeadPortrait;

	/**
	 * 用户昵称
	 */
	private String userNickname;

	/**
	 * 用户手机号
	 */
	private String phone;

	/**
	 * 实名状态
	 */
	private String certificationReal;

	/**
	 * 用户状态
	 */
	private String userStatus;

	/**
	 * 店铺信息
	 */
	private StoreRedisEntity storeRedisEntity;

	/**
	 * 用户的身份
	 */
	private List<UserIdentityEntity> userIdentityEntities;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCertificationReal() {
		return certificationReal;
	}

	public void setCertificationReal(String certificationReal) {
		this.certificationReal = certificationReal;
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

	public StoreRedisEntity getStoreRedisEntity() {
		return storeRedisEntity;
	}

	public void setStoreRedisEntity(StoreRedisEntity storeRedisEntity) {
		this.storeRedisEntity = storeRedisEntity;
	}

	public List<UserIdentityEntity> getUserIdentityEntities() {
		return userIdentityEntities;
	}

	public void setUserIdentityEntities(List<UserIdentityEntity> userIdentityEntities) {
		this.userIdentityEntities = userIdentityEntities;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

}
