package com.aihangxunxi.aitalk.storage.domain.team.model;


import com.aihangxunxi.aitalk.storage.domain.msg.model.Msg;

/**
 * 通知消息过滤器
 *
 * @author chenqingze107@163.com
 * @version 1.0
 * @since 2020-03-2020/3/18
 */
public interface IMMessageFilter {

	/**
	 * 是否过滤通知消息（默认不过滤）
	 * @param msg 通知消息
	 * @return 返回true表示过滤（那么SDK将不存储此消息，上层也不会收到此消息），默认false即不过滤（默认存储到db并通知上层）
	 */
	boolean shouldIgnore(Msg msg);

}
