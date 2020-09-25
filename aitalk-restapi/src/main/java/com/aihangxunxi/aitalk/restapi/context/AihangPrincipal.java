package com.aihangxunxi.aitalk.restapi.context;

import java.io.Serializable;

/**
 * @author wangchaochao
 * @version 3.0 2020/4/13
 */
public class AihangPrincipal implements Serializable {

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 登录用户名
	 */
	private String userName;

	/**
	 * 用户类型 userType
	 */
	private String userType;

	/**
	 * 用户token信息 feign请求时读取该token信息
	 */
	private String token;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
