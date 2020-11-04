package com.aihangxunxi.aitalk.storage.constant;

/**
 * 用户状态
 *
 * @author chenqingze107@163.com
 * @version 2.0
 */
public enum UserType {

	PERSONAL, STORE;

	public static UserType codeOf(int code) {
		for (UserType userType : values()) {
			if (userType.ordinal() == code) {
				return userType;
			}
		}
		throw new RuntimeException("没找到对应的枚举");
	}

}
