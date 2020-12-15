package com.aihangxunxi.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * redis存放的店铺信息
 *
 * @author wangchaohchao
 * @version 3.0 2020/4/11
 */
public class StoreRedisEntity implements Serializable {

	/**
	 * 店铺ID
	 */
	private Long storeId;

	/**
	 * 店铺头像
	 */
	private String storeHeadPortrait;

	/**
	 * 店铺头像
	 */
	private String storeNo;

	/**
	 * 店铺名称
	 */
	private String storeName;

	/**
	 * 店铺认证状态
	 */
	private String storeAuthStatus;

	/**
	 * 店铺认证到期时间
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate storeAuthExpire;

	/**
	 * 店铺状态
	 */
	private String storeStatus;

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreHeadPortrait() {
		return storeHeadPortrait;
	}

	public void setStoreHeadPortrait(String storeHeadPortrait) {
		this.storeHeadPortrait = storeHeadPortrait;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreAuthStatus() {
		return storeAuthStatus;
	}

	public void setStoreAuthStatus(String storeAuthStatus) {
		this.storeAuthStatus = storeAuthStatus;
	}

	public LocalDate getStoreAuthExpire() {
		return storeAuthExpire;
	}

	public void setStoreAuthExpire(LocalDate storeAuthExpire) {
		this.storeAuthExpire = storeAuthExpire;
	}

	public String getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

}
