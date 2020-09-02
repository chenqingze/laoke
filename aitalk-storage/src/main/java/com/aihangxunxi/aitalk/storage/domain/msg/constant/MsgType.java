package com.aihangxunxi.aitalk.storage.domain.msg.constant;

/**
 * 消息格式类型
 *
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020-03-2020/3/10
 */
public enum MsgType {

	UNDEF(-1, "UNKNOWN"),

	TEXT(0, "文本"),

	IMAGE(1, "图片"),

	AUDIO(2, "语音"),

	VIDEO(3, "视频"),

	LOCATION(4, "位置"),

	NOTIFICATION(5, "通知消息"),

	FILE(6, "文件"),

	AVCHAT(7, "音视频通话"),

	TIP(10, "提醒消息"),

	ROBOT(11, "机器人消息"),

	CUSTOM(100, "自定义消息");

	private final int value;

	private final String sendMessageTip;

	public String getSendMessageTip() {
		return this.sendMessageTip;
	}

	MsgType(int value, String sendMessageTip) {
		this.value = value;
		this.sendMessageTip = sendMessageTip;
	}

	public int getValue() {
		return this.value;
	}

	public static MsgType codeOf(int code) {
		for (MsgType em : values()) {
			if (em.getValue() == code) {
				return em;
			}
		}
		throw new RuntimeException("没有找到对应的枚举");
	}

}
