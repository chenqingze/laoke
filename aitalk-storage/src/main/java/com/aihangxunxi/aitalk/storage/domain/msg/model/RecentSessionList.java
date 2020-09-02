package com.aihangxunxi.aitalk.storage.domain.msg.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020-03-2020/3/18
 */
public interface RecentSessionList extends Serializable {

	/**
	 * 拉到的一页会话列表
	 * @return 本页的会话列表
	 */
	List<RecentSession> getSessionList();

	/**
	 * 是否有更早的消息
	 * @return true: 还有消息; false: 已经没有更多消息
	 */
	boolean hasMore();

}