package com.aihangxunxi.common.entity;

import com.aihangxunxi.aitalk.im.constant.enums.ClientTypeEnum;
import com.aihangxunxi.aitalk.im.constant.enums.UserTypeEnum;

import java.io.Serializable;

/**
 * 登录用户返回前端的用户信息
 *
 * @author wangchaochao
 * @version 3.0 2020/7/16
 */
public class LoginUserResponseRedisEntity implements Serializable {

	/**
	 * 用户Id
	 */
	private String userId;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 用户类型 AHXX_USER：爱航信息用户；CMS_USER：cms用户；HOTEL_USER：酒店用户
	 */
	private UserTypeEnum userType;

	/**
	 * 客户端类型 AHXX_APP：爱航信息APP；MIN_PROGRAM：微信小程序；AHXX_H5：爱航信息H5
	 */
	private ClientTypeEnum clientType;

	/**
	 * token
	 */
	private String accessToken;

	/**
	 * 刷新token
	 */
	private String refreshToken;

	/**
	 * 是否是第一次登录
	 */
	private Boolean firstLogin;

	/**
	 * 是否是店铺
	 */
	private Boolean existStore;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * token的过期时间
	 */
	private long expiresIn;

	/**
	 * 是否有推广关系
	 */
	private boolean isExtension;

	public boolean isExtension() {
		return isExtension;
	}

	public void setExtension(boolean extension) {
		isExtension = extension;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}

	public ClientTypeEnum getClientType() {
		return clientType;
	}

	public void setClientType(ClientTypeEnum clientType) {
		this.clientType = clientType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Boolean getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public Boolean getExistStore() {
		return existStore;
	}

	public void setExistStore(Boolean existStore) {
		this.existStore = existStore;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

}
