package com.aihangxunxi.aitalk.storage.domain.msg.constant;

/**
 * 附件传输状态枚举类
 *
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/19
 */
public enum AttachStatus {

	DEF(0), // 默认状态，未开始
	TRANSFERRING(1), // 传输成功
	TRANSFERRED(2), // 正在传输
	FAIL(3), // 传输失败
	CANCEL(4); // 传输取消

	private final int value;

	AttachStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static AttachStatus statusOfValue(int value) {

		for (AttachStatus t : values()) {
			if (value == t.value)
				return t;
		}
		return DEF;
	}

}
