package com.aihangxunxi.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * redis存放用户的身份
 *
 * @author liuzx
 * @version 3.0 2020/4/16
 */
public class UserIdentityEntity implements Serializable {

	/**
	 * 身份名称
	 */
	private String identityName;

	/**
	 * 认证状态--un_audit:待审核;pass:审核通过;unpass:审核未通过
	 */
	private String identityStatus;

	/**
	 * 身份认证过期时间
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate identityExpireTime;

	public String getIdentityName() {
		return identityName;
	}

	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	public LocalDate getIdentityExpireTime() {
		return identityExpireTime;
	}

	public void setIdentityExpireTime(LocalDate identityExpireTime) {
		this.identityExpireTime = identityExpireTime;
	}

	public String getIdentityStatus() {
		return identityStatus;
	}

	public void setIdentityStatus(String identityStatus) {
		this.identityStatus = identityStatus;
	}

}
