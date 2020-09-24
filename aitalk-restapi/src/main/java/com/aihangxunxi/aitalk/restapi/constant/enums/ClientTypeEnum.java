package com.aihangxunxi.aitalk.restapi.constant.enums;

/**
 * 客户端类型
 * @author wangchaochao
 * @version 3.0 2020/7/16
 */
public enum ClientTypeEnum implements BaseEnum {

	AHXX_APP("ahxx_app", "爱航信息APP"),

	MIN_PROGRAM("min_program", "微信小程序"),

	AHXX_H5("ahxx_h5", "爱航信息H5"),

	OTHER("other", "其它");

	private String code;

	private String value;

	ClientTypeEnum(String code, String value) {
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
