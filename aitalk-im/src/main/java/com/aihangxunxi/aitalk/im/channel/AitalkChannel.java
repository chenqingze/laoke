package com.aihangxunxi.aitalk.im.channel;

import com.aihangxunxi.aitalk.im.protocol.buffers.Message;

public interface AitalkChannel {

	/**
	 * 发送
	 */
	void sendMsg(String uid, Message message);

	/**
	 * 发送多人消息
	 */
	void sendMucMsg(String mucId, Message message);

}
