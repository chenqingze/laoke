package com.aihangxunxi.aitalk.im.constant.enums;

/**
 * 系统类型枚举
 * @author wangchaochao
 * @version 3.0 2020/7/16
 */
public enum PhoneSysTypeEnum implements BaseEnum {

	ANDROID("android", "安卓系统"), IOS("ios", "苹果系统");

	private String code;

	private String value;

	PhoneSysTypeEnum(String code, String value) {
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
