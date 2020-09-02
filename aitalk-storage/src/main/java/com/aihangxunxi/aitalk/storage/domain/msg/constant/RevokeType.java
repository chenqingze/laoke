package com.aihangxunxi.aitalk.storage.domain.msg.constant;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/19
 */
public enum RevokeType {

	UNDEFINED(-1), P2P_DELETE_MSG(7), TEAM_DELETE_MSG(8), SUPER_TEAM_DELETE_MSG(12), P2P_ONE_WAY_DELETE_MSG(
			13), TEAM_ONE_WAY_DELETE_MSG(14);

	private final int value;

	RevokeType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static RevokeType typeOfValue(int value) {

		for (RevokeType t : values()) {
			if (value == t.value)
				return t;
		}
		return UNDEFINED;
	}

}
