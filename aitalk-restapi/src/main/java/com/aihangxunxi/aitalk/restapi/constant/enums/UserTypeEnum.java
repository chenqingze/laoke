package com.aihangxunxi.aitalk.restapi.constant.enums;

/**
 * 用户类型
 * @author wangchaochao
 * @version 3.0 2020/7/16
 */
public enum UserTypeEnum implements BaseEnum {

	AHXX_USER("ahxx", "爱航信息用户"),

	CMS_USER("cms", "cms用户"),

	HOTEL_USER("hotel", "酒店用户");

	private String code;

	private String value;

	UserTypeEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getValue() {
		return value;
	}

}
