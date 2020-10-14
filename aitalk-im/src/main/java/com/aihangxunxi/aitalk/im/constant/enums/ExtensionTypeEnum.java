package com.aihangxunxi.aitalk.im.constant.enums;

/**
 * 推广者类型
 * @author wangchaochao
 * @version 3.0 2020/7/2
 */
public enum ExtensionTypeEnum implements BaseEnum {

	USER("user", "爱航用户"),

	HOTEL("hotel", "酒店用户"),

	AGENT("agent", "服务商用户");

	private String code;

	private String value;

	ExtensionTypeEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
