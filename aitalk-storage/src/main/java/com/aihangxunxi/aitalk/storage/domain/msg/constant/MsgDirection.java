package com.aihangxunxi.aitalk.storage.domain.msg.constant;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/18
 */
public enum MsgDirection {

	OUT(0), // 发出去的消息

	IN(1);// 接收到的消息

	private final int value;

	MsgDirection(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static MsgDirection directionOfValue(int value) {

		for (MsgDirection t : values()) {
			if (value == t.value)
				return t;
		}
		return OUT;
	}

}
