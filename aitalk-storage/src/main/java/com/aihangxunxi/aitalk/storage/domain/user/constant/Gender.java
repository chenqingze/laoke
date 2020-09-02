package com.aihangxunxi.aitalk.storage.domain.user.constant;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/25
 */
public enum Gender {

	UNKNOWN(0), MALE(1), FEMALE(2);

	private final int value;

	Gender(int value) {
		this.value = value;
	}

	public final int getValue() {
		return this.value;
	}

	public static Gender statusOfValue(int value) {

		for (Gender t : values()) {
			if (value == t.value)
				return t;
		}
		return UNKNOWN;
	}

}
