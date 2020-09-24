package com.aihangxunxi.aitalk.restapi.constant.enums;

/**
 * 用户状态
 * @author wangchaochao
 * @version 3.0 2020/7/4
 */
public enum UserStatusEnum implements BaseEnum {

	EFFECTIVE("effective", "有效"),

	PROHIBIT("prohibit", "禁用"),

	CANCEL("cancel", "注销");

	private String code;

	private String value;

	UserStatusEnum(String code, String value) {
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
