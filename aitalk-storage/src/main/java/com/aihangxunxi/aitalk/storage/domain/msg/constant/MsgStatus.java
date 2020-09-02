package com.aihangxunxi.aitalk.storage.domain.msg.constant;

/**
 * 消息状态
 *
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/16
 */
public enum MsgStatus {

	DRAFT(-1), // 草稿
	SENDING(0), // 正在发送中
	SUCCESS(1), // 发送成功
	FAIL(2), // 发送失败
	READ(3), // 消息已读 发送消息时表示对方已看过该消息 接收消息时表示自己已读过，一般仅用于音频消息
	UNREAD(4);// 未读状态

	private final int value;

	MsgStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static MsgStatus statusOfValue(int value) {

		for (MsgStatus t : values()) {
			if (value == t.value)
				return t;
		}
		return SENDING;
	}

}
