package com.aihangxunxi.aitalk.im.cluster;

import com.aihangxunxi.aitalk.im.channel.AitalkChannel;
import com.aihangxunxi.aitalk.im.channel.ChannelManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import io.netty.channel.Channel;

import java.io.IOException;

public class ClusterAitalkChannel implements AitalkChannel {

	private final ChannelManager channelManager;

	private final Producer producer;

	public ClusterAitalkChannel(ChannelManager channelManager, Producer producer) {
		this.channelManager = channelManager;
		this.producer = producer;
	}

	@Override
	public void sendMsg(String uid, Message message) {
		Channel channel = this.channelManager.findChannelByUserId(uid);
		if (channel.isWritable()) {
			channel.writeAndFlush(message);
		}
		else {
			String redisImNode = this.channelManager.findNodeByUserId(uid);
			if (redisImNode != null && !redisImNode.isEmpty()) {
				try {
					producer.send(redisImNode, message);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	@Override
	public void sendMucMsg(String mucId, Message message) {
		// todo:获取在线群成员的方式需要优化
		// todo：批量获取在线 group member 然后发送
	}

}
