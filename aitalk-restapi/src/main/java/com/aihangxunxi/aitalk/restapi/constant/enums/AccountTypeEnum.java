package com.aihangxunxi.aitalk.restapi.constant.enums;

/**
 * 账户类型枚举
 * @author wangchaochao
 * @version 3.0 2020/06/30
 */
public enum AccountTypeEnum implements BaseEnum {

	BALANCE_ACCOUNT("balance", "余额"),

	WECHAT_ACCOUNT("wechat", "微信"),

	ALIPAY_ACCOUNT("alipay", "支付宝"),

	CARD_ACCOUNT("card", "卡账户"),

	APPLE_ACCOUNT("apple", "苹果");

	private String code;

	private String value;

	AccountTypeEnum(String code, String value) {
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
