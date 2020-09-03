package com.aihangxunxi.aitalk.storage.domain.msg.model;

import java.io.Serializable;

/**
 * @author chenqingze107@163.com
 * @version 1.0

 */
public interface RecentSession extends Serializable {

	String KEY_EXT = "ext";

	/**
	 * 获取扩展字段
	 * @return 扩展字段
	 */
	String getExt();

	/**
	 * 获取最近的一条消息，可能为空
	 * @return 最后一条消息的Json字符串
	 */
	String getLastMsg();

	/**
	 * 获取会话的ID（会话类型|好友帐号，群ID等）,会话类型分为p2p/team/superTeam，格式分别是：p2p|accid、team|tid、super_team|tid
	 * @return 会话ID
	 */
	String getSessionId();

	/**
	 * 获取最近一条消息的时间戳
	 * @return 时间
	 */
	long getUpdateTime();

	/**
	 * 从session中分离出会话id和会话类型
	 * @param <any> 会话类型和会话id构成的Pair
	 */
	// <any> parseSessionId();

	/**
	 * 新建带有RecentSession信息的RecentContact
	 * @return 转化好的RecentContact
	 */
	RecentContact toRecentContact();

}
