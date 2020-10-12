package com.aihangxunxi.aitalk.im.handler;

import com.aihangxunxi.aitalk.im.manager.GroupManager;
import com.aihangxunxi.aitalk.im.protocol.buffers.ChangeGroupNameAck;
import com.aihangxunxi.aitalk.im.protocol.buffers.Message;
import com.aihangxunxi.aitalk.im.protocol.buffers.OpCode;
import com.aihangxunxi.aitalk.storage.repository.GroupRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 更新群名
 */
@Component
@ChannelHandler.Sharable
public class EditGroupNameHandler extends ChannelInboundHandlerAdapter {

	@Resource
	private GroupRepository groupRepository;

	@Resource
	private GroupManager groupManager;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof Message && ((Message) msg).getOpCode() == OpCode.EDIT_GROUP_NAME_REQUEST) {

			String groupName = ((Message) msg).getChangeGroupNameRequest().getGroupName();
			String groupId = ((Message) msg).getChangeGroupNameRequest().getGroupId();
			if (groupRepository.updateGroupName(groupId, groupName)) {
				Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq())
						.setOpCode(OpCode.EDIT_GROUP_NAME_ACK)
						.setChangeGroupNameAck(ChangeGroupNameAck.newBuilder().setGroupId(groupId)
								.setGroupName(groupName).setMessage("更新成功").setSuccess("ok").build())
						.build();
				ctx.writeAndFlush(ack);
				groupManager.sendGroupMsg(groupId, (Message) msg);

			}
			else {
				Message ack = Message.newBuilder().setSeq(((Message) msg).getSeq())
						.setOpCode(OpCode.EDIT_GROUP_NAME_ACK)
						.setChangeGroupNameAck(ChangeGroupNameAck.newBuilder().setGroupId(groupId)
								.setGroupName(groupName).setMessage("更新失败，请稍后再试").setSuccess("no").build())
						.build();
				ctx.writeAndFlush(ack);
			}

		}
		else {

			ctx.fireChannelRead(msg);
		}
	}

}
