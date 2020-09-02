package com.aihangxunxi.aitalk.im.protocol.constant;

/**
 * 操作类型
 */
public enum OpCode {

	// 连接认证相关0～20
	AUTH(0),

	AUTH_ACK(1),

	PING(2),

	PONG(3),

	DISCONNECT(4),

	// 消息相关字段编号范围:Msg 21~50
	MSG_DATA(5), // 消息请求

	MSG_DATA_ACK(6), // 消息回执

	// 群组相关字段编号范围:TEAM 51~
	CREATE_TEAM_REQ(51), GROUP_MSG_DATA_REQ(57), GROUP_MSG_DATA_ACK(58),

	DISMISS_TEAM_REQ(52),

	QUERY_MEMBER_LIST_REQ(53),

	JOIN_TEAM_REQ(54),

	CONVERSATION(1010),

	INVITE(1000),

	FRIEND(1011),

	LOG_OUT(200),

	SYSTEM_INFORMS(115);

	private final int value;

	OpCode(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static OpCode codeOfValue(int value) {

		for (OpCode t : values()) {
			if (value == t.value)
				return t;
		}
		throw new RuntimeException("没有找到对应的枚举");
	}

}
//
// public class OpCodeType {
//
// private OpCodeType() {
// }
//
// // 连接认证相关0～10
// public static final int AUTH = 0;
//
// public static final int AUTH_ACK = 1;
//
// public static final int PING = 2;
//
// public static final int PONG = 3;
//
// public static final int DISCONNECT = 4;
//
// // 消息相关字段编号范围:Msg 21~50
// public static final int MSG_DATA = 5; // 消息请求
//
// public static final int MSG_DATA_ACK = 6; // 消息回执
//
// // 群组相关字段编号范围:TEAM 51~
// public static final int CREATE_TEAM_REQ = 51;
//
// public static final int DISMISS_TEAM_REQ = 52;
//
// public static final int QUERY_MEMBER_LIST_REQ = 53;
//
// public static final int JOIN_TEAM_REQ = 54;
//
// }
