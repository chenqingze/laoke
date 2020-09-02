package com.aihangxunxi.aitalk.storage.domain.msg.constant;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020/3/19
 */
public enum NotificationType {

	UNDEFINED(1), INVITE_MEMBER(0), KICK_MEMBER(1), LEAVE_TEAM(2), UPDATE_TEAM(3), DISMISS_TEAM(4), PASS_TEAM_APPLY(
			5), TRANSFER_OWNER(6), ADD_TEAM_MANAGER(7), REMOVE_TEAM_MANAGER(8), ACCEPT_INVITE(9), MUTE_TEAM_MEMBER(
					10), CHAT_ROOM_MEMBER_IN(301), CHAT_ROOM_MEMBER_EXIT(302), CHAT_ROOM_MEMBER_BLACK_ADD(
							303), CHAT_ROOM_MEMBER_BLACK_REMOVE(304), CHAT_ROOM_MEMBER_MUTE_ADD(
									305), CHAT_ROOM_MEMBER_MUTE_REMOVE(306), CHAT_ROOM_MANAGER_ADD(
											307), CHAT_ROOM_MANAGER_REMOVE(308), CHAT_ROOM_COMMON_ADD(
													309), CHAT_ROOM_COMMON_REMOVE(310), CHAT_ROOM_CLOSE(
															311), CHAT_ROOM_INFO_UPDATED(312), CHAT_ROOM_MEMBER_KICKED(
																	313), CHAT_ROOM_MEMBER_TEMP_MUTE_ADD(
																			314), CHAT_ROOM_MEMBER_TEMP_MUTE_REMOVE(
																					315), CHAT_ROOM_MY_ROOM_ROLE_UPDATED(
																							316), CHAT_ROOM_QUEUE_CHANGE(
																									317), CHAT_ROOM_ROOM_MUTED(
																											318), CHAT_ROOM_ROOM_DE_MUTED(
																													319), CHAT_ROOM_QUEUE_BATCH_CHANGE(
																															320), SUPER_TEAM_INVITE(
																																	401), SUPER_TEAM_KICK(
																																			402), SUPER_TEAM_LEAVE(
																																					403), SUPER_TEAM_UPDATE_T_INFO(
																																							404), SUPER_TEAM_DISMISS(
																																									405), SUPER_TEAM_CHANGE_OWNER(
																																											406), SUPER_TEAM_ADD_MANAGER(
																																													407), SUPER_TEAM_REMOVE_MANAGER(
																																															408), SUPER_TEAM_MUTE_TLIST(
																																																	409), SUPER_TEAM_APPLY_PASS(
																																																			410), SUPER_TEAM_INVITE_ACCEPT(
																																																					411);

	private final int value;

	NotificationType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static NotificationType typeOfValue(int value) {

		for (NotificationType t : values()) {
			if (value == t.value)
				return t;
		}
		return UNDEFINED;
	}

}
