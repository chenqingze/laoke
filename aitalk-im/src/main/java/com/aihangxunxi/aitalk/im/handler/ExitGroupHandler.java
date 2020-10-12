package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.ExitGroupAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 退出群聊
 */
@Component
@ChannelHandler.Sharable
public class ExitGroupHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupRepository groupRepository;

	@Resource
	private GroupManager groupManager;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.EXIT_GROUP_REQUEST) {
			String groupId = ((Message) msg).getExitGroupRequest().getGroupId();
			String userId = ((Message) msg).getExitGroupRequest().getUserId();

			this.groupRepository.exitGroup(groupId, Long.parseLong(userId));

			groupManager.sendGroupMsg(groupId, (Message) msg);

			Message ack = Message.newBuilder().setOpCode(OpCode.EXIT_GROUP_ACK).setSeq(((Message) msg).getSeq())
					.setExitGroupAck(ExitGroupAck.newBuilder().setGroupId(groupId).setMessage("退出成功").setSuccess("ok")
							.setUserId(userId).build())
					.build();

			ctx.writeAndFlush(ack);
		}
		else {
			ctx.fireChannelRead(msg);
		}
	}

}
