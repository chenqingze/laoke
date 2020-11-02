package com.aihangxunxi.aitalk.storage.constant;

/**
 * @author chenqingze107@163.com
 * @version 2.0
 */
public enum ConversationType {

	P2P, MUC, NOTIFICATION, CHATROOM, TEAM, CONSULT;

	public static ConversationType codeOf(int code) {
		for (ConversationType conversationType : values()) {
			if (conversationType.ordinal() == code) {
				return conversationType;
			}
		}
		throw new RuntimeException("没找到对应的枚举");
	}

}
