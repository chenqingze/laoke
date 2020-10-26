package com.aihangxunxi.aitalk.im.channel;

import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;

/**
 * 获取channel发送消息
 */
public class DefaultAitalkChannel implements AitalkChannel {

	private final ChannelManager channelManager;

	private final GroupManager groupManager;

	public DefaultAitalkChannel(ChannelManager channelManager, GroupManager groupManager, GroupManager groupManager1) {
		this.channelManager = channelManager;
		this.groupManager = groupManager1;
	}

	@Override
	public void sendMsg(String uid, Message message) {
		channelManager.findChannelByUserId(uid).writeAndFlush(message);
	}

	@Override
	public void sendMucMsg(String mucId, Message message) {
		groupManager.sendGroupMsg(mucId, message);
	}

}
